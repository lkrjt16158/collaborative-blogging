package com.lk.collaborative.blogging.data.repository;

import com.lk.collaborative.blogging.data.domain.Article;
import com.lk.collaborative.blogging.data.domain.User;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

public interface ArticleRepository extends ListCrudRepository<Article, Long> {

    String SELECT_QUERY = "Select a from Article a";
    String JOIN_WITH_USER = " left join fetch a.authors au ";
    String COMPARE_FOR_PUBLISHED_ARTICLES = " a.articleStatus = com.lk.collaborative.blogging.data.domain.ArticleStatus.PUBLISHED";
    String COMPARE_FOR_TITLE = " a.title like CONCAT('%',:title,'%')";
    String COMPARE_FOR_USER = " au = :user ";
    String ORDER_BY_PUBLISHED_DATE = " order by a.publishedDate desc";
    String AND = " and ";
    String WHERE = " where ";

    /**
     * @param title String the article title must contain
     * @param pageable Pagination request
     *
     * @return {@link Page } of published {@link Article}
     */
    @Query(SELECT_QUERY + WHERE + COMPARE_FOR_PUBLISHED_ARTICLES + AND + COMPARE_FOR_TITLE + ORDER_BY_PUBLISHED_DATE)
    Page<Article> findArticlesByTitle(@NonNull String title, Pageable pageable);


    /**
     * @param title String the articles' title must contain
     * @param user {@link User} who is an author to the articles
     * @param pageable Pagination request
     *
     * @return {@link Page } of published {@link Article} for which user is an author.
     */
    @Query(SELECT_QUERY + JOIN_WITH_USER + WHERE + COMPARE_FOR_PUBLISHED_ARTICLES + AND + COMPARE_FOR_TITLE + AND +
            COMPARE_FOR_USER + ORDER_BY_PUBLISHED_DATE)
    Page<Article> findArticlesByTitleAndUser(@NonNull String title, @NonNull User user, Pageable pageable);


    /**
     *
     * @param title String the articles' title must contain
     * @param user {@link User} who is an author to the articles
     * @param pageable Pagination request
     *
     * @return {@link Page } of all the {@link Article} for which user is an author.
     */
    @Query(SELECT_QUERY + JOIN_WITH_USER  + WHERE + COMPARE_FOR_TITLE + AND + COMPARE_FOR_USER + ORDER_BY_PUBLISHED_DATE)
    Page<Article> findMyArticlesByTitle(@NonNull String title, @NonNull User user, Pageable pageable);

}
