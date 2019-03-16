package com.czxy.jmyp.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;


@Table(name = "tb_specification_option")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SpecificationOption  implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name="spec_id")
    @JsonProperty("spec_id")
    private Integer spec_id;
    @Transient
    private Specification specification;

    @Column(name="option_name")
//    @JsonProperty("option_name")
    private String option_name;
}

