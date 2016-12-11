package net.june.web;

import javax.servlet.http.HttpSession;

import net.june.domain.User;

public class HttpSessionUtils {
	public static final String USER_SESSION_KEY = "sessionedUser";	//자바에서는 이 변수는 상수로 인식하게 된다.
	
	public static boolean isLoginUser(HttpSession session) {
		Object sessionedUser = session.getAttribute(USER_SESSION_KEY);
		if (sessionedUser == null) {
			return false;
		}
		return true;
	}
	
	public static User getUserFromSession(HttpSession session) {
		if (!isLoginUser(session)) {
			return null;
		}
		
		//User sessionedUser = (User)session.getAttribute(USER_SESSION_KEY);
		//return sessionedUser;		
		return (User)session.getAttribute(USER_SESSION_KEY);
	}
}
