package com.cms.mini_board.repository;

import com.cms.mini_board.entity.Post;
import com.cms.mini_board.entity.Reply;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
//    @Query("SELECT p FROM Reply r JOIN Post p WHERE r.post.postId= :postid")
//    List<Reply> findRepliesByPostId(@Param("postId") Long postId, Sort sort);

    //used in replyservice : ver3
        List<Reply> findRepliesByPost(Post post);
}
