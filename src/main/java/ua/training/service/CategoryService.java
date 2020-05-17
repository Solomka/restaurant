package ua.training.service;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.training.dao.CategoryDao;
import ua.training.dao.DaoFactory;
import ua.training.entity.Category;

public class CategoryService {

	private static final Logger LOGGER = LogManager.getLogger(CategoryService.class);

	private DaoFactory daoFactory;

	CategoryService(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	private static class Holder {
		static final CategoryService INSTANCE = new CategoryService(DaoFactory.getDaoFactory());
	}

	public static CategoryService getInstance() {
		return Holder.INSTANCE;
	}

	public List<Category> getAllCategories() {
		LOGGER.info("Get all categories");
		try (CategoryDao categoryDao = daoFactory.createCategoryDao()) {
			return categoryDao.getAll();
		}
	}

	public Optional<Category> getCategoryById(Long categoryId) {
		LOGGER.info("Get category by id: " + categoryId);
		try (CategoryDao categoryDao = daoFactory.createCategoryDao()) {
			return categoryDao.getById(categoryId);
		}
	}

	public void createCategory(Category category) {
		LOGGER.info("Create category: " + category.getName());
		try (CategoryDao categoryDao = daoFactory.createCategoryDao()) {
			categoryDao.create(category);
		}
	}

	public void updateCategory(Category category) {
		LOGGER.info("Update category: " + category.getName());
		try (CategoryDao categoryDao = daoFactory.createCategoryDao()) {
			categoryDao.update(category);
		}
	}

	public void deleteCategory(Long categoryId) {
		LOGGER.info("Delete category: " + categoryId);
		try (CategoryDao categoryDao = daoFactory.createCategoryDao()) {
			categoryDao.delete(categoryId);
		}
	}

	public List<Category> searchCategoriesByName(String categoryName) {
		LOGGER.info("Search categories by name: " + categoryName);
		try (CategoryDao categoryDao = daoFactory.createCategoryDao()) {
			return categoryDao.searchCategoriesByName(categoryName);
		}
	}
}