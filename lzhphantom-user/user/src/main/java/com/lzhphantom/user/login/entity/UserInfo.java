package com.lzhphantom.user.login.entity;

import com.lzhphantom.core.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.envers.Audited;

import java.time.LocalDate;

@Entity
@Table(name = "LZHPHANTOM_USER_INFORMATION")
@Data
@Accessors(chain = true)
@Audited
@EqualsAndHashCode(callSuper = true)
public class UserInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "address")
    private String address;

    @Column(name = "introduction")
    private String introduction;
}
