package org.example.mystoreh.service.impl;

import org.example.mystoreh.entity.AuthUser;
import org.example.mystoreh.entity.Role;
import org.example.mystoreh.entity.User;
import org.example.mystoreh.repository.RoleRepository;
import org.example.mystoreh.repository.UserRepository;
import org.example.mystoreh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private UserRepository repository;
    private RoleRepository roleRepository;

    @Autowired  // goi tat ca cac phuong thuc cua repository ra
    public UserServiceImpl(UserRepository repository, RoleRepository roleRepository) {
        this.repository = repository;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User getUserByUsername(String username) {
        return repository.getUserByUsername(username);
    }

    @Override
    public User findUserByUsername(String username) {
        return repository.findUserByUsername(username);
    }

    @Override
    public void save(User user) {

    }

    @Override
    public Optional<User> checkPasswordUser(String username, String password) {
        User user = repository.findUserByUsername(username);
        if (user.getPassword().equals(password)) return Optional.ofNullable(user);
        return Optional.empty();
    }

    @Override
    public Boolean checkUserByUsername(String username) {
        User user = repository.findUserByUsername(username);
        if (user == null) return false;
        return true;
    }

    @Override
    public User getById(Long id) {
        return repository.getById(id);
    }

    @Override
    public void changePassword(Long id, String password) {
        User user = repository.findById(id).orElseThrow(
                () -> new RuntimeException()
        );
        user.setPassword(password);
        repository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("not found");
        }

        Set<Role> roles = user.getRoles();
        //list role --> list grantedAuthority
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (roles != null) {
            for(Role role: roles){
                GrantedAuthority authority = new SimpleGrantedAuthority(role.getRoleName());
                authorities.add(authority);
            }
        }
        return new AuthUser(
                user.getUsername(),
                user.getPassword(),
                authorities,
                user.getId(),
                user.getCreateAt(),
                user.isDeleted(),
                user.getStatus()
        );
    }
}
