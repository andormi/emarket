package net.dorm.emarket.servlet.ajax;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import net.dorm.emarket.form.ProductForm;
import net.dorm.emarket.model.ShoppingCart;
import net.dorm.emarket.servlet.AbstractController;
import net.dorm.emarket.util.RoutingUtils;
import net.dorm.emarket.util.SessionUtils;

public abstract class AbstractProductController extends AbstractController {
	private static final long serialVersionUID = -4825007025146982708L;
	
	@Override
	protected final void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ProductForm productForm = createProductForm(req);
		ShoppingCart shoppingCart = SessionUtils.getCurrentShoppingCart(req);
		processProductForm(productForm, shoppingCart, req, resp);
		sendResponse(shoppingCart, req, resp);
	}
	
	protected abstract void processProductForm(ProductForm form, ShoppingCart shoppingCart, HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException;
	
	protected void sendResponse(ShoppingCart shoppingCart, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		JSONObject cardStatistics = new JSONObject();
		cardStatistics.put("totalCount", shoppingCart.getTotalCount());
		cardStatistics.put("totalCost", shoppingCart.getTotalCost());
		RoutingUtils.sendJSON(cardStatistics, req, resp);
	}

}
