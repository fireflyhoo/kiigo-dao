package net.kiigo.core;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.kiigo.core.context.KGApplicationContextManger;

import org.springframework.context.ApplicationContext;

public abstract class ApplicationManager  implements ServletContextListener {
	private static  ServletContext context =null;

	
	public static ServletContext getContext() {
		return context;
	}

	public void contextInitialized(ServletContextEvent sce) {
		ApplicationManager.context = sce.getServletContext();
		ApplicationContext _context = getSpringApplicationContext(context); 
		KGApplicationContextManger.init(true, _context);
		System.err.println("Web App Start...");
	}

	protected abstract ApplicationContext getSpringApplicationContext(ServletContext context);

	public void contextDestroyed(ServletContextEvent sce) {
		ApplicationManager.context = null;
		System.err.println("Web App Colse...");
	}
}
