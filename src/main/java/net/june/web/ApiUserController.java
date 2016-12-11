package net.june.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.june.domain.User;
import net.june.domain.UserRepository;

@RestController
@RequestMapping("/api/users")
public class ApiUserController {
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/{id}")	//해당하는 id를 조회하는 컨트롤러 구현
	public User show(@PathVariable Long id){
		return userRepository.findOne(id);
	}
}
