package net.dorm.emarket.servlet.page;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.dorm.emarket.Constants;
import net.dorm.emarket.entity.Product;
import net.dorm.emarket.form.SearchForm;
import net.dorm.emarket.servlet.AbstractController;
import net.dorm.emarket.util.RoutingUtils;

@WebServlet("/search")
public class SearchController extends AbstractController {
	private static final long serialVersionUID = 2778113303821540524L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SearchForm searchForm = createSearchForm(req);
		List<Product> products = getProductService().listProductsBySearchForm(searchForm, 1, Constants.MAX_PRODUCTS_PER_HTML_PAGE);
		req.setAttribute("products", products);
		int totalCount = getProductService().countProductsBySearchForm(searchForm);
		req.setAttribute("pageCount", getPageCount(totalCount, Constants.MAX_PRODUCTS_PER_HTML_PAGE));
		req.setAttribute("productCount", totalCount);
		req.setAttribute("searchForm", searchForm);
		RoutingUtils.forwardToPage("search-result.jsp", req, resp);
	}
}
