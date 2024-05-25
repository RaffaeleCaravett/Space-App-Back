package com.example.space.pianeti;

import com.example.space.enums.Galassia;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "planets")
@Getter
@Setter
public class Pianeta {
@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
private String nome;
@Enumerated(EnumType.STRING)
private Galassia galassia;
}
