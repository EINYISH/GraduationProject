package Hoon.graduationproject.controller;

import Hoon.graduationproject.domain.InterviewQuestion;
import Hoon.graduationproject.dto.InterviewResponseDto;
import Hoon.graduationproject.service.InterviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/interview")
public class InterviewController {

    private final InterviewService interviewService;

    public InterviewController(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    // 이력서 업로드 API (파일 저장 후 resumeId 반환)
    @PostMapping("/upload-resume")
    public ResponseEntity<?> uploadResume(@RequestParam("file") MultipartFile file) {
        Long resumeId = interviewService.saveResume(file);
        return ResponseEntity.ok("이력서 업로드가 완료되었습니다. Resume ID: " + resumeId);
    }

    // GPT API 호출을 통한 질문 생성 API
    @GetMapping("/generate-questions/{resumeId}")
    public ResponseEntity<List<String>> generateQuestions(@PathVariable Long resumeId) {
        List<String> questions = interviewService.generateQuestionsWithResume(resumeId);
        return ResponseEntity.ok(questions);
    }

    // 이력서 ID에 해당하는 질문 목록 조회 API
    @GetMapping("/questions/{resumeId}")
    public ResponseEntity<List<InterviewQuestion>> getQuestions(@PathVariable Long resumeId) {
        List<InterviewQuestion> questions = interviewService.findQuestionsByResumeId(resumeId);
        return ResponseEntity.ok(questions);
    }

    // 답변 제출 API
    @PostMapping("/submit-answer")
    public ResponseEntity<?> submitAnswer(@RequestBody InterviewResponseDto responseDto) {
        interviewService.saveResponse(responseDto);
        return ResponseEntity.ok("답변이 저장되었습니다.");
    }
}