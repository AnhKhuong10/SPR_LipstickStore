package org.example.mystoreh.service;

import org.example.mystoreh.dto.UpdateUserDetailDTO;
import org.example.mystoreh.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    User getUserByUsername(String username);
    User findUserByUsername(String username);
    void save(User user);
    Optional<User> checkPasswordUser(String username, String password); // check password dung chua
    Boolean checkUserByUsername(String username); //check ton tai
    User getById(Long id);
    void changePassword(Long id, String password);
}
