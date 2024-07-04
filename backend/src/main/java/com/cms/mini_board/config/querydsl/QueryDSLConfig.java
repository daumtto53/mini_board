package com.cms.mini_board.config.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * - JPAQueryFactory를 Bean으로 등록하기 위해서 Configuration 파일 생성.
 * - EntityManager em을 주입받는다. @Autowired 대신 @PersistenceContext로 주입 받는다.
 * - jpaQueryFactory를 Bean으로 등록받게 return한다.
 */
@Configuration
//@Entity로 관리되는 JPA Entity들의 상태 변화를 감지한다.
//@EnableJpaAuditing
public class QueryDSLConfig {
    // EntityManager에 의존성 주입을 담당한다.
    @PersistenceContext
    private EntityManager em;
    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager em) {
        return new JPAQueryFactory(em);
    }
}
