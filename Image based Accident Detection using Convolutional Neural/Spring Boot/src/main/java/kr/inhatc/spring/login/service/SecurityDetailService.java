package kr.inhatc.spring.login.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kr.inhatc.spring.login.security.SecurityUser;
import kr.inhatc.spring.user.entity.Users;
import kr.inhatc.spring.user.repository.UserRepository;

@Service
public class SecurityDetailService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Users> optional=userRepo.findById(username);																									//원래는 id가 맞지만 그냥 시큐리티 관습임.
		if(optional.isPresent()) {
			Users user = optional.get();
			System.out.println("==========> " + user);
			return new SecurityUser(user);
		}else {
			throw new UsernameNotFoundException(username+" 사 용 자 없음 !!!");
		}
	}
	

}
