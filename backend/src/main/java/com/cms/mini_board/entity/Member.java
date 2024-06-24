package com.cms.mini_board.entity;


import com.cms.mini_board.entity.constants.Gender;
import com.cms.mini_board.entity.manytomany.MemberRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor @AllArgsConstructor @Builder
@Getter
@Setter
@ToString
public class Member extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(length = 64, nullable = false, unique = true)
    private String email;

    @Column(length = 64, nullable = false, unique = true)
    private String loginId;
    @Column(length = 64, nullable = false)
    private String password;

    //Security 관련 logic
    private boolean isFromSocial;
    private boolean isDisabled;

    //개인정보
    @Column(length = 64, nullable = false)
    private String name;
    @Column(length = 64, nullable = false)
    private String nickname;
    private Date birthday;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    //다른 Entity와의 연관관계.
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<Post> posts;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<Reply> replies;
    //Role
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<MemberRole> memberRoles;

}
