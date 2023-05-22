package com.task.service;

import com.task.dto.FeedbackDTO;

public interface FeedbackService {
    boolean saveFeedback(FeedbackDTO feedbackDTO);
}
