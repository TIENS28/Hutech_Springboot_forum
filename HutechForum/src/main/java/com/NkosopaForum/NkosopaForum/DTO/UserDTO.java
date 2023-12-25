package com.NkosopaForum.NkosopaForum.DTO;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Nullable;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserDTO extends BaseDTO<UserDTO> {

	private String firstName;
    private String lastName;
    private String password;
    private String email;
    private RoleDTO role;
    private String fullName;
    private String studentID;
    private String DOB;
    private String department;
//    private List<UserDTO> followingUser =  new ArrayList<>();
//    private List<UserDTO> followerUser = new ArrayList<>();
    private List<PostDTO> userPost = new ArrayList<>();
//    private boolean followed;
    @Nullable
    private MultipartFile avatar;
    
    private String avatarUrl;
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public RoleDTO getRole() {
		return role;
	}

	public void setRole(RoleDTO role) {
		this.role = role;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStudentID() {
		return studentID;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}

	public String getDOB() {
		return DOB;
	}

	public void setDOB(String dOB) {
		DOB = dOB;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
//
//	public List<UserDTO> getFollowingUser() {
//		return followingUser;
//	}
//
//	public void setFollowingUser(List<UserDTO> followingUser) {
//		this.followingUser = followingUser;
//	}
//
//	public List<UserDTO> getFollowerUser() {
//		return followerUser;
//	}
//
//	public void setFollowerUser(List<UserDTO> followerUser) {
//		this.followerUser = followerUser;
//	}
//
//	public boolean isFollowed() {
//		return followed;
//	}
//
//	public void setFollowed(boolean followed) {
//		this.followed = followed;
//	}

	public MultipartFile getAvatar() {
		return avatar;
	}

	public void setAvatar(MultipartFile avatar) {
		this.avatar = avatar;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public List<PostDTO> getUserPost() {
		return userPost;
	}

	public void setUserPost(List<PostDTO> userPost) {
		this.userPost = userPost;
	}
	
}
