package com.lk.collaborative.blogging.data.repository;

import com.lk.collaborative.blogging.data.domain.Article;
import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository extends CrudRepository<Article, Long> {
}
