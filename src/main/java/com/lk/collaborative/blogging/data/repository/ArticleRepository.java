package com.lk.collaborative.blogging.data.repository;

import com.lk.collaborative.blogging.data.domain.Article;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

public interface ArticleRepository extends ListCrudRepository<Article, Long> {

    /**
     * @param title String the article title must contain
     * @param pageable Pagination request
     *
     * @return {@link Page } of published {@link Article}
     */
    @Query("Select a from Article a where a.articleStatus = com.lk.collaborative.blogging.data.domain.ArticleStatus.PUBLISHED" +
            " and a.title like CONCAT('%',:title,'%') order by a.publishedDate desc")
    Page<Article> findArticles(@NonNull String title, Pageable pageable);


}
