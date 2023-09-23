package com.lk.collaborative.blogging;

import com.lk.collaborative.blogging.data.domain.*;
import com.lk.collaborative.blogging.data.repository.ArticleRepository;
import com.lk.collaborative.blogging.data.repository.ProfileRepository;
import com.lk.collaborative.blogging.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class CollaborativeBloggingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CollaborativeBloggingApplication.class, args);
	}

	@Autowired
	UserRepository userRepository;

	@Autowired
	ArticleRepository articleRepository;

	@Autowired
	ProfileRepository profileRepository;

	@Bean
	ApplicationRunner applicationRunner() {
		return args -> {
			User user1 = new User("user1" , "a" ,"b","user1@email.com", Password.of("test"));
			User user2 = new User("user2" , "a" ,"b","user2@email.com", Password.of("test"));
			User user3 = new User("user3" , "a" ,"b","user3@email.com", Password.of("test"));
			user1 = userRepository.save(user1);
			//user2 = userRepository.save(user2);
			//user3 = userRepository.save(user3);

			Article article = new Article("url","title","content");
			article.setCreator(user1);
			article.addAuthor(user2);
			article.addAuthor(user3);
			//article = articleRepository.save(article);

			//article.getAuthors().forEach(a -> System.out.println(((User)a).getFirstName()));

			Profile profile = new Profile();
			profile.setGender(Gender.MALE);
			profile.setUser(user1);
			profileRepository.save(profile);

			user1 = userRepository.findById(user1.getId()).get();
			System.out.println("Lazy loading profile");
			System.out.println(user1.getProfile());


		};
	}

}
