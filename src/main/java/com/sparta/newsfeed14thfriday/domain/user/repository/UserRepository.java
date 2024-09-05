package com.sparta.newsfeed14thfriday.domain.user.repository;

import com.sparta.newsfeed14thfriday.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmailAndDeleted(String email, boolean Deleted);
    List<User> findAllByEmailInAndDeleted(List<String> emails, boolean deleted);

    Optional<User> findByEmail(String email);
}
