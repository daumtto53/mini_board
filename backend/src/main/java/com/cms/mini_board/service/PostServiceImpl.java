package com.cms.mini_board.service;

import com.cms.mini_board.dto.*;
import com.cms.mini_board.dto.PageDTO.PageRequestDTO;
import com.cms.mini_board.dto.PageDTO.PageResultDTO;
import com.cms.mini_board.entity.BoardFile;
import com.cms.mini_board.entity.Member;
import com.cms.mini_board.entity.Post;
import com.cms.mini_board.entity.Reply;
import com.cms.mini_board.model.security.CustomOAuth2User;
import com.cms.mini_board.repository.BoardFileRepository;
import com.cms.mini_board.repository.MemberRepository;
import com.cms.mini_board.repository.PostRepository;
import com.cms.mini_board.repository.SearchQueryRepository;
import com.cms.mini_board.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class PostServiceImpl implements PostService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final BoardFileRepository boardFileRepository;
    private final FileUtils fileUtils;
    private final SearchQueryRepository searchQueryRepository;

    //parameter로 option, searchQuery가 들어왔을때는 overloading을 사용하는것이 좋을듯하다.


    public PageResultDTO<BoardPageDTO, Post> getList(PageRequestDTO requestDTO, SearchCondition condition) {
        Pageable pageRequest = requestDTO.getPageRequest(Sort.by(Sort.Direction.DESC, "postId"));
        Page<Post> result = searchQueryRepository.findAllBySearchQueryAndOption(condition, pageRequest);
        Function<Post, BoardPageDTO> fn = (en -> boardPageToDTO(en));
        return new PageResultDTO<>(result, fn);
    }
    @Override
    public PageResultDTO<BoardPageDTO, Post> getList(PageRequestDTO requestDTO) {
        Pageable request = requestDTO.getPageRequest(Sort.by(Sort.Direction.DESC, "postId"));
        Page<Post> result = postRepository.findAll(request);
        Function<Post, BoardPageDTO> fn = (en -> boardPageToDTO(en));
        return new PageResultDTO<>(result, fn);
    }
    private static List<BoardFileDTO> convertBoardFileToBoardFileDTOList(List<BoardFile> boardFiles) {
        return boardFiles.stream().map(boardFile -> {
            return createBoardFileDTO(boardFile);
        }).collect(Collectors.toList());
    }
    private static BoardFileDTO createBoardFileDTO(BoardFile boardFile) {
        return BoardFileDTO.builder()
                .originalFileName(boardFile.getOriginalName())
                .fullPath(boardFile.getPath())
                .saveName(boardFile.getSaveName())
                .formattedCreatedDate(
                        boardFile.getCreatedDate().format(
                                DateTimeFormatter.ofPattern("yyMMdd")).toString()
                )
                .build();
    }
    private static List<BoardReadReplyDTO> convertReplyToBoardReplyDTO(Post post) {
        return (List<BoardReadReplyDTO>) post.getReplies().stream()
                .map(PostServiceImpl::createBoardReadReplyDTO)
                .collect(Collectors.toList());
    }
    private static BoardReadReplyDTO createBoardReadReplyDTO(Reply reply) {
        BoardReadReplyDTO replyDTO = BoardReadReplyDTO.builder()
                .replyId(reply.getReplyId())
                .replyAuthor(reply.getMember().getNickname())
                .replyText(reply.getReplyText())
                .replyUpdatedAt(reply.getUpdatedAt())
                .build();
        return replyDTO;
    }
    @Override
    public BoardReadDTO getFullBoardReadContent(String postId) {
        Optional<Post> opt = postRepository.findById(Long.valueOf(postId));
        List<BoardFile> boardFiles = boardFileRepository.findBoardFilesByPostId(opt.get().getPostId());

        return opt.map((post) -> {
            BoardReadDTO dto = BoardReadDTO.builder()
                    .title(post.getTitle())
                    .author(post.getMember().getNickname())
                    .content(post.getContent())
                    .updatedAt(post.getUpdatedAt())
                    .views(post.getViews())
                    .boardReadReplyDTOList(
                            convertReplyToBoardReplyDTO(post)
                    )
                    .boardFileDTOList(
                            convertBoardFileToBoardFileDTOList(boardFiles)
                    )
                    .build();
            return dto;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Information Not Found"));
    }



    //save file & postData
    @Override
    @Transactional
    public Long writePost(PostDTO postDTO, List<MultipartFile> files) {
        //get from Spring seuciryt COntext.
        CustomOAuth2User oAuth2User = (CustomOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member member = memberRepository.findByUsername(oAuth2User.getUsername()).orElseThrow(() -> new NoSuchElementException());
        Post post = postDTOToEntity(postDTO);
        List<BoardFile> boardFiles = fileUtils.uploadFiles(files, post);
        post.setFiles(boardFiles);
        post.setMember(member);
        Post save = postRepository.save(post);
        List<BoardFile> boardFiles1 = boardFileRepository.saveAll(boardFiles);
        return save.getPostId();
    }

    @Override
    @Transactional
    public Long writePost(PostDTO postDTO) {
        //get From Spring Security Context
        CustomOAuth2User oAuth2User = (CustomOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member member = memberRepository.findByUsername(oAuth2User.getUsername()).orElseThrow(() -> new NoSuchElementException());
        Post post = postDTOToEntity(postDTO);
        post.setMember(member);
        Post save = postRepository.save(post);
        return save.getPostId();
    }

    @Override
    @Transactional
    public Long modifyPost(PostDTO postDTO) {
        Post post = postRepository.findById(postDTO.getPostId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "post Not Found"));
        List<BoardFile> oldBoardFile = boardFileRepository.findBoardFilesByPostId(post.getPostId());
        for (BoardFile file : oldBoardFile) {
            String path = file.getPath();
            String uuid = file.getSaveName();
            String fileName = path + File.separator + uuid;
            Path filePath = Paths.get(fileName);
            try {
                Files.deleteIfExists(filePath);
            } catch (IOException e) {e.printStackTrace();}
        }
        //1. 기존에 있던 파일데이터 삭제
        boardFileRepository.deleteAllFileByPostId(post.getPostId());
        Post toSave = postDTOToEntity(postDTO);
        Post modfiedPost = postRepository.save(toSave);
        return modfiedPost.getPostId();
    }
    @Override
    @Transactional
    public Long modifyPost(PostDTO postDTO, List<MultipartFile> files) {
        Post post = postRepository.findById(postDTO.getPostId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "post Not Found"));
        Post toSave = postDTOToEntity(postDTO);
        //1.5. 기존 디렉토리에 있던 파일도 삭제
        List<BoardFile> oldBoardFile = boardFileRepository.findBoardFilesByPostId(post.getPostId());
        for (BoardFile file : oldBoardFile) {
            String path = file.getPath();
            String uuid = file.getSaveName();
            String fileName = path + File.separator + uuid;
            Path filePath = Paths.get(fileName);
            try {
                Files.deleteIfExists(filePath);
            } catch (IOException e) {e.printStackTrace();}
        }
        //1. 기존에 있던 파일데이터 삭제
        boardFileRepository.deleteAllFileByPostId(post.getPostId());
        //2. file로 온 파일 DB에 추가
        List<BoardFile> boardFiles = fileUtils.uploadFiles(files, post);
        //3. post에 추가 굳이 안해도됨. 단방향 ManyToOne에서 양방향 설정해줬으니까.
        //4. boardFiles에 추가.
        List<BoardFile> boardFiles1 = boardFileRepository.saveAll(boardFiles);
        Post modfiedPost = postRepository.save(toSave);
        return modfiedPost.getPostId();
    }

    //cascade 사용하지 않기.
    //query를 사용하는 방법을 바꿔야함.
    @Override
    @Transactional
    public void deletePost(Long postId) {
        postRepository.deleteAllByReplyId(postId);
        postRepository.deleteById(postId);
    }

    @Override
    public Long incrementPostViewCount(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "post Not Found"));
        Long viewCount = post.getViews();
        post.setViews(++viewCount);
        postRepository.flush();
        return post.getViews();
    }

    public BoardFileDTO getDownloadFileDTO(String saveName) {
        BoardFile boardFile = boardFileRepository.findBoardFileBySaveName(saveName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "boardFile Not Found"));
        BoardFileDTO boardFileDTO = createBoardFileDTO(boardFile);
        return boardFileDTO;
    }

//    public boolean isAuthor(Long postId, String username) {
//        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException());
//        Member member = post.getMember();
//        return member.getUsername().equals(username);
//    }

    //TEST
    //username == UUID
    public boolean isAuthor(Long postId, Authentication authentication) {
        CustomOAuth2User oauth2User = (CustomOAuth2User) authentication.getPrincipal();
        String username = (String) (oauth2User.getAttributes().get("username"));
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException());
        Member member = post.getMember();
        return member.getUsername().equals(username);
    }

}
