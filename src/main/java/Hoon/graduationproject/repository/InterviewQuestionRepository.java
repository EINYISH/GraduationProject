package Hoon.graduationproject.repository;

import Hoon.graduationproject.domain.InterviewQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InterviewQuestionRepository extends JpaRepository<InterviewQuestion, Long> {

    // Resume ID에 해당하는 질문 목록을 조회
    List<InterviewQuestion> findByResumeId(Long resumeId);
}