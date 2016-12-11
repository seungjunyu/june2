package net.june.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.june.domain.Answer;
import net.june.domain.AnswerRepository;
import net.june.domain.Question;
import net.june.domain.QuestionRepository;
import net.june.domain.Result;
import net.june.domain.User;
import net.june.web.HttpSessionUtils;

//@Controller
@RestController	//JSON API로 인식하기위함.
@RequestMapping("/api/questions/{questionId}/answers")
public class AnswerController {
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerRepository;
	
	@PostMapping("")	//질문상세에서 답변하기 클릭
	//public String create(@PathVariable Long questionId, String contents, HttpSession session){	
	public Answer create(@PathVariable Long questionId, String contents, HttpSession session){		
		if (!HttpSessionUtils.isLoginUser(session)){	//로그인을 안하면 질문하기 url경로로 접근해도 안되도록 백엔드에서 구현
			//return "/users/loginForm";
			return new Answer();
		}
		
		User loginUser = HttpSessionUtils.getUserFromSession(session);		
		Question question = questionRepository.findOne(questionId);
		Answer answer = new Answer(loginUser, question, contents);
		//Answer persistAnswer = answerRepository.save(answer);
		question.addAnswer();
		return answerRepository.save(answer);
		//return String.format("redirect:/questions/%d", questionId);
	}
	
	@DeleteMapping("/{id}")	//질문상세에서 답변삭제 클릭
	public Result delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			//return "/user/login";
			return Result.fail("로그인 사용자만 답변 삭제가 가능합니다.");
		}
		
		Answer answer = answerRepository.findOne(id);
		User loginUser = HttpSessionUtils.getUserFromSession(session);		
		if(!answer.isSameWriter(loginUser)){
			return Result.fail("자신의 글만 답변 삭제가 가능합니다.");
		}
		
		//answer.delete(loginUser);
		//answerRepository.save(answer);
		answerRepository.delete(id);
		Question question = questionRepository.findOne(questionId);
		question.deleteAnswer();
		questionRepository.save(question);
		return Result.ok();
		//return answerRepository.save(answer);
		//return String.format("redirect:/questions/%d", questionId);
	}	
}
