package com.cms.mini_board.repository;

import com.cms.mini_board.entity.Member;
import com.cms.mini_board.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT r from Member m join m.memberRoles mr join mr.role r " +
            "WHERE m.email = :email and m.memberId=mr.member.memberId and mr.role.roleId=r.roleId")
    List<Role> findRolesByEmail(@Param("email") String email);

    @Query("SELECT r FROM Member m JOIN m.memberRoles mr JOIN mr.role r " +
            "WHERE m.memberId = :member_id AND m.memberId=mr.member.memberId AND mr.role.roleId=r.roleId")
    List<Role> findRolesByMemberId(@Param("member_id") Long member_id);

    Optional<Member> findByUsername(String username);
}
