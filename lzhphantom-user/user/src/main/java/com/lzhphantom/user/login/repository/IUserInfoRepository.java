package com.lzhphantom.user.login.repository;

import com.lzhphantom.user.login.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserInfoRepository extends JpaRepository<UserInfo, Long> {
}
