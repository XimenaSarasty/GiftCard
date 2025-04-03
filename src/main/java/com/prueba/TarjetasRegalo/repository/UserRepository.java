package com.prueba.TarjetasRegalo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.prueba.TarjetasRegalo.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByNameUser(String nameUser);
}
