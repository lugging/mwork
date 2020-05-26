package com.yuntongxun.mwork.interceptor;

import com.google.common.base.Splitter;
import com.yuntongxun.mwork.config.MyConfigProperties;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 继承自  org.springframework.web.servlet.i18n.LocaleChangeInterceptor
 * 从head中获取客户端语言
 *
 * @author liugang
 */
public class LocaleInterceptor extends LocaleChangeInterceptor {

	private static final String SEPARATOR = ",";

	private MyConfigProperties myConfigProperties;

	public LocaleInterceptor(MyConfigProperties myConfigProperties){
		this.myConfigProperties = myConfigProperties;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {
		String newLocale = request.getParameter(getParamName());
		if (StringUtils.isEmpty(newLocale)){
			newLocale = request.getHeader(getParamName());
		}
		if (newLocale != null) {
			newLocale = skip(newLocale);
			if (checkHttpMethod(request.getMethod())) {
				LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
				if (localeResolver == null) {
					throw new IllegalStateException(
							"No LocaleResolver found: not in a DispatcherServlet request?");
				}
				try {
					localeResolver.setLocale(request, response, parseLocaleValue(newLocale));
				} catch (IllegalArgumentException ex) {
					if (isIgnoreInvalidLocale()) {
						if (logger.isDebugEnabled()) {
							logger.debug("Ignoring invalid locale value [" + newLocale + "]: " + ex.getMessage());
						}
					} else {
						throw ex;
					}
				}
			}
		}
		// Proceed in any case.
		return true;
	}

	private boolean checkHttpMethod(String currentMethod) {
		String[] configuredMethods = getHttpMethods();
		if (ObjectUtils.isEmpty(configuredMethods)) {
			return true;
		}
		for (String configuredMethod : configuredMethods) {
			if (configuredMethod.equalsIgnoreCase(currentMethod)) {
				return true;
			}
		}
		return false;
	}

	private String skip(String locale){
		if ( locale.indexOf(SEPARATOR) > 0 ){
			return Splitter.on(SEPARATOR).split(locale).iterator().next();
		} else {
			return locale;
		}
	}
}

