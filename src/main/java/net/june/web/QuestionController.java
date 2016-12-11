package net.june.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.june.domain.Question;
import net.june.domain.QuestionRepository;
import net.june.domain.User;

@Controller
@RequestMapping("/questions")
public class QuestionController {	
	
	//private List<Question> questions = new ArrayList<Question>();
	
	@Autowired	//spring에게 사용요청을 한다.
	private QuestionRepository questionRepository;
		
	@GetMapping("/form")	//질문하기 클릭
	public String question(HttpSession session){
		if (!HttpSessionUtils.isLoginUser(session)){	//로그인을 안하면 질문하기 url경로로 접근해도 안되도록 백엔드에서 구현
			return "/users/loginForm";					
		}
		return "qna/form";
	}
	
	//@PostMapping("/questions")	//질문하기 - 제목, 내용      
	@PostMapping("")	//질문하기 - 제목, 내용
	public String create(String title, String contents, HttpSession session){
	//public String create(Question question){
		if (!HttpSessionUtils.isLoginUser(session)){	//로그인을 안하면 질문하기 url경로로 접근해도 안되도록 백엔드에서 구현
			return "/users/loginForm";					
		}
		//System.out.println("questions:"+questions);
		User sessionUser = HttpSessionUtils.getUserFromSession(session);	//글쓴이는 이곳에서 가져올수 있다.
		//Question newQuestion = new Question(sessionUser.getUserId(), title, contents);
		Question newQuestion = new Question(sessionUser, title, contents);
		
		//questions.add(question);
		questionRepository.save(newQuestion);
		return "redirect:/";
	}
	
	@GetMapping("/{id}")	//질문목록에서 글쓴이 클릭하여 상세보기
	public String show(@PathVariable Long id, Model model){
		//Question question = questionRepository.findOne(id);
		model.addAttribute("question", questionRepository.findOne(id));		
		return "/qna/show";
	}
	
	@GetMapping("/{id}/form") //질문상세에서 수정클릭
	public String updateForm(@PathVariable Long id, Model model, HttpSession session){
		if (!HttpSessionUtils.isLoginUser(session)){	//로그인을 안하면 질문하기 url경로로 접근해도 안되도록 백엔드에서 구현
			return "/users/loginForm";					
		}
		User sessionUser = HttpSessionUtils.getUserFromSession(session);
		//model.addAttribute("question", questionRepository.findOne(id));
		Question question = questionRepository.findOne(id);
		
		if(!question.isSameWriter(sessionUser)){
			return "/users/loginForm";		
		}
		model.addAttribute("question", question);	//마우스오른쪽 Refactor > Extract Local Variable ...

		
		return "/qna/updateForm";
	}
	
	@PutMapping("/{id}") //질문 수정화면에서의 수정
	public String update(@PathVariable Long id, String title, String contents, HttpSession session){
		if (!HttpSessionUtils.isLoginUser(session)){	//로그인을 안하면 질문하기 url경로로 접근해도 안되도록 백엔드에서 구현
			return "/users/loginForm";					
		}
		User sessionUser = HttpSessionUtils.getUserFromSession(session);		
		Question question = questionRepository.findOne(id);
		
		if(!question.isSameWriter(sessionUser)){
			return "/users/loginForm";		
		}
		
		question.update(title, contents);
		questionRepository.save(question);
		return String.format("redirect:/questions/%d", id);
	}
	
	@DeleteMapping("/{id}") //질문 상세화면에서의 삭제
	public String delete(@PathVariable Long id, HttpSession session){
		if (!HttpSessionUtils.isLoginUser(session)){	//로그인을 안하면 질문하기 url경로로 접근해도 안되도록 백엔드에서 구현
			return "/users/loginForm";					
		}
		
		User sessionUser = HttpSessionUtils.getUserFromSession(session);		
		Question question = questionRepository.findOne(id);
		
		if(!question.isSameWriter(sessionUser)){
			return "/users/loginForm";		
		}
		
		questionRepository.delete(id);
		return "redirect:/";
	}
	
	
}
