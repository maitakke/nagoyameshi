package com.example.nagoyameshi.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.Company;
import com.example.nagoyameshi.form.CompanyEditForm;
import com.example.nagoyameshi.repository.CompanyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyService {
	public final CompanyRepository companyRepository;

	
	   // idが最も大きい会社概要を取得する
	public Optional<Company> findFirstCompanyByOrderByIdDesc(){
		return companyRepository.findFirstByOrderByIdDesc();
	}
	
	@Transactional
	public void updateCompany(CompanyEditForm companyEditForm, Company company) {
		company.setName(companyEditForm.getName());
		company.setPostalCode(companyEditForm.getPostalCode());
		company.setAddress(companyEditForm.getAddress());
		company.setRepresentative(companyEditForm.getRepresentative());
		company.setEstablishmentDate(companyEditForm.getEstablishmentDate());
		company.setCapital(companyEditForm.getCapital());
		company.setBusiness(companyEditForm.getBusiness());
		company.setNumberOfEmployees(companyEditForm.getNumberOfEmployees());

		companyRepository.save(company);
	}
}
