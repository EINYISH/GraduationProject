package Hoon.graduationproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterviewResponseDto {
    private Long questionId;           // 응답할 질문의 ID
    private String answerContent;      // 사용자가 입력한 답변 내용
    private MultipartFile audioFile;   // 답변을 녹음한 음성 파일 (톤 분석용)
}