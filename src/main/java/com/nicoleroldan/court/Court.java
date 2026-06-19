package com.nicoleroldan.court;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

@Entity
public class Court {

    @Id
    @GeneratedValue
    public Long id;

    @Column(nullable = false)
    public String name;

    @Column(nullable = false)
    public String sport;

    @Column(nullable = false)
    public boolean available = true;
}