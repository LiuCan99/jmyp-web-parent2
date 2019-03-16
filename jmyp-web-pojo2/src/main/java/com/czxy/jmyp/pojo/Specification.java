package com.czxy.jmyp.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Table(name = "tb_specification")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Specification  implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name="spec_name")
    @JsonProperty("spec_name")
    private String spec_name;

    @Column(name="category_id")
    private Integer category_id;
    @Transient
    private Category category;
    @Transient
    private List<SpecificationOption> options;

}
