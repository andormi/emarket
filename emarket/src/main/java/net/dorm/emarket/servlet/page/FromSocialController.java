package net.dorm.emarket.servlet.page;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.dorm.emarket.Constants;
import net.dorm.emarket.model.CurrentAccount;
import net.dorm.emarket.model.SocialAccount;
import net.dorm.emarket.servlet.AbstractController;
import net.dorm.emarket.util.RoutingUtils;
import net.dorm.emarket.util.SessionUtils;

@WebServlet("/from-social")
public class FromSocialController extends AbstractController {

	private static final long serialVersionUID = -188361664938409356L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String code = req.getParameter("code");
		if (code != null) {
			SocialAccount socialAccount = getSocialService().getSocialAccount(code);
			CurrentAccount currentAccount = getOrderService().authentificate(socialAccount);
			SessionUtils.setCurrentAccount(req, currentAccount);
			redirectToSuccessPage(req, resp);
		} else {
			LOGGER.warn("Parameter code not found");
			if(req.getSession().getAttribute(Constants.SUCCESS_REDIRECT_URL_AFTER_SIGNIN) != null){
				req.getSession().removeAttribute(Constants.SUCCESS_REDIRECT_URL_AFTER_SIGNIN);
			}
			RoutingUtils.redirect("/sign-in", req, resp);
		}
	}
	
	protected void redirectToSuccessPage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String targetUrl = (String) req.getSession().getAttribute(Constants.SUCCESS_REDIRECT_URL_AFTER_SIGNIN);
		if (targetUrl != null) {
			req.getSession().removeAttribute(Constants.SUCCESS_REDIRECT_URL_AFTER_SIGNIN);
			RoutingUtils.redirect(URLDecoder.decode(targetUrl, "UTF-8"), req, resp);
		} else {
			RoutingUtils.redirect("/my-orders", req, resp);
		}
	}
}
