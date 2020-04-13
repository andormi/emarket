package net.dorm.emarket.listener;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.dorm.emarket.Constants;
import net.dorm.emarket.entity.Category;
import net.dorm.emarket.entity.Producer;
import net.dorm.emarket.service.impl.ServiceManager;
@WebListener
public class EMarketApplicationListener implements ServletContextListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(EMarketApplicationListener.class);
	private ServiceManager serviceManager;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			serviceManager = ServiceManager.getInstance(sce.getServletContext());
			List<Category> categories = serviceManager.getProductService().listAllCategories();
			List<Producer> producers = serviceManager.getProductService().listAllProducers();
			sce.getServletContext().setAttribute(Constants.CATEGORY_LIST, categories);
			sce.getServletContext().setAttribute(Constants.PRODUCER_LIST, producers);
		} catch (RuntimeException e) {
			LOGGER.error("Web application 'emarket' init failed: "+e.getMessage(), e);
			throw e;
		}
		LOGGER.info("Web application 'emarket' initialized");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		serviceManager.close();
		LOGGER.info("Web application 'emarket' destroyed");
	}
}
