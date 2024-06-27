package org.example.mystoreh.service.impl;

import org.example.mystoreh.dto.UpdateUserDetailDTO;
import org.example.mystoreh.entity.User;
import org.example.mystoreh.entity.UserDetail;
import org.example.mystoreh.repository.UserDetailRepository;
import org.example.mystoreh.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailService {
    private UserDetailRepository repository;

    @Autowired
    public UserDetailServiceImpl(UserDetailRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<UserDetail> getUserDetailByUserId(Long id) {
        return repository.getUserDetailByUserId(id);
    }

    @Override
    public void updateProfile(UpdateUserDetailDTO updateUserDetailDTO, Long id) {
        UserDetail user = repository.findById(id).orElseThrow(
                () -> new RuntimeException()
        );
        user.setMobile(updateUserDetailDTO.getMobile());
        user.setGender(updateUserDetailDTO.getGender());
        user.setEmail(updateUserDetailDTO.getEmail());
        repository.save(user);
    }
}
