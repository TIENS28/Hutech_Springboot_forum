package com.NkosopaForum.NkosopaForum.DTO;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Nullable;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PostDTO extends BaseDTO<PostDTO> {

    private String title;
    private String description;
    private String content;
	private List<CommentDTO> comments = new ArrayList<>();
    private UserDTO user;
    private boolean isLiked;
    private int totalLikes;
    private int totalComments;
    private UserDTO currentUser;
    
    @Nullable
    private MultipartFile thumbnail;
    private String thumbnailUrl;

    public UserDTO getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(UserDTO currentUser) {
        this.currentUser = currentUser;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
    public MultipartFile getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(MultipartFile thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    

    public List<CommentDTO> getComments() {
		return comments;
	}

	public void setComments(List<CommentDTO> comments) {
		this.comments = comments;
	}

	public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
    
    public String getAuthorFirstName() {
        if (user != null) {
            return user.getFirstName();
        } else {
            return "Unknown"; 
        }
    }

    public String getAuthorLastName() {
        if (user != null) {
            return user.getLastName();
        } else {
            return "Unknown"; 
        }
    }

    public String getAuthorFullName() {
        if (user != null) {
            return user.getFirstName() + " " + user.getLastName();
        } else {
            return "Unknown"; 
        }
    }

	public int getTotalLikes() {
		return totalLikes;
	}

	public void setTotalLikes(int totalLikes) {
		this.totalLikes = totalLikes;
	}

	public int getTotalComments() {
		return totalComments;
	}

	public void setTotalComments(int totalComments) {
		this.totalComments = totalComments;
	}

	public boolean isLiked() {
		return isLiked;
	}

	public void setLiked(boolean isLiked) {
		this.isLiked = isLiked;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}
   
}
