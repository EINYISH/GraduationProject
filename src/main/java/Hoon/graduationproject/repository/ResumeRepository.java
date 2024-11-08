package Hoon.graduationproject.repository;

import Hoon.graduationproject.domain.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
    List<Resume> findByUserId(Long userId); // 특정 사용자의 이력서 조회
}