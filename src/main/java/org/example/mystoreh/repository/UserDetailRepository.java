package org.example.mystoreh.repository;

import org.example.mystoreh.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
    @Query("SELECT ud FROM UserDetail ud WHERE ud.user.id = :userId")
    Optional<UserDetail> getUserDetailByUserId(@Param("userId") Long userId);
}
