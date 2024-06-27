package org.example.mystoreh.repository;

import org.example.mystoreh.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAll();
    User getUserByUsername(String username);
    User findUserByUsername(String username);
    User getById(Long id);
}
