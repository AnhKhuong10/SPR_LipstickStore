package org.example.mystoreh.service.impl;
import org.example.mystoreh.entity.Role;
import org.example.mystoreh.entity.User;
import org.example.mystoreh.repository.RoleRepository;
import org.example.mystoreh.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private RoleRepository repository;

    @Autowired
    public RoleServiceImpl(RoleRepository repository) {
        this.repository = repository;
    }
}
