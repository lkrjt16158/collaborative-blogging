package com.lk.collaborative.blogging.service.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddArticleModel {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

}
