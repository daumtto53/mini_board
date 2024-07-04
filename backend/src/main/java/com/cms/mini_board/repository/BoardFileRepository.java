package com.cms.mini_board.repository;

import com.cms.mini_board.entity.BoardFile;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BoardFileRepository extends JpaRepository<BoardFile, Long> {

    @Modifying
    @Query("DELETE FROM BoardFile f WHERE f.post.postId = :postId")
    void deleteAllFileByPostId(@Param("postId") Long postId);

    @Query("SELECT f FROM BoardFile f WHERE f.post.postId = :postId")
    List<BoardFile> findBoardFilesByPostId(@Param("postId") Long postId);

    Optional<BoardFile> findBoardFileBySaveName(String saveName);
}
