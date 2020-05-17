package ua.training.dao;

import java.util.List;

import ua.training.entity.Category;

public interface CategoryDao extends GenericDao<Category, Long>, AutoCloseable {
	
	List<Category> searchCategoriesByName(String categoryName);
	
	void close();
}
