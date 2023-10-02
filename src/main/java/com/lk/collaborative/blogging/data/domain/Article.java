package com.lk.collaborative.blogging.data.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Article extends AbstractPersistable<Long> {

    /**
     * @param url Unique url for the articlw
     * @param title Title
     * @param content Content
     */
    public Article(String url, String title, String content ) {
        this.url = url;
        this.title = title;
        this.content = content;
    }

    @Column(unique = true, nullable = false)
    private String url;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(name = "status", nullable = false)
    private ArticleStatus articleStatus = ArticleStatus.DRAFT;

    @ManyToMany(targetEntity = User.class)
    @JoinTable(name = "ARTICLE_AUTHOR",
        joinColumns = @JoinColumn(name = "ARTICLE_ID", referencedColumnName = "ID"),
        inverseJoinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"))
    private final Set<User> authors = new HashSet<>();

    @ManyToOne
    @CreatedBy
    private User creator;

    @ManyToOne
    @LastModifiedBy
    private User lastModifiedBy;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    private LocalDateTime publishedDate;

    @PrePersist
    protected void addCreatorAsAuthor() {
        this.authors.add(creator);
    }

    public void addAuthor(User author) {
        this.authors.add(author);
    }


}
