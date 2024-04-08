package com.cms.mini_board.entity;


import com.cms.mini_board.entity.Enum.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor @AllArgsConstructor @Builder
@Getter
@Setter
@ToString
public class Member extends BaseEntity{
    @Id @GeneratedValue
    private Long member_id;

    @Column(length = 64, nullable = false, unique = true)
    private String login_id;

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
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<Reply> replies;
}
