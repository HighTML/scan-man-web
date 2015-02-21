package com.hightml.scanman.value;

/**
 * Please enter description here.
 * <p/>
 * User: marcel
 * Date: 07/02/15
 * Time: 23:54
 * <p/>
 * Copyright by HighTML.
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
//@ToString(exclude = "parent", includeFieldNames = true)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;




    @JsonIgnore
    @ManyToOne
    private Category parent;

    @Transient
    private Integer parentId;


    @NonNull
    private String displayName;

    private String explanation;

    @NonNull
    @Column(unique = true)
    private String code;

    private String directory; // if scan is physically moved, to which directory

    @OneToMany(targetEntity = Keyword.class, mappedBy = "category", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Keyword> categoryKeywords; // excluding those of parent, those are implicit


    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Category> childCategories;



    @Override
    public String toString() {
        if (parent != null) {
            return code + "(" + parent.toString() + ")";
        } else {
            return code;
        }
    }

    public Integer getParentId() {
        if (parent != null) {
            return parent.getId();
        } else {
            return null;
        }
    }


    public void setParent(Category parent) {
        log.debug(this + " setParent(" + parent + ")");
        this.parent = parent;
        parent.addChildCategory(this);
    }

    public void addChildCategory(Category child) {
        log.debug(this + " addChild(" + child + ")");
        if (childCategories == null) {
            childCategories = new ArrayList<>();
        }
        childCategories.add(child);
    }



    public void setCategoryKeywords(List<String> keys) {
        categoryKeywords = new ArrayList<>();
        for (String s : keys) {
            categoryKeywords.add(new Keyword(this, s));
        }
    }

}

