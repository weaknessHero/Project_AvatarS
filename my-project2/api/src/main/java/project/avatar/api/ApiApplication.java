package project.avatar.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
//import project.avatar.api.entity.Users;
//import project.avatar.api.repo.UserRepository;

//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;

//@EnableSwagger2
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class ApiApplication /*implements CommandLineRunner*/ {

	@Autowired
	//UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	/*@Override
	public void run(String... args) throws Exception{
		Users users1 = new Users("3", "saedongh2@naver.com", "tpehd13@", "황세동");

		userRepository.save(users1);

		List<Users> result = userRepository.findByUid("saedongh2@naver.com");
		System.out.println(result);
	}*/

	/*@Bean
	public PasswordEncoder passwordEncoder(){
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}*/
}
