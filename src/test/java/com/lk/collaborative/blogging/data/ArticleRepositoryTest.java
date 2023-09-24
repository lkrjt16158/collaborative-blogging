package com.lk.collaborative.blogging.data;

import com.lk.collaborative.blogging.AbstractTest;
import com.lk.collaborative.blogging.data.domain.Article;
import com.lk.collaborative.blogging.data.domain.Password;
import com.lk.collaborative.blogging.data.domain.User;
import com.lk.collaborative.blogging.data.repository.ArticleRepository;
import com.lk.collaborative.blogging.data.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
@Slf4j
class ArticleRepositoryTest extends AbstractTest {

    private User creator;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ArticleRepository articleRepository;

    @BeforeEach
    public void setup() {
        creator = new User("john.doe", "John", "Doe", "john.doe@email.com", Password.of("test"));

        //Set creator as the authenticated user
        setUpSecurityContext(creator);
    }

    @AfterEach
    public void remove() {
        articleRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void saveArticle() {
        creator = userRepository.save(creator);
        Article article = new Article("/abc", "ABC", "ABC");
        article = articleRepository.save(article);
        Assertions.assertNotNull(article);
        Assertions.assertNotNull(article.getId());

        //Created date
        log.info(article.getCreatedDate().toString());
        Assertions.assertNotNull(article.getCreatedDate());

        //Modified date
        log.info(article.getLastModifiedDate().toString());
        Assertions.assertNotNull(article.getLastModifiedDate());

        //Created by
        log.info(article.getCreator().toString());
        Assertions.assertEquals(article.getCreator(), creator);

        //Modified by
        log.info(article.getCreator().toString());
        Assertions.assertEquals(article.getLastModifiedBy(), creator);

    }

    @Test
    public void updateArticle() {
        //add creator
        userRepository.save(creator);

        User newUser = new User("gandalf.grey", "Gandalf", "Grey","gandalf.grey@email.com", Password.of("YouShallNotPass"));
        newUser = userRepository.save(newUser);

        //add article
        Article article = new Article("/abc", "ABC", "ABC");
        article = articleRepository.save(article);
        Assertions.assertNotNull(article);
        Assertions.assertNotNull(article.getId());

        LocalDateTime currentModifiedDate = article.getLastModifiedDate();
        LocalDateTime currentCreateDate = article.getCreatedDate();

        //Set newUser as the currentUser instead of creator
        setUpSecurityContext(newUser);

        //update article
        article.setContent("Changed content");
        article = articleRepository.save(article);

        //Checks if creator is still the creator
        Assertions.assertEquals(creator, article.getCreator());
        //Checks if last modified by is set to the new user
        Assertions.assertEquals(newUser, article.getLastModifiedBy());

        //Check if last modified date is updated
        Assertions.assertTrue(article.getLastModifiedDate().isAfter(currentModifiedDate));

        Assertions.assertTrue(article.getCreatedDate().isEqual(currentCreateDate));

    }






}