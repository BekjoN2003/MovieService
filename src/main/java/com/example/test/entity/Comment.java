package com.example.test.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter

@Entity
@Table(name = ("comments"))
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String content;
    @ManyToOne
    @JoinColumn(name = ("user_id"), insertable = false, updatable = false)
    private User user;
    @Column(name = ("user_id"))
    private Integer userId;
    @ManyToOne
    @JoinColumn(name = ("movie_id"), insertable = false, updatable = false)
    private Movie movie;
    @Column(name = ("movie_id"))
    private Integer movieId;
}
