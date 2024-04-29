package com.cms.mini_board.repository;

import com.cms.mini_board.entity.Post;
import com.cms.mini_board.entity.Reply;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Modifying
    @Query("DELETE FROM Reply r WHERE r.post.postId = :postId")
    void deleteAllByReplyId(@Param("postId") Long postId);
}
