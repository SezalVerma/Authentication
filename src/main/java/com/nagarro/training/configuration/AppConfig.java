package com.nagarro.training.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nagarro.training.dao.HibernateDao;
import com.nagarro.training.dao.HibernateDaoImplementation;

import com.nagarro.training.service.BookManagementService;
import com.nagarro.training.service.LoginService;

@Configuration
public class AppConfig {
	@Bean
	public HibernateDao getHibernateDao() {
		return new HibernateDaoImplementation();
	}


	@Bean
	public LoginService getLoginService() {
		return new LoginService();
	}

	@Bean
	public BookManagementService getLibraryManagementService() {
		return new BookManagementService();
	}
}
