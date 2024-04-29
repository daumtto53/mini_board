package com.cms.mini_board.service;

import com.cms.mini_board.dto.BoardPageDTO;
import com.cms.mini_board.dto.BoardReadDTO;
import com.cms.mini_board.dto.PageDTO.PageRequestDTO;
import com.cms.mini_board.dto.PageDTO.PageResultDTO;
import com.cms.mini_board.dto.PostDTO;
import com.cms.mini_board.entity.Member;
import com.cms.mini_board.entity.Post;

/* Repository의 Data를 가공해서 Controller로 전달하는 것이 service의 역할.*/
public interface PostService {

    PageResultDTO<BoardPageDTO, Post> getList(PageRequestDTO requestDTO);

    default BoardPageDTO boardPageToDTO(Post post) {
        BoardPageDTO dto = BoardPageDTO.builder()
                .id(post.getPostId())
                .author(post.getMember().getName())
                .title(post.getTitle())
                .updatedAt(post.getUpdatedAt())
                .views(post.getViews())
                .replyCount(post.getReplies().size())
                .build();
        return dto;
    }

    default Post postDTOToEntity(PostDTO postDTO) {
        Member member = Member.builder()
//                .memberId(postDTO.getMemberId())
                .memberId(1L)
                .build();

        Post result = Post.builder()
                .postId(postDTO.getPostId())
                .title(postDTO.getTitle())
                .member(member)
                .content(postDTO.getContent())
                .views(postDTO.getViews() == null ? 0 : postDTO.getViews())
                .build();
        return result;
    }

    default PostDTO PostEntityToDTO(Post post) {
        PostDTO dto = PostDTO.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .memberId(post.getMember().getMemberId())
                .author(post.getMember().getNickname())
                .content(post.getContent())
                .views(post.getViews())
                .regDate(post.getUpdatedAt())
                .build();
        return dto;
    }


    BoardReadDTO getFullBoardReadContent(String pageId);

    //Board관련 API
    Long writePost(PostDTO postDTO);
    Long modifyPost(PostDTO postDTO);
    void deletePost(Long postId);


}
