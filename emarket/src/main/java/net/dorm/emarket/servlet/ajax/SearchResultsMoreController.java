package net.dorm.emarket.servlet.ajax;

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

@WebServlet("/ajax/html/more/search")
public class SearchResultsMoreController extends AbstractController {
	private static final long serialVersionUID = 8657719172364255988L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SearchForm searchForm = createSearchForm(req);
		List<Product> products = getProductService().listProductsBySearchForm(searchForm, getPage(req), Constants.MAX_PRODUCTS_PER_HTML_PAGE);
		req.setAttribute("products", products);
		RoutingUtils.forwardToFragment("product-list.jsp", req, resp);
	}
}
