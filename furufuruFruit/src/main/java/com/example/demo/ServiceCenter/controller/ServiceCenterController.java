package com.example.demo.ServiceCenter.controller;


import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Entity.servicecenterquestion;
import com.example.demo.ServiceCenter.QuestionForm;
import com.example.demo.ServiceCenter.Exception.UserException;
import com.example.demo.ServiceCenter.Service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ServiceCenterController {  
	
	private final QuestionService qr;
	
	@GetMapping("/csc")
	public String SCenter(Model model, @RequestParam(value="page", defaultValue="0") int page) {
		Page<servicecenterquestion> paging = this.qr.getList(page);

		model.addAttribute("paging", paging);
		
		return "csc/CSC_List";
	}
	
	
	//한민기 작업
	@GetMapping(value = "/csc/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id, QuestionForm questionForm) throws UserException {
		servicecenterquestion q = this.qr.getQuestion(id);
		model.addAttribute("question",  q);
		return "csc/CSC_Detail";
		
	}
	
	
	@GetMapping("/csc/form")
	public String questionForm(QuestionForm questionForm) {

		return "csc/CSC_Form";
	}
	
	@PostMapping("/csc/form")
	public String questionCreate(@Valid QuestionForm questionForm, 
				BindingResult bindingResult) throws UserException {
		
		if(bindingResult.hasErrors()) {
			return "csc/CSC_Form";
		}
		
		this.qr.create(questionForm.getTitle(), questionForm.getContents());
		
		return "redirect:/csc";
	}
	
	//한민기 작업
	@GetMapping("/csc/modify/{id}")
	public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id) throws UserException {
		
		servicecenterquestion q = this.qr.getQuestion(id);
		questionForm.setTitle(q.getTitle());
		questionForm.setContents(q.getContents());
		
		return "csc/CSC_Form";
		
	}
	
	@PostMapping("/csc/modify/{id}")
	public String questionModify(@Valid QuestionForm questionForm, 
			@PathVariable("id") Integer id, BindingResult bindingResult) throws UserException {
		
		if(bindingResult.hasErrors()) {
			return "csc/CSC_Form";
		}
		
		servicecenterquestion q = this.qr.getQuestion(id);
		this.qr.modify(q, questionForm.getTitle(), questionForm.getContents());
		
		return "redirect:/csc/detail/{id}";
	}
	
	@GetMapping("/csc/delete/{id}")
	public String questionDelete(QuestionForm questionForm, @PathVariable("id") Integer id) throws UserException {
		
		servicecenterquestion q = this.qr.getQuestion(id);
		this.qr.delete(q);
		
		return "redirect:/csc";
	}
	
}
