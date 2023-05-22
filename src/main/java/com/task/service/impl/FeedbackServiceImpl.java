package com.task.service.impl;

import com.task.dto.FeedbackDTO;
import com.task.model.Feedback;
import com.task.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements com.task.service.FeedbackService {
    private final FeedbackRepository feedbackRepository;
    @Override
    public boolean saveFeedback(FeedbackDTO feedbackDTO) {
        Feedback feedback = Feedback.builder()
                .name(feedbackDTO.getName())
                .email(feedbackDTO.getEmail())
                .subject(feedbackDTO.getSubject())
                .message(feedbackDTO.getMessage())
                .build();
        feedbackRepository.save(feedback);
        return true;
    }
}
