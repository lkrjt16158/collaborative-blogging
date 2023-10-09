package com.lk.collaborative.blogging.service.permission;

import com.lk.collaborative.blogging.data.domain.Article;
import com.lk.collaborative.blogging.data.domain.User;
import com.lk.collaborative.blogging.service.permission.action.ArticleAction;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Article as protected resource.
 */
@Service
public class ArticleResource extends Resource<ArticleAction, Article> {

    @Override
    protected boolean hasPermission(ArticleAction action, Article article, User loggedInUser) {
        Set<User> authors = article.getAuthors();
        switch (action) {
            case ADD_ARTICLE -> {
                return true;
            }
            case UPDATE_ARTICLE -> {
                return authors.contains(loggedInUser);
            }
            case DELETE_ARTICLE -> {}
            case PUBLISH_ARTICLE  -> {
                return article.getCreator().equals(loggedInUser);
            }
            default -> {
                return false;
            }
        }
        return false;
    }
}
