package net.dorm.emarket.service;

import java.util.List;

import net.dorm.emarket.entity.Order;
import net.dorm.emarket.form.ProductForm;
import net.dorm.emarket.model.CurrentAccount;
import net.dorm.emarket.model.ShoppingCart;
import net.dorm.emarket.model.SocialAccount;

public interface OrderService {

	void addProductToShoppingCart(ProductForm productForm, ShoppingCart shoppingCart);
	void removeProductFromShoppingCart(ProductForm form, ShoppingCart shoppingCart);
	
	String serializeShoppingCart(ShoppingCart shoppingCart);
	ShoppingCart deserializeShoppingCart(String string);
	CurrentAccount authentificate(SocialAccount socialAccount);
	
	long makeOrder (ShoppingCart shoppingCart, CurrentAccount currentAccount);
	
	Order findOrderById(long id, CurrentAccount currentAccount);
	
	List<Order> listMyOrders(CurrentAccount currentAccount, int page, int limit);
	
	int countMyOrders(CurrentAccount currentAccount);
}
