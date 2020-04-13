package net.dorm.emarket.servlet.page;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.dorm.emarket.servlet.AbstractController;
import net.dorm.emarket.util.RoutingUtils;
import net.dorm.emarket.util.SessionUtils;

@WebServlet("/shopping-cart")
public class ShowShoppingCartController extends AbstractController {
	private static final long serialVersionUID = -611353069465654329L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (SessionUtils.isCurrentShoppingCartCreated(req)) {
			RoutingUtils.forwardToPage("shopping-cart.jsp", req, resp);
		} else {
			RoutingUtils.redirect("/products", req, resp);
		}
	}
}
