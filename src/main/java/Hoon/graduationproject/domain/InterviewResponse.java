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
public class InterviewResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String answerContent;
    private String toneAnalysisResult;
    private LocalDateTime responseDate;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private InterviewQuestion question;
}