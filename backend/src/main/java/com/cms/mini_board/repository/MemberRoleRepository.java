package com.cms.mini_board.repository;

import com.cms.mini_board.entity.manytomany.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRoleRepository extends JpaRepository<MemberRole, Long> {
}
