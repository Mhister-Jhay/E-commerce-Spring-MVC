package com.task.controller;

import com.task.dto.FeedbackDTO;
import com.task.service.impl.FeedbackServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class FeedbackController {
    private final FeedbackServiceImpl feedbackServiceImpl;
    @GetMapping("/contact")
    public ModelAndView landingPage(ModelAndView modelAndView, @RequestParam("session_id") Long id, @RequestParam("session_name") String name){
        modelAndView.setViewName("contact");
        modelAndView.addObject("feedbackForm", new FeedbackDTO());
        modelAndView.addObject("session_id", id);
        modelAndView.addObject("session_name", name);
        return modelAndView;
    }
    @GetMapping("/contacts")
    public ModelAndView landingPage(ModelAndView modelAndView){
        modelAndView.setViewName("contact");
        modelAndView.addObject("feedbackForm", new FeedbackDTO());
        modelAndView.addObject("session_id", null);
        modelAndView.addObject("session_name", null);
        return modelAndView;
    }
    @PostMapping("/sendFeedback")
    public String sendFeedback(@Validated FeedbackDTO feedbackDTO, Model model){
        boolean isFeedbackSent = feedbackServiceImpl.saveFeedback(feedbackDTO);
        if(isFeedbackSent){
            model.addAttribute("feedbackStatus", "Thanks for your feedback");
        }else{
            model.addAttribute("feedbackStatus", "Unable to send Feedback");
        }
        return "contact";
    }
}
