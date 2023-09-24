package com.lk.collaborative.blogging.data;

import com.lk.collaborative.blogging.AbstractTest;
import com.lk.collaborative.blogging.data.domain.Article;
import com.lk.collaborative.blogging.data.domain.ArticleStatus;
import com.lk.collaborative.blogging.data.domain.Password;
import com.lk.collaborative.blogging.data.domain.User;
import com.lk.collaborative.blogging.data.repository.ArticleRepository;
import com.lk.collaborative.blogging.data.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest
public class ArticlePaginationTests extends AbstractTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ArticleRepository articleRepository;


    @BeforeEach
    public void setup() {
        List<User> users = IntStream.range(0, 10).mapToObj(i -> new User("user-name-" + i,
                "first-name" + i,
                "last-name" + i,
                "email"+i+"@email.com",
                Password.of("test"))).collect(Collectors.toList());
        userRepository.saveAll(users);

        List<Article> articles = IntStream.range(0, 100).mapToObj(i -> {
            User currentUser = users.get(i % 10);
            setUpSecurityContext(currentUser);
            Article article =  new Article("/url" + i,
                    "title",
                    "content");

            //Publishing half the number of articles
            if(i%2 == 0) {
                article.setArticleStatus(ArticleStatus.PUBLISHED);
                if(i%4 == 0) {
                    //Setting matching string for 25% articles
                    article.setTitle("matchingString");
                }
            }

            return article;
        }).collect(Collectors.toList());

        articleRepository.saveAll(articles);
    }

    @AfterEach
    public void remove() {
        articleRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void checkIfOnlyPublishedArticlesAreFetched() {
        Page<Article> articles = articleRepository.findArticles("", PageRequest.of(0, 5));
        Assertions.assertEquals(50, articles.getTotalElements());
    }

    @Test
    public void findArticlesByTitle() {
        Page<Article> articles = articleRepository.findArticles("match", PageRequest.of(0, 5));
        Assertions.assertEquals(25, articles.getTotalElements());
    }



}
