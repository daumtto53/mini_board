package com.cms.mini_board.repository;

import com.cms.mini_board.entity.Member;
import com.cms.mini_board.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT r from Member m left join m.memberRoles mr left join mr.role r " +
            "WHERE m.email = :email and m.memberId=mr.member.memberId and mr.role.roleId=r.roleId")
    List<Role> findRolesByEmail(@Param("email") String email);
}
