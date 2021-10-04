package kr.inhatc.spring.user.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import antlr.collections.List;
import kr.inhatc.spring.board.entity.Boards;
import kr.inhatc.spring.board.repository.BoardRepository;
import kr.inhatc.spring.user.entity.Users;

@SpringBootTest //이거 넣어야함 
class UserRepositoryTest {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BoardRepository boardRepository;

	
	@Test
	public void insertTest() {
	for(int i=0;i<100;i++)
	{
		Users user = Users.builder()
				.filename("78534287347100.jpg")
				.id("abc"+(int)(Math.random()*100))
				.pw("111")
				.name("test")
				.email("abc@test.com")
				.role("ROLE_USER")
				.build();
		
		userRepository.save(user);
	}
	}

	@Test
	void testFindAllByOrderByIdDescTest() {
		java.util.List<Users> list = userRepository.findAllByOrderByIdDesc();
		System.out.println("==============test===============");
		for (Users users : list) {
			System.out.println(users);
		}
	}

}
