package com.microrobot.user.persistence;

import com.microrobot.user.entities.RolUser;
import com.microrobot.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findByRoles(RolUser roles);
}
