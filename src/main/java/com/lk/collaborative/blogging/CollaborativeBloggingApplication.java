package com.lk.collaborative.blogging;

import com.lk.collaborative.blogging.data.domain.Gender;
import com.lk.collaborative.blogging.data.domain.Password;
import com.lk.collaborative.blogging.data.domain.Profile;
import com.lk.collaborative.blogging.data.domain.User;
import com.lk.collaborative.blogging.data.repository.ProfileRepository;
import com.lk.collaborative.blogging.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class CollaborativeBloggingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CollaborativeBloggingApplication.class, args);
	}

	@Autowired
	UserRepository userRepository;

	@Autowired
	ProfileRepository profileRepository;

	@Bean
	@org.springframework.context.annotation.Profile("!test")
	ApplicationRunner applicationRunner () {
		return args -> {
			User user = new User("sample", "first", "last", "sample@email.com", Password.of("test"));
			user = userRepository.save(user);
			Profile profile = new Profile(user);
			profile.setGender(Gender.MALE);
			profileRepository.save(profile);

			user = userRepository.findById(user.getId()).get();
			System.out.println(user.getProfile());
		};
	}
}
