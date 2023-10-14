package com.lk.collaborative.blogging.service.impl;

import com.lk.collaborative.blogging.data.domain.Article;
import com.lk.collaborative.blogging.data.domain.ArticleStatus;
import com.lk.collaborative.blogging.data.permission.ArticleAction;
import com.lk.collaborative.blogging.data.permission.ArticlePermissionChecker;
import com.lk.collaborative.blogging.data.repository.ArticleRepository;
import com.lk.collaborative.blogging.service.ArticleService;
import com.lk.collaborative.blogging.service.exception.AnonymousUserException;
import com.lk.collaborative.blogging.service.exception.ArticleNotFoundException;
import com.lk.collaborative.blogging.service.exception.UnauthorizedAccessException;
import com.lk.collaborative.blogging.service.model.AddArticleModel;
import com.lk.collaborative.blogging.service.model.ArticleModel;
import com.lk.collaborative.blogging.service.model.UpdateArticleModel;
import com.lk.collaborative.blogging.util.ExceptionMessageUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticlePermissionChecker permissionChecker;

    public ArticleServiceImpl(ArticleRepository articleRepository, ArticlePermissionChecker permissionChecker) {
        this.articleRepository = articleRepository;
        this.permissionChecker = permissionChecker;
    }

    @Override
    public Article addArticle(AddArticleModel articleModel) {
        String title = articleModel.getTitle();
        String url = generateUrlFromTitle(title);
        Article article = new Article(url, title, articleModel.getContent());
        return articleRepository.save(article);
    }

    @Override
    public Article updateArticle(UpdateArticleModel updateArticleModel) {
        String url = updateArticleModel.getUrl();
        Article article = findArticleOrThrowException(url);

        //permission check
        try {
            if(!permissionChecker.hasPermission(ArticleAction.UPDATE_ARTICLE, article)) {
                throw new UnauthorizedAccessException(ExceptionMessageUtils.unauthorizedArticleAccess(url));
            }
        }catch (AnonymousUserException e) {
            throw new UnauthorizedAccessException(e);
        }

        article.setTitle(updateArticleModel.getTitle());
        article.setContent(updateArticleModel.getContent());
        return articleRepository.save(article);

    }

    @Override
    public Article publishArticle(ArticleModel articleModel) {
        String url = articleModel.getUrl();
        Article article = findArticleOrThrowException(url);

        //permission check
        try {
            if(!permissionChecker.hasPermission(ArticleAction.PUBLISH_ARTICLE, article)) {
                throw new UnauthorizedAccessException(ExceptionMessageUtils.unauthorizedArticleAccess(url));
            }
        }catch (AnonymousUserException e) {
            throw new UnauthorizedAccessException(e);
        }

        article.setArticleStatus(ArticleStatus.PUBLISHED);
        return articleRepository.save(article);
    }

    @Override
    public void deleteArticle(ArticleModel articleModel) {
        String url = articleModel.getUrl();
        Article article = findArticleOrThrowException(url);

        //permission check
        try {
            if(!permissionChecker.hasPermission(ArticleAction.DELETE_ARTICLE, article)) {
                throw new UnauthorizedAccessException(ExceptionMessageUtils.unauthorizedArticleAccess(url));
            }
        }catch (AnonymousUserException e) {
            throw new UnauthorizedAccessException(e);
        }
        articleRepository.delete(article);
    }


    /**
     * @param title Title of the article
     * @return unique url for the article
     */
    private String generateUrlFromTitle(String title) {
        String url = title.toLowerCase();
        return "/" +
                url +
                url.replace(" ", "-") +
                "-" +
                Instant.now().toEpochMilli();
    }

    private Article findArticleOrThrowException(String url) {
        return articleRepository
                .findByUrl(url)
                .orElseThrow(() -> new ArticleNotFoundException(ExceptionMessageUtils.articleNotFound(url)));
    }
}
