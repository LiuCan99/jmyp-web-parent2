package com.czxy.jmyp.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/*
`id` bigint(20) NOT NULL,
  `cat_name` varchar(20) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `is_parent` int(1) DEFAULT NULL,
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_category")
public class Category {
    @Id
    private  Integer id;

    @Column(name = "cat_name")
//    @JsonProperty("cat_name")
    private  String  catName;

    @Column(name = "parent_id")
//    @JsonProperty("parent_id")
    private  Integer parentId;

    @Column(name = "is_parent")
//    @JsonProperty("is_parent")
    private  Boolean isParent;

    private List<Category> children = new ArrayList<>();


}
