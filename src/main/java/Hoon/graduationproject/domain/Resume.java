package Hoon.graduationproject.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filePath;
    private LocalDateTime uploadedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Resume(String filePath, LocalDateTime uploadedAt, User user) {
        this.filePath = filePath;
        this.uploadedAt = uploadedAt;
        this.user = user;
    }
}

