package Hoon.graduationproject.repository;

import Hoon.graduationproject.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // 필요하면 사용자별 맞춤 조회 메서드 추가 가능
}