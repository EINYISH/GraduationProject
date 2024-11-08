package Hoon.graduationproject.repository;

import Hoon.graduationproject.domain.InterviewResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewResponseRepository extends JpaRepository<InterviewResponse, Long> {
    // 필요하면 답변별 맞춤 조회 메서드 추가 가능
}