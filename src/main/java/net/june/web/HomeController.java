package net.june.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import net.june.domain.QuestionRepository;

@Controller
public class HomeController {
	@Autowired	//spring에게 사용요청을 한다.
	private QuestionRepository questionRepository;
	
	@GetMapping("")	//질문목록 = index페이지 = 첫페이지
	public String home(Model model){
		model.addAttribute("questions", questionRepository.findAll());
		return "index";
	}
}
