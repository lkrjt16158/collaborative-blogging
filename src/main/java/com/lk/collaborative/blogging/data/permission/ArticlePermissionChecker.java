package com.lk.collaborative.blogging.data.permission;

import com.lk.collaborative.blogging.data.domain.Article;
import com.lk.collaborative.blogging.data.domain.User;
import com.lk.collaborative.blogging.data.permission.ArticleAction;
import com.lk.collaborative.blogging.permission.PermissionChecker;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Contains permission related details for the articles.
 */
@Service
public class ArticlePermissionChecker extends PermissionChecker<ArticleAction, Article> {

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
