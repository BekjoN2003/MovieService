package com.example.test.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter

@Entity
@Table(name = ("movies"))
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = ("creator_id"), insertable = false, updatable = false)
    private User user;

    @Column(name = ("creator_id"))
    private Integer creatorId;
    private LocalDateTime createdAt;

    @Column(name = ("visible"))
    private Integer visible;

    @Column(name = ("deleted_at"))
    private LocalDateTime deletedAt;

    private Boolean status;
}
