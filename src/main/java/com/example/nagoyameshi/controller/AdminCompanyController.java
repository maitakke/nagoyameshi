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

import com.example.nagoyameshi.entity.Company;
import com.example.nagoyameshi.form.CompanyEditForm;
import com.example.nagoyameshi.service.CompanyService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/company")
@RequiredArgsConstructor
public class AdminCompanyController {
	private final CompanyService companyService;

	@GetMapping
	public String index(Model model) {
		Optional<Company> companyOptional = companyService.findFirstCompanyByOrderByIdDesc();
		model.addAttribute("company", companyOptional.orElse(null));
		
		return "admin/company/index";
	}
	
	@GetMapping("/edit")
	public String edit(Model model) {
		Optional<Company> companyOptional = companyService.findFirstCompanyByOrderByIdDesc();
		
		if (companyOptional.isPresent()) {
            Company company = companyOptional.get();
            // 全フィールドに値をセットするための引数つきコンストラクタ（Step 5で作成したもの）を利用
            CompanyEditForm companyEditForm = new CompanyEditForm(
                company.getName(),
                company.getPostalCode(),
                company.getAddress(),
                company.getRepresentative(),
                company.getEstablishmentDate(),
                company.getCapital(),
                company.getBusiness(),
                company.getNumberOfEmployees()
            );
            model.addAttribute("companyEditForm", companyEditForm);
        }
		
		return "admin/company/edit";
	}
	
	@PostMapping("/update")
    public String update(@ModelAttribute @Validated CompanyEditForm companyEditForm, 
                         BindingResult bindingResult, 
                         RedirectAttributes redirectAttributes,
                         Model model) {
        
        // エラーが存在する場合
        if (bindingResult.hasErrors()) {
            model.addAttribute("companyEditForm", companyEditForm);
            return "admin/company/edit"; // 編集ページを再度表示
        }
        
		Optional<Company> companyOptional = companyService.findFirstCompanyByOrderByIdDesc();
        Company company = companyOptional.get();
	       companyService.updateCompany(companyEditForm, company);
        // フラッシュスコープに変数を渡す
        redirectAttributes.addFlashAttribute("successMessage", "会社概要を編集しました。");
        
        // 管理者用の会社概要ページにリダイレクト
        return "redirect:/admin/company";
    }
}
