package org.example.mystoreh.service.impl;

import org.example.mystoreh.entity.UserAddress;
import org.example.mystoreh.repository.UserAddressRepository;
import org.example.mystoreh.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserAddressServiceImpl implements UserAddressService {
    private UserAddressRepository repository;
    @Autowired
    public UserAddressServiceImpl(UserAddressRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<UserAddress> findByUserId(Long id) {
        return repository.findByUserId(id);
    }

    @Override
    public void setDefaultAddress(Long addressId, Long userId) {
        List<UserAddress> userAddresses = repository.findByUserId(userId);
        for (UserAddress address : userAddresses) {
            if (address.getId().equals(addressId)) {
                address.setDefaultAddress(true);
            } else {
                address.setDefaultAddress(false);
            }
        }
        repository.saveAll(userAddresses);
    }

    @Override
    public UserAddress getUserAddressDefault(List<UserAddress> userAddresses) {
        for(UserAddress userAddress: userAddresses) {
            if (userAddress.isDefaultAddress()) {
                return userAddress;
            }
        }
        return null;
    }
}
