package net.june.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.june.domain.User;
import net.june.domain.UserRepository;

@Controller
@RequestMapping("/users")	//아래줄에서 중복되는부분을 제거하기 위한 애노테이션
public class UserController {
	//사용자의 목록을 보여주려면 서버에서 사용자의 회원가입 목록을 저장하고 있어야 한다.
	//저장한 데이타들을 보여줘야 하므로 자바의 콜렉션(List > ArrayList)을 이용한다.
	//private List<User> users = new ArrayList<User>();
	
	@Autowired	//spring에게 사용요청을 한다.
	private UserRepository userRepository;
	
	@GetMapping("/form")	//회원가입 클릭
	public String form(){
		return "/user/form";
	}
	
	@GetMapping("/loginForm") //로그인 클릭
	public String loginForm(){
		return "/user/login";
	}
	
	@PostMapping("/login")	//로그인 - 아이디, 비밀번호
	public String login(String userId, String password, HttpSession session){
		User user = userRepository.findByUserId(userId);	//디비에서 id에 해당하는 정보를 가져온다.
		if (user == null){
			System.out.println("Login Failure!");
			return "redirect:/users/loginForm";	//존재하지 않는 사용자이면 로그인화면으로 이동된다.
		}
		
		if (!user.matchPassword(password)) {	//비밀번호가 다르면 로그인화면으로 이동된다.	
			return "redirect/users/loginForm";
		}		
//		if (!password.equals(user.getPassword())){
//			System.out.println("Login Failure!");
//			return "redirect:/users/loginForm";
//		}
		
		System.out.println("Login Success!");
		//session.setAttribute("sessionedUser", user);
		session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);
		
		return "redirect:/";
	}
	
	@GetMapping("/logout")	//로그아웃 클릭
	public String logout(HttpSession session){
		//session.removeAttribute("sessionedUser");
		session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);	
		
		return "redirect:/";
	}
	
	//@GetMapping("/create")
	//@PostMapping("/user/create")
	//@PostMapping("/users")
	@PostMapping("")	//회원가입 - 아이디, 비밀번호, 이름, 이메일
	//public String create(String userId, String password, String name, String email){
	public String create(User user){
		//System.out.println("userId:"+userId + " name:"+name);
		System.out.println("user:"+user);
		//users.add(user);
		userRepository.save(user);
		
		//return "index";
		//return "redirect:/list";
		//return "redirect:/user/list";
		return "redirect:/users";
	}
	
	//@GetMapping("/list")
	//@GetMapping("/user/list")
	//@GetMapping("/users")
	@GetMapping("")	//사람 아이콘 클릭. 사용자목록 조회클릭
	public String list(Model model){
		//model.addAttribute("users", users);
		model.addAttribute("users", userRepository.findAll());
		//return "list";
		return "/user/list";		
	}
	
	@GetMapping("/{id}/form")	//사용자목록에서 수정 클릭
	public String updateForm(@PathVariable Long id, Model model, HttpSession session){	
	//url에서 id값을 얻어오기위해서 @PathVariable 애노테이션을 이용한다.
		
		//Object sessionedUser = session.getAttribute("sessionedUser");
		Object tempUser = session.getAttribute("sessionedUser");
		if (tempUser == null) {	//로그인을 안한 경우
		//if (HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";	//로그인화면으로 이동
		}
			
		User sessionedUser = (User)tempUser;		//(User)로 캐스팅을 한다.
		//User sessionedUser = HttpSessionUtils.getUserFromSession(session);
		if (!sessionedUser.matchId(id)) {
			throw new IllegalStateException("You can't update the another user");
		}		
//		if (!id.equals(sessionedUser.getId())){
//			throw new IllegalStateException("You can't update the another user");
//		}
		
		User user = userRepository.findOne(id);	//id로 디비에서 사용자정보를 조회해온다.
		//User user = userRepository.findOne(sessionedUser.getId());	//자기자신의 정보만 수정할수 있게 된다.
		//model.addAttribute("user", userRepository.findOne(id));
		
		model.addAttribute("user", user);
		return "/user/updateForm";
	}
	
	//@PostMapping("/{id}")
	@PutMapping("/{id}")	//개인정보수정 - 비밀번호입력, 아이디,이름,이메일 노출
	public String update(@PathVariable Long id, User updatedUser, HttpSession session){
		Object tempUser = session.getAttribute("sessionedUser");
		if (tempUser == null) {
			return "redirect:/users/loginForm";
		}
		
		User sessionedUser = (User)tempUser;
		if (!id.equals(sessionedUser.getId())){
			throw new IllegalStateException("You can't update the another user");
		}
		
		User user = userRepository.findOne(id);
		user.update(updatedUser);
		userRepository.save(user);
		return "redirect:/users";
	}
	
	//@GetMapping("/{id}")
	//public String show(@PathVariable String id){
	//	model.addAttribute("users", users);
	//	return "/user/list";
	//}

}
