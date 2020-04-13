package net.dorm.emarket.servlet.page;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.dorm.emarket.servlet.AbstractController;
import net.dorm.emarket.util.RoutingUtils;

@WebServlet("/my-orders")
public class MyOrdersController extends AbstractController {

	private static final long serialVersionUID = -2937345683251643692L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RoutingUtils.forwardToPage("my-orders.jsp", req, resp);
	}
}
