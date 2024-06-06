package com.cms.mini_board.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class BoardFile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String originalName;
    private String saveName;
    private Long size;
    private String path;
    private boolean isDeleted;
    private LocalDateTime createdDate;
    private LocalDateTime deletedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public void setPost(Post post) {
        if (this.post != null) {
            this.post.getFiles().remove(this);
        }
        this.post = post;
        if (post != null) {
            this.post.getFiles().add(this);
        }
    }
}
