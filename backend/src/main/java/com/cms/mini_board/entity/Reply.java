package com.cms.mini_board.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@NoArgsConstructor @AllArgsConstructor @Builder
@Getter
@Setter
@ToString(exclude = {"member"})
public class Reply {
    @Id @GeneratedValue
    private Long replyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
    @Column(length = 500)
    private String replyText;
}
