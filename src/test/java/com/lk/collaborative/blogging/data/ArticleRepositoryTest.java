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
    private User gandalf;
    private Article article;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ArticleRepository articleRepository;

    @BeforeEach
    public void setup() {
        creator = new User("john.doe", "John", "Doe", "john.doe@email.com", Password.of("test"));
        gandalf = new User("gandalf.grey", "Gandalf", "Grey","gandalf.grey@email.com", Password.of("YouShallNotPass"));
        article = new Article("/abc", "ABC", "ABC");

        //add creator
        creator = userRepository.save(creator);

        //Set creator as the authenticated user
        setUpSecurityContext(creator);

        //add article
        article = articleRepository.save(article);

    }

    @AfterEach
    public void remove() {
        articleRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void saveArticle() {
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

        gandalf = userRepository.save(gandalf);

        LocalDateTime currentModifiedDate = article.getLastModifiedDate();
        LocalDateTime currentCreateDate = article.getCreatedDate();

        //Set newUser as the currentUser instead of creator
        setUpSecurityContext(gandalf);

        //update article
        article.setContent("Changed content");
        article = articleRepository.save(article);

        //Checks if creator is still the creator
        Assertions.assertEquals(creator, article.getCreator());
        //Checks if last modified by is set to the new user
        Assertions.assertEquals(gandalf, article.getLastModifiedBy());

        //Check if last modified date is updated
        Assertions.assertTrue(article.getLastModifiedDate().isAfter(currentModifiedDate));

        Assertions.assertTrue(article.getCreatedDate().isEqual(currentCreateDate));

    }

    @Test
    public void addAuthor() {
        Assertions.assertEquals(article.getAuthors().size(), 1);
        gandalf = userRepository.save(gandalf);
        article.addAuthor(gandalf);
        article = articleRepository.save(article);
        Assertions.assertEquals(2, article.getAuthors().size());
        Assertions.assertTrue(article.getAuthors().contains(creator));
        Assertions.assertTrue(article.getAuthors().contains(gandalf));

    }






}