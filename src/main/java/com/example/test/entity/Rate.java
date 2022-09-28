package com.example.test.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter

@Entity
@Table(name=("rates"))
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = ("score"))
    private Double score;
    @ManyToOne
    @JoinColumn(name = ("user_id"), insertable = false, updatable = false)
    private User user;
    @Column(name = ("user_id"))
    private Integer user_id;
    @ManyToOne
    @JoinColumn(name = ("movie_id"), insertable = false, updatable = false)
    private Movie movie;
    @Column(name = ("movie_id"))
    private Integer movieId;
}
