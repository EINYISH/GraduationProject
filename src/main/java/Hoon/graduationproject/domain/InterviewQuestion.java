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
public class InterviewQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String questionContent;
    private String category;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;
}