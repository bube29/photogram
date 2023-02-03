package com.cos.photogramstart.web.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data                       // 전역적으로 쓸꺼라 제네릭으로 선언함.
public class CMRespDto<T> { // 어떤 걸 응답할때 응답은 공통으로 CMRespDto를 쓸 예정.
	private int code; // 1(성공), -1(실패) <- 응답할때의 코드
	private String message;
	private T data;
}
