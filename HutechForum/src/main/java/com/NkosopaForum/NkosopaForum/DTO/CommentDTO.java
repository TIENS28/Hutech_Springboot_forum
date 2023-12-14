package com.NkosopaForum.NkosopaForum.DTO;

import java.util.ArrayList;
import java.util.List;

public class CommentDTO extends BaseDTO<CommentDTO> {
    private String content;

    private Long userId;

    private Long postId;

    private List<CommentDTO> commentsAnws = new ArrayList<>();

    private UserDTO user;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public List<CommentDTO> getCommentsAnws() {
        return commentsAnws;
    }

    public void setCommentsAnws(List<CommentDTO> commentsAnws) {
        this.commentsAnws = commentsAnws;
    }

    // Getter and setter for UserDTO
    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
