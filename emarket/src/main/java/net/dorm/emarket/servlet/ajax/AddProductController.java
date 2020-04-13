package net.dorm.emarket.servlet.ajax;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.dorm.emarket.form.ProductForm;
import net.dorm.emarket.model.ShoppingCart;
import net.dorm.emarket.util.SessionUtils;

@WebServlet("/ajax/json/product/add")
public class AddProductController extends AbstractProductController{
	private static final long serialVersionUID = -3440773010973766553L;

	@Override
	protected void processProductForm(ProductForm form, ShoppingCart shoppingCart, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		getOrderService().addProductToShoppingCart(form, shoppingCart);
		String cookieValue = getOrderService().serializeShoppingCart(shoppingCart);
		SessionUtils.updateCurrentShoppingCartCookie(cookieValue, resp);
		
	}

}
