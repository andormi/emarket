package net.dorm.emarket.service;

import java.util.List;

import net.dorm.emarket.entity.Category;
import net.dorm.emarket.entity.Producer;
import net.dorm.emarket.entity.Product;
import net.dorm.emarket.form.SearchForm;

public interface ProductService {

	List<Product> listAllProducts(int page, int limit);
	
	int countAllProducts();
	
	List<Product> listProductsByCategory(String categoryUrl, int page, int limit);
	
	int countProductsByCategory(String categoryUrl);
	
	List<Category> listAllCategories();
	
	List<Producer> listAllProducers();
	
	List<Product> listProductsBySearchForm(SearchForm searchForm, int page, int limit);
	
	int countProductsBySearchForm(SearchForm searchForm);
}
