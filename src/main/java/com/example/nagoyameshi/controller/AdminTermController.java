package com.example.nagoyameshi.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.Term;
import com.example.nagoyameshi.form.TermEditForm;
import com.example.nagoyameshi.service.TermService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/terms")
@RequiredArgsConstructor
public class AdminTermController {
	private final TermService termService;

		@GetMapping
		public String index(Model model) {
			Optional<Term> termOptional = termService.findFirstTermByOrderByIdDesc();
			model.addAttribute("term", termOptional.orElse(null));
			
			return "admin/terms/index";
		}
		
		@GetMapping("/edit")
		public String edit(Model model) {
			Optional<Term> termOptional = termService.findFirstTermByOrderByIdDesc();
			
			if (termOptional.isPresent()) {
				Term term = termOptional.get();

				TermEditForm termEditForm = new TermEditForm(term.getContent());
	            model.addAttribute("termEditForm", termEditForm);
	        }
			
			return "admin/terms/edit";
		}
		
		@PostMapping("/update")
	    public String update(@ModelAttribute @Validated TermEditForm termEditForm, 
	                         BindingResult bindingResult, 
	                         RedirectAttributes redirectAttributes,
	                         Model model) {
	        
	        // エラーが存在する場合
	        if (bindingResult.hasErrors()) {
	            return "admin/terms/edit"; // 編集ページを再度表示
	        }
	        
			Optional<Term> termOptional = termService.findFirstTermByOrderByIdDesc();
			
			if (termOptional.isPresent()) {
				Term term = termOptional.get();
				// フォームの値でエンティティの content を更新
	            term.setContent(termEditForm.getContent());
	            
	         // サービスクラスの updateTerm() メソッドを呼び出し、データベースを更新
	            termService.updateTerm(termEditForm, term);
			}
			
	        // フラッシュスコープに変数を渡す
	        redirectAttributes.addFlashAttribute("successMessage", "利用規約を編集しました。");
	        
	        return "redirect:/admin/terms";
	    }
	}
