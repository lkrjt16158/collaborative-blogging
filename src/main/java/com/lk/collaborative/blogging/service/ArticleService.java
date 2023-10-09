package com.lk.collaborative.blogging.service;

import com.lk.collaborative.blogging.data.domain.Article;
import com.lk.collaborative.blogging.service.model.AddArticleModel;
import com.lk.collaborative.blogging.service.model.ArticleModel;
import com.lk.collaborative.blogging.service.model.UpdateArticleModel;

/**
 * Article service contract
 */
public interface ArticleService {
    Article addArticle(AddArticleModel addArticleModel);
    Article updateArticle(UpdateArticleModel updateArticleModel);
    Article publishArticle(ArticleModel articleModel);
    void deleteArticle(ArticleModel articleModel);
}
