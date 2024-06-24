package com.cms.mini_board.entity.manytomany;

import com.cms.mini_board.entity.Member;
import com.cms.mini_board.entity.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString(exclude = {"member", "role"})
public class MemberRole {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberRoleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;
}
