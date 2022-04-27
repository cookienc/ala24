package com.ala24.bookstore;

import com.ala24.bookstore.web.interceptor.AuthorityCheckInterceptor;
import com.ala24.bookstore.web.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	private static final String[] CHECKING_PATH_AUTHORITY = {"/adminHome", "/members/list", "/items/post"};
	private static final String CHECKING_PATH_LOGIN = "/**";
	private static final String[] EXCLUDE_PATH_LOGIN = {"/", "/login", "/logout", "/items/list",
				"/members/post", "/css/**", "/*.ico", "/error"};

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(new LoginCheckInterceptor())
				.order(1)
				.addPathPatterns(CHECKING_PATH_LOGIN)
				.excludePathPatterns(EXCLUDE_PATH_LOGIN);

		registry.addInterceptor(new AuthorityCheckInterceptor())
				.order(2)
				.addPathPatterns(CHECKING_PATH_AUTHORITY);
	}
}
