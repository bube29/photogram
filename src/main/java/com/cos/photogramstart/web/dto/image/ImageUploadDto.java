package com.cos.photogramstart.web.dto.image;

import org.springframework.web.multipart.MultipartFile;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data // Getter, Setter
public class ImageUploadDto {
	private MultipartFile file; // MultipartFile 타입에는 @NotBlank가 지원이 안된다.
	private String caption;
	
	public Image toEntity(String postImageUrl, User user) {
		return Image.builder()
				.caption(caption)
				.postImageUrl(postImageUrl) // 정확한 경로가 들어가야함.
				.user(user) // Image 객체는 user 정보가 필요함.
				.build();
	}
}
