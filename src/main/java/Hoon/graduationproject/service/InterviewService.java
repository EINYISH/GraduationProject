package Hoon.graduationproject.service;

import Hoon.graduationproject.domain.Resume;
import Hoon.graduationproject.repository.ResumeRepository;
import Hoon.graduationproject.repository.InterviewQuestionRepository;
import Hoon.graduationproject.repository.InterviewResponseRepository;
import Hoon.graduationproject.util.FileStorageUtil;
import Hoon.graduationproject.util.GPTApiService;
import Hoon.graduationproject.dto.InterviewResponseDto;
import Hoon.graduationproject.domain.InterviewQuestion;
import Hoon.graduationproject.domain.InterviewResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class InterviewService {

    private final ResumeRepository resumeRepository;
    private final InterviewQuestionRepository questionRepository;
    private final InterviewResponseRepository responseRepository;
    private final FileStorageUtil fileStorageUtil;
    private final GPTApiService gptApiService;

    public InterviewService(ResumeRepository resumeRepository, InterviewQuestionRepository questionRepository,
                            InterviewResponseRepository responseRepository, FileStorageUtil fileStorageUtil,
                            GPTApiService gptApiService) {
        this.resumeRepository = resumeRepository;
        this.questionRepository = questionRepository;
        this.responseRepository = responseRepository;
        this.fileStorageUtil = fileStorageUtil;
        this.gptApiService = gptApiService;
    }

    public Long saveResume(MultipartFile file) {
        try {
            String filePath = fileStorageUtil.saveFile(file);  // 파일 저장 및 경로 반환

            Resume resume = new Resume();
            resume.setFilePath(filePath);
            resume.setUploadedAt(LocalDateTime.now());

            return resumeRepository.save(resume).getId();
        } catch (IOException e) {
            throw new RuntimeException("이력서 파일 저장 중 오류가 발생했습니다.", e);
        }
    }

    // 새로운 메서드: GPT API를 이용해 이력서에서 질문 생성
    public List<String> generateQuestionsWithResume(Long resumeId) {
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new IllegalArgumentException("Resume not found"));

        // 파일 내용 읽기
        Path path = Paths.get(resume.getFilePath());
        String resumeContent;
        try {
            resumeContent = Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException("이력서 파일 읽기 중 오류가 발생했습니다.", e);
        }

        // GPT API로 질문 생성
        return gptApiService.generateInterviewQuestions(resumeContent);
    }

    public List<InterviewQuestion> findQuestionsByResumeId(Long resumeId) {
        return questionRepository.findByResumeId(resumeId);
    }

    public void saveResponse(InterviewResponseDto responseDto) {
        InterviewResponse response = new InterviewResponse();
        response.setAnswerContent(responseDto.getAnswerContent());
        response.setResponseDate(LocalDateTime.now());
        response.setToneAnalysisResult("분석 결과 예시");
        response.setQuestion(questionRepository.findById(responseDto.getQuestionId()).orElse(null));

        responseRepository.save(response);
    }
}