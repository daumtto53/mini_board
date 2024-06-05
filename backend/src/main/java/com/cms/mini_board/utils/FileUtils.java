package com.cms.mini_board.utils;

import com.cms.mini_board.entity.BoardFile;
import com.cms.mini_board.entity.Post;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Log4j2
public class FileUtils {

    private final String uploadPath = Paths.get("Z:", "cms", "spring-projects", "mini_board").toString();


    public List<BoardFile> uploadFiles(final List<MultipartFile> multipartFiles, Post post) {
        log.info("FileUtils file path : {}" , uploadPath);
        List<BoardFile> collect = multipartFiles.stream().map(
                        multipartFile -> {
                            return uploadFile(multipartFile, post);
                        })
                .collect(Collectors.toList());
        return collect;
    }

    public BoardFile uploadFile(final MultipartFile multipartFile, Post post) {
        String saveName = generateSaveFilaName(multipartFile.getOriginalFilename());
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd")).toString();
        String uploadPath = getUploadPath(today) + java.io.File.separator + saveName;
        File uploadFileUtils = new File(uploadPath);

        try {
            multipartFile.transferTo(uploadFileUtils);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return BoardFile.builder()
                .post(post)
                .originalName(multipartFile.getOriginalFilename())
                .saveName(saveName)
                .size(multipartFile.getSize())
                .isDeleted(false)
                .createdDate(LocalDateTime.now())
                .build();
    }

    private String generateSaveFilaName(final String filename) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String extension = StringUtils.getFilenameExtension(filename);
        return uuid + "." + extension;
    }

    private String getUploadPath() {
        return makeDirectories(uploadPath);
    }

    private String getUploadPath(final String addPath) {
        return makeDirectories(uploadPath + java.io.File.separator + addPath);
    }

    private String makeDirectories(final String path) {
        java.io.File dir = new java.io.File(path);
        if (dir.exists() == false) {
            dir.mkdirs();
        }
        return dir.getPath();
    }





}
