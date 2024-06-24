package com.cms.mini_board.entity;

import com.cms.mini_board.entity.manytomany.MemberRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
    private List<MemberRole> memberRoles;
}
