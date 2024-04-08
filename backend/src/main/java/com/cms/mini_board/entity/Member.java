package com.cms.mini_board.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Member extends BaseEntity{
    @Id @GeneratedValue
    private Long member_id;

    @Column(length = 64, nullable = false, unique = true)
    private String long_id;

    @Column(length = 64, nullable = false)
    private String password;
    @Column(length = 64, nullable = false)
    private String name;
    @Column(length = 64, nullable = false)
    private String nickname;
    private Date birthday;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<Post> posts;
}
