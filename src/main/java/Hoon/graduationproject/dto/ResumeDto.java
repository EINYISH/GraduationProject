package Hoon.graduationproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumeDto {
    private Long id;                   // 이력서 ID
    private MultipartFile file;        // 업로드된 이력서 파일
    private String filePath;           // 서버에 저장된 이력서 파일 경로 (응답 시 사용)
}