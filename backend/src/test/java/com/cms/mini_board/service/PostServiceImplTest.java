package com.cms.mini_board.service;

import com.cms.mini_board.dto.PostDTO;
import com.cms.mini_board.entity.Post;
import com.cms.mini_board.repository.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class PostServiceImplTest {

    @Autowired
    PostRepository postRepository;
    @Autowired
    PostService postService;

//    @Test
//    void writePost() {
//        //given
//        PostDTO dto = PostDTO.builder()
//                .title("title")
//                .memberId(1L)
//                .content("content")
//                .views(0L)
//                .build();
//        //when
//        Long l = postService.writePost(dto);
//        //then
//        Post post = postRepository.findById(l).get();
//        Assertions.assertThat(post.getTitle()).isEqualTo("title");
//        Assertions.assertThat(post.getMember().getMemberId()).isEqualTo(1L);
//    }

//    @Test
//    void modifyPost() {
//        //given
//        PostDTO original = PostDTO.builder()
//                .title("title")
//                .memberId(1L)
//                .content("content")
//                .views(0L)
//                .build();
//        Long postId = postService.writePost(original);
//
//        //when
//        PostDTO dto = PostDTO.builder()
//                .postId(postId)
//                .title("modifiedTitle")
//                .memberId(1L)
//                .content("modifiedContent")
//                .views(0L)
//                .build();
//        Long modifiedPostId= postService.modifyPost(dto);
//        //then
//        Post post = postRepository.findById(modifiedPostId).get();
//        Assertions.assertThat(modifiedPostId).isEqualTo(postId);
//        Assertions.assertThat(post.getContent()).isEqualTo("modifiedContent");
//        Assertions.assertThat(post.getMember().getMemberId()).isEqualTo(1L);
//    }

//    @Test
//    void deletePost() {
//        //given
//        PostDTO original = PostDTO.builder()
//                .title("title")
//                .memberId(1L)
//                .content("content")
//                .views(0L)
//                .build();
//        Long postId = postService.writePost(original);
//        //when
//        postService.deletePost(postId);
//        //then
//        org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException.class, () -> postRepository.findById(postId).get());
//    }
}