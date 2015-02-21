package com.hightml.scanman.value;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

/**
 * Please enter description here.
 * <p>
 * User: marcel
 * Date: 20/02/15
 * Time: 12:58
 * <p>
 * Copyright by HighTML.
 */
@Data
@Slf4j
@Entity
@RequiredArgsConstructor
@ToString(exclude="category", includeFieldNames=true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Keyword {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    private String value;


    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Category category;

    public Keyword(Category category, String value) {
        this.category = category;
        this.value = value;
    }
}
