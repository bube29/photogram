package com.cos.photogramstart.util;

public class Script {

	public static String back(String msg) {
		StringBuffer sb = new StringBuffer();
		sb.append("<script>");
		sb.append("alert('"+msg+"');"); // 알림창 메세지 띄우기 / 자바스크립트에 자바를 넣으려면 변수 앞, 뒤로 +를 넣어야한다. / alert() <- () 안에 ' ' 안에 " " 안에 + + 안에 넣어야한다!
		sb.append("history.back();"); // 뒤로 돌아가기
		sb.append("</script>");
		return sb.toString(); // script라는 코드를 만들어서 리턴하는 메서드
	}
}
