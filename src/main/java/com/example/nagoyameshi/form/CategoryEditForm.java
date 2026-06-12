package com.example.nagoyameshi.form;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CategoryEditForm {
	@NotBlank(message = "カテゴリー名を入力してください。")
	private String name;

}
