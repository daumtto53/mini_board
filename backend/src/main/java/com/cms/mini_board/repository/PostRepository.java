package com.cms.mini_board.repository;

import com.cms.mini_board.entity.Post;
import com.cms.mini_board.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
}
