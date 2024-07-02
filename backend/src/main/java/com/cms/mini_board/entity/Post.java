package com.cms.mini_board.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor @AllArgsConstructor @Builder
@Getter
@Setter
public class Post extends BaseEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false, columnDefinition = "TINYTEXT")
    private String title;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
    @Column(nullable = false)
    @Builder.Default
    private Long views = 0L;

    @ToString.Exclude
    @OneToMany(mappedBy = "post")
    private List<Reply> replies;

    @ToString.Exclude
    @OneToMany(mappedBy = "post")
    private List<BoardFile> files;
}
