package com.cms.mini_board.repository;

import com.cms.mini_board.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Commit
@ActiveProfiles("test")
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void createRoles() {
        Role role0 = new Role(null, "ROLE_ADMIN", null);
        Role role1 = new Role(null, "ROLE_MANAGER", null);
        Role role2 = new Role(null, "ROLE_USER", null);
        roleRepository.saveAll(Arrays.asList(role0, role1, role2));
    }


}