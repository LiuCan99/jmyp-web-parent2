package com.czxy.jmyp.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 *
 Create Table

 CREATE TABLE `tb_news` (
 `id` int(11) NOT NULL,
 `title` varchar(20) DEFAULT NULL,
 `content` text,
 `author` varchar(255) DEFAULT NULL,
 `created_at` date DEFAULT NULL,
 `updated_at` date DEFAULT NULL,
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "tb_news")
public class News {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;
    @Column(name = "author")
    private String author;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAt;


}

