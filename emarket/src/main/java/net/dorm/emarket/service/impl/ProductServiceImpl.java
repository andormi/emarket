package net.dorm.emarket.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.dorm.emarket.entity.Category;
import net.dorm.emarket.entity.Producer;
import net.dorm.emarket.entity.Product;
import net.dorm.emarket.exception.InternalServerErrorException;
import net.dorm.emarket.form.SearchForm;
import net.dorm.emarket.jdbc.JDBCUtils;
import net.dorm.emarket.jdbc.ResultSetHandler;
import net.dorm.emarket.jdbc.ResultSetHandlerFactory;
import net.dorm.emarket.jdbc.SearchQuery;
import net.dorm.emarket.service.ProductService;

class ProductServiceImpl implements ProductService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);
	private static final ResultSetHandler<List<Product>> productsResultSetHandler = 
			ResultSetHandlerFactory.getListResultSetHandler(ResultSetHandlerFactory.PRODUCT_RESULT_SET_HANDLER);
	private final ResultSetHandler<List<Category>> categoryListResultSetHandler = 
			ResultSetHandlerFactory.getListResultSetHandler(ResultSetHandlerFactory.CATEGORY_RESULT_SET_HANDLER);
	private final ResultSetHandler<List<Producer>> producerListResultSetHandler = 
			ResultSetHandlerFactory.getListResultSetHandler(ResultSetHandlerFactory.PRODUCER_RESULT_SET_HANDLER);
	private final ResultSetHandler<Integer> countResultSetHandler = ResultSetHandlerFactory.getCountResultSetHandler();
	
	private final DataSource dataSource;
	
	public ProductServiceImpl(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	@Override
	public List<Product> listAllProducts(int page, int limit) {
		try (Connection c = dataSource.getConnection()) {
			int offset = (page - 1) * limit;
			String sqlReq = "select p.*, c.name as category, pr.name as producer from product p, producer pr, category c "
					+ "where c.id=p.id_category and pr.id=p.id_producer limit ? offset ?";
			return JDBCUtils.select(c, sqlReq, productsResultSetHandler, limit, offset);
		} catch (SQLException e) {
			throw new InternalServerErrorException("Can't execute sql query: " + e.getMessage(), e);
		}
	}

	@Override
	public List<Product> listProductsByCategory(String categoryUrl, int page, int limit) {
		try (Connection c = dataSource.getConnection()) {
			int offset = (page - 1) * limit;
			String sqlReq = "select p.*, c.name as category, pr.name as producer from product p, producer pr, category c "
					+ "where pr.id=p.id_producer and c.id=p.id_category and c.url=? order by p.id limit ? offset ?";
			return JDBCUtils.select(c, sqlReq, productsResultSetHandler, categoryUrl, limit, offset);
		} catch (SQLException e) {
			throw new InternalServerErrorException("Can't execute sql query: " + e.getMessage(), e);
		}
	}

	@Override
	public List<Category> listAllCategories() {
		try (Connection c = dataSource.getConnection()) {
			String sqlReq = "select category.* from category order by id";
			return JDBCUtils.select(c, sqlReq, categoryListResultSetHandler);
		} catch (SQLException e) {
			throw new InternalServerErrorException("Can't execute sql query: " + e.getMessage(), e);
		}
	}

	@Override
	public List<Producer> listAllProducers() {
		try (Connection c = dataSource.getConnection()) {
			String sqlReq = "select producer.* from producer order by name";
			return JDBCUtils.select(c, sqlReq, producerListResultSetHandler);
		} catch (SQLException e) {
			throw new InternalServerErrorException("Can't execute sql query: " + e.getMessage(), e);
		}
	}
	
	@Override
	public int countAllProducts() {
		try (Connection c = dataSource.getConnection()) {
			String sqlReq = "select count(*) from product";
			return JDBCUtils.select(c, sqlReq, countResultSetHandler);
		} catch (SQLException e) {
			throw new InternalServerErrorException("Can't execute sql query: " + e.getMessage(), e);
		}
	}
	
	@Override
	public int countProductsByCategory(String categoryUrl) {
		try (Connection c = dataSource.getConnection()) {
			String sqlReq = "select count(p.*) from product p, category c where c.id=p.id_category and c.url=?";
			return JDBCUtils.select(c, sqlReq, countResultSetHandler, categoryUrl);
		} catch (SQLException e) {
			throw new InternalServerErrorException("Can't execute sql query: " + e.getMessage(), e);
		}
	}
	
	@Override
	public List<Product> listProductsBySearchForm(SearchForm form, int page, int limit) {
		try (Connection c = dataSource.getConnection()) {
			int offset = (page - 1) * limit;
			SearchQuery sq = buildSearchQuery("p.*, c.name as category, pr.name as producer", form);
			sq.getSql().append(" order by p.id limit ? offset ?");
			sq.getParams().add(limit);
			sq.getParams().add(offset);
			LOGGER.debug("search query={} with params={}", sq.getSql(), sq.getParams());
			return JDBCUtils.select(c, sq.getSql().toString(), productsResultSetHandler, sq.getParams().toArray());
		} catch (SQLException e) {
			throw new InternalServerErrorException("Can't execute SQL request: " + e.getMessage(), e);
		}
	}

	protected SearchQuery buildSearchQuery(String selectFields, SearchForm form) {
		List<Object> params = new ArrayList<>();
		StringBuilder sql = new StringBuilder("select ");
		String sqlReqPart = " from product p, category c, producer pr "
				+ "where pr.id=p.id_producer and c.id=p.id_category and (p.name ilike ? or p.description ilike ?)";
		sql.append(selectFields).append(sqlReqPart);
		params.add("%" + form.getQuery() + "%");
		params.add("%" + form.getQuery() + "%");
		JDBCUtils.populateSqlAndParams(sql, params, form.getCategories(), "c.id = ?");
		JDBCUtils.populateSqlAndParams(sql, params, form.getProducers(), "pr.id = ?");
		return new SearchQuery(sql, params);
	}

	@Override
	public int countProductsBySearchForm(SearchForm form) {
		try (Connection c = dataSource.getConnection()) {
			SearchQuery sq = buildSearchQuery("count(*)", form);
			LOGGER.debug("search query={} with params={}", sq.getSql(), sq.getParams());
			return JDBCUtils.select(c, sq.getSql().toString(), countResultSetHandler, sq.getParams().toArray());
		} catch (SQLException e) {
			throw new InternalServerErrorException("Can't execute SQL request: " + e.getMessage(), e);
		}
	}
}
