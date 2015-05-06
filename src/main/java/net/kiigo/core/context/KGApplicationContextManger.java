package net.kiigo.core.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class KGApplicationContextManger  {
	/**
	 * 初始化
	 */
	private static boolean initd;
	
	private static ApplicationContext _context;
	
	@SuppressWarnings("unused")
	private static ApplicationContext _coreContext;
	
	public static ApplicationContext getApplicationContext(){
		if(!initd){
			init();
		}
		return _context;		
	}

	private KGApplicationContextManger() {
	}
	
	private static void init() {
		init(false,null);
	}

	public synchronized static void  init(boolean isWebApp,ApplicationContext context) {
		if(isWebApp){
			if(initd){
				_coreContext  = context;
			}
			_context = context;
			initd = true;
			return ;
		}
		if(initd && isWebApp){/*已经启动了java应用的Application*/
			return;
		}		
		if(!initd){
			 _context = new ClassPathXmlApplicationContext("classpath*:web/applicationContext-*.xml");
		}
		initd = true;
		
	}
}
