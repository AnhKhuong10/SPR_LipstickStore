package org.example.mystoreh.repository;

import org.example.mystoreh.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
    List<UserAddress> findAll();
    List<UserAddress> findByUserId(Long userId);
}
