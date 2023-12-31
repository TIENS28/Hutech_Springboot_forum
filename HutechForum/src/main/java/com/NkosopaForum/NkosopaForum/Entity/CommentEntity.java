package com.NkosopaForum.NkosopaForum.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comment")
public class CommentEntity extends BaseEntity<CommentEntity>{
		
	
		@Column(name = "content")
		private String content;
		
	    @ManyToOne
		@JoinColumn(name = "user_id")
		private User user;
		
	    @ManyToOne
		@JoinColumn(name = "post_id")
		private Post post;
		

}
