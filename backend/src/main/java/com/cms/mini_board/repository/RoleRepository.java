package com.cms.mini_board.repository;

import com.cms.mini_board.entity.Role;
import lombok.Getter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);

//    List<Role> findAllByName(List<Role> roles);
}
