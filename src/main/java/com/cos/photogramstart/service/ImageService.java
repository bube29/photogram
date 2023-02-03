package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {

	private final ImageRepository imageRepository;
	
	@Value("${file.path}") // application.yml에 업로드된 사진 저장할 공간을 정의한 경로
	private String uploadFolder; // private String uploadFolder = "C:/workspace/springbootwork/photogram_upload/"; <- 로 해도 되지만.. 
	
	// 2가지 이상의 로직이 하나의 서비스가 되는걸 트랜젹션(일의 최소 단위)이라고 한다. 
	@Transactional // <- 서비스단에서 DB에 변형을 줄때는 꼭 걸어줘야함. 이유는?  여러가지 insert나 update가 있을 경우.. 다 성공을 해야 트랜잭션이 정상적으로 발동을 해서 Rollback을 안하고 commit을 한다.
	public void 사진업로드(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {
		UUID uuid = UUID.randomUUID();
   // UUID(Universally Unique IDentifier 범용 고유 식별자)란? 네트워크 상에서 고유성이 보장되는 id를 만들기 위한 표준 규약. 유일성이 보장. 128비트 숫자, 8-4-4-4-12로 5개의 그룹으로 구분(ex. 550e8400-e29b-a716-446655440000)
		String imageFileName = uuid+"_"+imageUploadDto.getFile().getOriginalFilename(); // 실제 파일명이 imageFileName로 들어간다.
		
		Path imageFilePath = Paths.get(uploadFolder+imageFileName);
		
		// 통신이 일어나거나, I/O가 일어날때 -> 예외가 발생할 수 있다.
		try {
			Files.write(imageFilePath, imageUploadDto.getFile().getBytes()); // 첫번째 Path, 두번째 실제 이미지 파일을 바이트화해서, 세번째 생략
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// DB의 image 테이블에 저장
		Image image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser()); // imageUploadDto의 내용을 가지고 Image 객체로 변환하는게 필요하다. / imageFileName = 5cf6237d-c404-836b-e55413ed0e49-cat.jpg
	    imageRepository.save(image); // 어차피 void라 아래코드에서 이걸로...
		// Image imageEntity = imageRepository.save(image); <- imageUploadDto를 넣을 수 없고, Image 객체를 넣어야한다.
		// imageUploadDto를 image에 집어넣는 로직이 필요한데.. 그건 데이터트랜스퍼오브젝트(web-dto-image-ImageUploadDto)에서 만들면 된다.
		
		// System.out.println(imageEntity); <- 주석을 풀면 사진등록 오류가 나는 이유는? Image의 Getter가 다 출력이 됨. Image 클래스와 User 클래스를 반복으로 돌아감. User 클래스 출력에서 문제가 발생.
		// imageEntity를 출력할때 sysout 내부적으론 imageEntity.toSting()이 자동으로 호출됨.
	}
}
