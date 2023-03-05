package com.lzhphantom.user.login.repository;

import com.lzhphantom.user.login.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Long> {
}
