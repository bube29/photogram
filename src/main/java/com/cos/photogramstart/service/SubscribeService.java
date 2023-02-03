package com.cos.photogramstart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubscribeService {

	private final SubscribeRepository subscribeRepository;
	
	@Transactional // Insert하여 데이터베이스에 영향을 주니깐..
	public void 구독하기(int fromUserId, int toUserId) {
		try {
			subscribeRepository.mSubscribe(fromUserId, toUserId);
			// 데이터베이스쪽에서 오류나면 mSubscribe에서 Exception이 터짐. 추후 이부분을 try-catch로 묶어서 Handler에서 Exception을 처리하도록 만들 예정이라 int로 1(성공), -1(오류) 처리 안함.
		} catch (Exception e) {
			throw new CustomApiException("이미 구독을 하였습니다.");
		}

	} 
	
	@Transactional // Delete하여 데이터베이스에 영향을 주니깐..
	public void 구독취소하기(int fromUserId, int toUserId) {
		subscribeRepository.mUnSubscribe(fromUserId, toUserId); // 특별히 오류가 날 일이 없음. 추후 이 부분으로 인해 오류가 나면 그때 try-catch로 묶기.
	}
}
