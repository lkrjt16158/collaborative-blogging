package com.lk.collaborative.blogging.service.impl;

import com.lk.collaborative.blogging.data.domain.Article;
import com.lk.collaborative.blogging.data.domain.ArticleStatus;
import com.lk.collaborative.blogging.data.repository.ArticleRepository;
import com.lk.collaborative.blogging.service.ArticleService;
import com.lk.collaborative.blogging.service.exception.ArticleNotFoundException;
import com.lk.collaborative.blogging.service.model.AddArticleModel;
import com.lk.collaborative.blogging.service.model.ArticleModel;
import com.lk.collaborative.blogging.service.model.UpdateArticleModel;
import com.lk.collaborative.blogging.util.ExceptionMessageUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
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

        //TODO: Check if the user has permission to update the article

        article.setTitle(updateArticleModel.getTitle());
        article.setContent(updateArticleModel.getContent());
        return articleRepository.save(article);

    }

    @Override
    public Article publishArticle(ArticleModel articleModel) {
        String url = articleModel.getUrl();
        Article article = findArticleOrThrowException(url);

        //TODO: Check if the user has permission to publish the article

        article.setArticleStatus(ArticleStatus.PUBLISHED);
        return articleRepository.save(article);
    }

    @Override
    public void deleteArticle(ArticleModel articleModel) {
        String url = articleModel.getUrl();
        Article article = findArticleOrThrowException(url);

        //TODO: Check if the user has permission to delete the article
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
