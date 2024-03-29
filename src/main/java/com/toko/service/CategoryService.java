package com.toko.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toko.dao.CategoryDAO;
import com.toko.entity.Category;

@Service
public class CategoryService {
	@Autowired
	CategoryDAO dao;

	public List<Category> findAll() {
		return dao.findAll();
	}

	public Category findById(Integer id) {
		return dao.findById(id).get();
	}

	public Category create(Category category) {
		return dao.save(category);
	}

	public Category update(Category category) {
		return dao.save(category);
	}

	public void deleteById(Integer id) {
		dao.deleteById(id);
	}

	public boolean existedById(Integer id) {
		return dao.existsById(id);
	}
	public Category findByType(String type) {
		return dao.findByType(type);
	}
}
