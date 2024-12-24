package com.hicham.airbnb_clone_back.user.repository;

import com.hicham.airbnb_clone_back.user.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOneByEmail(String email);

    Optional<User> findOneByPublicId(UUID publicId);
}
