package com.lk.collaborative.blogging.data.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
public class Article extends AbstractPersistable<Long> {

    /**
     * @param url Unique url for the articlw
     * @param title Title
     * @param content Content
     * @param creator
     */
    public Article(String url, String title, String content, User creator ) {
        this.url = url;
        this.title = title;
        this.content = content;
        this.creator = creator;
        this.authors.add(creator);
    }

    @Column(unique = true, nullable = false)
    private String url;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToMany(targetEntity = User.class)
    @JoinTable(name = "ARTICLE_AUTHOR",
        joinColumns = @JoinColumn(name = "ARTICLE_ID", referencedColumnName = "ID"),
        inverseJoinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"))
    private final Set<User> authors = new HashSet<>();

    @ManyToOne(optional = false)
    private User creator;


    public void addAuthor(User author) {
        this.authors.add(author);
    }


}
