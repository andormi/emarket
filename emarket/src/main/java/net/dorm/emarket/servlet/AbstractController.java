package net.dorm.emarket.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.dorm.emarket.form.ProductForm;
import net.dorm.emarket.form.SearchForm;
import net.dorm.emarket.service.OrderService;
import net.dorm.emarket.service.ProductService;
import net.dorm.emarket.service.SocialService;
import net.dorm.emarket.service.impl.ServiceManager;

public abstract class AbstractController extends HttpServlet {
	private static final long serialVersionUID = 3994139737113078070L;
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	private ProductService productService;
	private OrderService orderService;
	private SocialService socialService;

	@Override
	public final void init() throws ServletException {
		productService = ServiceManager.getInstance(getServletContext()).getProductService();
		orderService = ServiceManager.getInstance(getServletContext()).getOrderService();
		socialService = ServiceManager.getInstance(getServletContext()).getSocialService();
	}

	public final ProductService getProductService() {
		return productService;
	}

	public final OrderService getOrderService() {
		return orderService;
	}
	
	public final SocialService getSocialService() {
		return socialService;
	}
	
	public final int getPageCount(int totalCount, int itemsPerPage) {
		int res = totalCount / itemsPerPage;
		if(res * itemsPerPage != totalCount) {
			res++;
		}
		return res;
	}
	
	public final int getPage(HttpServletRequest request) {
		try {
			return Integer.parseInt(request.getParameter("page"));
		} catch (NumberFormatException e) {
			return 1;
		}
	}
	
	public final SearchForm createSearchForm(HttpServletRequest request) {
		return new SearchForm(
				request.getParameter("query"), 
				request.getParameterValues("category"), 
				request.getParameterValues("producer"));
	}
	
	public final ProductForm createProductForm(HttpServletRequest request) {
		return new ProductForm(
				Integer.parseInt(request.getParameter("idProduct")),
				Integer.parseInt(request.getParameter("count")));
	}
}
