package org.example.mystoreh.service;

import org.example.mystoreh.dto.UpdateUserDetailDTO;
import org.example.mystoreh.entity.UserDetail;

import java.util.Optional;

public interface UserDetailService { ;
    Optional<UserDetail> getUserDetailByUserId(Long id);
    void updateProfile(UpdateUserDetailDTO updateUserDetailDTO, Long id);
}
