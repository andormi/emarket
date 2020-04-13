package net.dorm.emarket.servlet.page;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.dorm.emarket.Constants;
import net.dorm.emarket.entity.Product;
import net.dorm.emarket.servlet.AbstractController;
import net.dorm.emarket.util.RoutingUtils;

@WebServlet("/products/*")
public class ProductsByCategoryController extends AbstractController {
	private static final long serialVersionUID = -5427342780240354250L;
	private static final int SUBSTRING_INDEX = "/products".length();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {	
		String categoryUrl = req.getRequestURI().substring(SUBSTRING_INDEX);
		List<Product> products = getProductService().listProductsByCategory(categoryUrl, 1, Constants.MAX_PRODUCTS_PER_HTML_PAGE);
		req.setAttribute("products", products);
		int totalCountByCategory = getProductService().countProductsByCategory(categoryUrl);
		req.setAttribute("pageCount", getPageCount(totalCountByCategory, Constants.MAX_PRODUCTS_PER_HTML_PAGE));
		req.setAttribute("selectedCategoryUrl", categoryUrl);
		RoutingUtils.forwardToPage("products.jsp", req, resp);
	}
}
