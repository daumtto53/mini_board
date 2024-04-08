package com.cms.mini_board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"member"})
public class Post extends BaseEntity{
    @Id @GeneratedValue
    private Long post_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false, columnDefinition = "TINYTEXT")
    private String title;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
    @Column(nullable = false)
    private Long views = 0L;

    @OneToMany(mappedBy = "post")
    private List<Reply> replies;
    @OneToMany(mappedBy = "member")
    private List<Member> members;


}
