package com.example.space.pdf;

import com.example.space.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.io.ByteArrayOutputStream;

@Entity
@Table(name = "pdf")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pdf {
@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
@ManyToOne
    @JoinColumn(name = "user_id")
private User user;
    @Lob
private byte[] pdf;
}
