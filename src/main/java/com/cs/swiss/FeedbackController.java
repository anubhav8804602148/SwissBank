package com.cs.swiss;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FeedbackController {
	
	/*
	 * "/feedback"
	 * "/saveFeedback"
	 */
	@Autowired
	FeedbackRepository feedbackRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@RequestMapping(value="/feedback",method=RequestMethod.GET)
	public String showFeedbackForm(Model model) {
		model.addAttribute("user", userRepo.findByEmail(
				SecurityContextHolder.getContext().getAuthentication().getName()).get(0));
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
