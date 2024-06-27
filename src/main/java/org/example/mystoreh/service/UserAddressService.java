package org.example.mystoreh.service;

import org.example.mystoreh.entity.UserAddress;

import java.util.List;

public interface UserAddressService {
    List<UserAddress> findByUserId(Long userId);
    void setDefaultAddress(Long addressId, Long userId);
    public UserAddress getUserAddressDefault(List<UserAddress> userAddresses);
}
