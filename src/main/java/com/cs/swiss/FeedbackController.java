package com.cs.swiss;

import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FeedbackController {
	
	@Autowired
	FeedbackRepository feedbackRepo;
	
	@RequestMapping(value="/feedback",method=RequestMethod.GET)
	public String showFeedbackForm(Model model) {
		model.addAttribute("feedback", new Feedback());
		return "SupportAndFeedback";
	}
	
	@RequestMapping(value="/saveFeedback",method=RequestMethod.POST)
	public String saveFeedback(Feedback feedback, Model model) {
		if(feedback.isConsentForSharingUserId()) feedback.setUserId(SecurityContextHolder.getContext().getAuthentication().getName());
		feedbackRepo.save(feedback);
		model.addAttribute("feedbackId",feedback.getId());
		CustomEmailService.sendmail("Feedback Submitted : "+feedback.getId(), feedback.getUserId(), feedback.toString());		
		return "FeedbackSuccess";
	}
	
}
