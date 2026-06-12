package com.example.nagoyameshi.form;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryEditForm {
	@NotBlank(message = "カテゴリー名を入力してください。")
	private String name;
	
	private List<Integer> categoryIds;

}
