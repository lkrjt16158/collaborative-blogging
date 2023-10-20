package com.lk.collaborative.blogging.service.impl;

import com.lk.collaborative.blogging.AbstractTest;
import com.lk.collaborative.blogging.data.domain.Article;
import com.lk.collaborative.blogging.data.domain.ArticleStatus;
import com.lk.collaborative.blogging.data.domain.Password;
import com.lk.collaborative.blogging.data.domain.User;
import com.lk.collaborative.blogging.data.repository.ArticleRepository;
import com.lk.collaborative.blogging.data.repository.UserRepository;
import com.lk.collaborative.blogging.service.ArticleService;
import com.lk.collaborative.blogging.service.exception.ArticleAlreadyPublishedException;
import com.lk.collaborative.blogging.service.exception.UnauthorizedAccessException;
import com.lk.collaborative.blogging.service.model.AddArticleModel;
import com.lk.collaborative.blogging.service.model.ArticleModel;
import com.lk.collaborative.blogging.service.model.UpdateArticleModel;
import com.lk.collaborative.blogging.util.ExceptionMessageUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashSet;
import java.util.Optional;

import static org.mockito.BDDMockito.*;

@SpringBootTest
class ArticleServiceImplTest extends AbstractTest {

    @MockBean
    UserRepository userRepository;

    @MockBean
    ArticleRepository articleRepository;

    @Autowired
    ArticleService articleService;

    User user1;

    User user2;

    Article article;

    @BeforeEach
    public void beforeEach() {
        user1 = new User("gandalf.grey", "Gandalf", "Grey","gandalf.grey@abc.com", Password.of("test"));
        ReflectionTestUtils.setField(user1, "id", 1L);

        user2 = new User("gandalf.white", "Gandalf", "White","gandalf.white@abc.com", Password.of("test"));
        ReflectionTestUtils.setField(user2, "id", 2L);

        article = new Article("/hello-world", "Hello World", "Hello World");
        article.setArticleStatus(ArticleStatus.DRAFT);
        ReflectionTestUtils.setField(article, "id", 1L);
    }

    @Test
    public void NoExceptionIsThrownWhenAddingArticle() {
        //Set user1 as article creator and author
        ReflectionTestUtils.setField(article, "creator", user1);
        HashSet<User> authors = new HashSet<>();
        authors.add(user1);
        ReflectionTestUtils.setField(article, "authors", authors);

        //mock updating article
        when(articleRepository.save(any()))
                .thenReturn(article);

        AddArticleModel addArticleModel = new AddArticleModel();
        addArticleModel.setTitle(article.getTitle());
        addArticleModel.setContent(article.getContent());

        //set user1 as logged in user
        setUpSecurityContext(user1);
        Assertions.assertDoesNotThrow(() -> articleService.addArticle(addArticleModel));
    }

    /**
     * Validates that only authors can make changes to the article
     */
    @Test
    public void throwsExceptionForUnauthorizedArticleUpdate() {
        //Set user1 as article creator and author
        ReflectionTestUtils.setField(article, "creator", user1);
        HashSet<User> authors = new HashSet<>();
        authors.add(user1);
        ReflectionTestUtils.setField(article, "authors", authors);

        //mock fetching article
        given(articleRepository.findByUrl(article.getUrl()))
                .willReturn(Optional.of(article));

        //Model for updating article
        UpdateArticleModel articleModel = new UpdateArticleModel();
        articleModel.setUrl(article.getUrl());
        articleModel.setTitle("Updated title");
        articleModel.setContent("Updated content");

        //Mock updating article
        Article updatedArticle = new Article(articleModel.getUrl(), articleModel.getTitle(), articleModel.getContent());
        ReflectionTestUtils.setField(article, "id", article.getId());
        when(articleRepository.save(any()))
                .thenReturn(updatedArticle);

        //Set current loggedIn user as user1
        setUpSecurityContext(user1);

        //Will not throw exception if user1 updates the article
        Assertions.assertDoesNotThrow(() -> articleService.updateArticle(articleModel));

        //Set current logged in user as user2
        setUpSecurityContext(user2);
        Assertions.assertThrowsExactly(UnauthorizedAccessException.class, () -> articleService.updateArticle(articleModel),
                ExceptionMessageUtils.unauthorizedArticleAccess(article.getUrl()));

    }

