package net.june.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
	//특정 파일을 호출할때는 Get방식을 이용한다.
	@GetMapping("/helloworld")		
	//localhost:8080/helloworld 라고 서버에 요청을 하면
	//컨트롤러가 먼저 받아서 처리한다.
	//아래 welcome파일명을 templates 폴더에서 찾아 해당 파일을 출력한다.	
	//해당 관련 파일들을 import 하려면 ctrl+shift+o
	public String welcome(String name, int age, Model model){
		System.out.println("name:"+name + " age:"+age);
		model.addAttribute("name", name);
		model.addAttribute("age", age);
		return "welcome";
	}
}
