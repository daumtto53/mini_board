package com.cms.mini_board.repository;

import com.cms.mini_board.dto.SearchCondition;
import com.cms.mini_board.entity.constants.SearchOption;
import com.cms.mini_board.entity.Post;
import com.cms.mini_board.entity.QPost;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SearchQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    private BooleanExpression queryTitle(SearchCondition condition) {
        if (condition.getSearchOption() == SearchOption.TITLE || condition.getSearchOption() == SearchOption.TITLE_CONTENT)
            return QPost.post.title.contains(condition.getSearchQuery());
        return null;
    }

    private BooleanExpression queryContent(SearchCondition condition) {
        if (condition.getSearchOption() == SearchOption.CONTENT || condition.getSearchOption() == SearchOption.TITLE_CONTENT)
            return QPost.post.content.contains(condition.getSearchQuery());
        return null;
    }

    public Page<Post> findAllBySearchQueryAndOption(SearchCondition condition, Pageable pageable) {
        QPost post = QPost.post;
        List<Post> content = jpaQueryFactory.
                selectFrom(post)
                .where(queryTitle(condition).or(queryContent(condition)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(
                        post.postId.desc()
                )
                .fetch();

        Long total = jpaQueryFactory
                .select(post.count())
                .from(post)
                .where(
                        queryTitle(condition),
                        queryContent(condition)
                ).fetchOne();

        return new PageImpl<>(content, pageable, total);
    }
}