    /**
     * Validates that only creator of the article is allowed to publish article
     */
    @Test
    public void throwsExceptionForUnauthorizedArticlePublish() {
        //Set user1 as article creator
        ReflectionTestUtils.setField(article, "creator", user1);
        HashSet<User> authors = new HashSet<>();
        //Set both users as authors of the article
        authors.add(user1);
        authors.add(user2);
        ReflectionTestUtils.setField(article, "authors", authors);

        //mock fetching article
        given(articleRepository.findByUrl(article.getUrl()))
                .willReturn(Optional.of(article));

        //Model for updating article
        ArticleModel articleModel = new UpdateArticleModel();
        articleModel.setUrl(article.getUrl());

        //mock updating article
        when(articleRepository.save(any()))
                .thenReturn(article);

        //Set current loggedIn user as user1
        setUpSecurityContext(user1);

        //checks if user1 can publish article
        Assertions.assertDoesNotThrow(() -> articleService.publishArticle(articleModel));

        //set article to draft to bypass publish check
        article.setArticleStatus(ArticleStatus.DRAFT);

        //set current loggedin user as user2
        setUpSecurityContext(user2);
        //checks if user 2 can publish article
        Assertions.assertThrowsExactly(UnauthorizedAccessException.class, () -> articleService.publishArticle(articleModel), ExceptionMessageUtils.unauthorizedArticleAccess(article.getUrl()));

    }

    @Test
    public void throwsExceptionWhenTryingToPublishAlreadyPublishedArticle() {
        //Set user1 as article creator and author
        ReflectionTestUtils.setField(article, "creator", user1);
        HashSet<User> authors = new HashSet<>();
        authors.add(user1);
        ReflectionTestUtils.setField(article, "authors", authors);

        //mock fetching article
        given(articleRepository.findByUrl(article.getUrl()))
                .willReturn(Optional.of(article));

        //Model for updating article
        ArticleModel articleModel = new UpdateArticleModel();
        articleModel.setUrl(article.getUrl());

        //mock updating article
        when(articleRepository.save(any()))
                .thenReturn(article);

        setUpSecurityContext(user1);

        //publish article the first time
        Assertions.assertDoesNotThrow(() -> articleService.publishArticle(articleModel));

        //publish the second time
        Assertions.assertThrowsExactly(ArticleAlreadyPublishedException.class, () -> articleService.publishArticle(articleModel), ExceptionMessageUtils.articleAlreadyPublished(article.getUrl()));
    }

    /**
     * Validates that only creator of the article is allowed to delete article
     */
    @Test
    public void throwsExceptionForUnauthorizedArticleDeletion() {
        //Set user1 as article creator
        ReflectionTestUtils.setField(article, "creator", user1);
        HashSet<User> authors = new HashSet<>();
        //Set both users as authors of the article
        authors.add(user1);
        authors.add(user2);
        ReflectionTestUtils.setField(article, "authors", authors);

        //mock fetching article
        given(articleRepository.findByUrl(article.getUrl()))
                .willReturn(Optional.of(article));

        //Model for updating article
        ArticleModel articleModel = new UpdateArticleModel();
        articleModel.setUrl(article.getUrl());

        //mock updating article
        when(articleRepository.save(any()))
                .thenReturn(article);

        //Set current loggedIn user as user1
        setUpSecurityContext(user1);

        //checks if user1 can publish article
        Assertions.assertDoesNotThrow(() -> articleService.deleteArticle(articleModel));

        //set current loggedin user as user2
        setUpSecurityContext(user2);
        //checks if user 2 can delete article
        Assertions.assertThrowsExactly(UnauthorizedAccessException.class, () -> articleService.deleteArticle(articleModel), ExceptionMessageUtils.unauthorizedArticleAccess(article.getUrl()));

    }

}