package egovframework.com.cmmn.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import egovframework.com.user.vo.UserVo;

public class cmmnInterceptor extends HandlerInterceptorAdapter {

	private static final String LOGIN = "login";
	protected Log log = LogFactory.getLog(cmmnInterceptor.class);
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String requestURI = request.getRequestURI();
		
		if(log.isDebugEnabled()) {
		
			log.debug(" ============= start ==============");
			log.debug("Request URI \t :" + requestURI);
		}
		
		HttpSession httpSession = request.getSession();
		
		try {
			if (requestURI.indexOf("/main") == -1) {
				if (httpSession.getAttribute(LOGIN) != null) {
					
				} else {
					log.debug(" =============권한없음==============");
					response.sendRedirect("/main/main.do?chk=1");
					return false;
				}
			}
		} catch (Exception e) {
			
		}
		
		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		try {
			if(log.isDebugEnabled()) {
				log.debug(" ============= end ==============");
				if (modelAndView != null) {
					log.debug("Response viewName : " + modelAndView.getViewName());
				}
			}
		}catch(Exception e) {
		
		}
		
		super.postHandle(request, response, handler, modelAndView);
	}

	
}
