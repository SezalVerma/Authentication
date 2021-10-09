package com.nagarro.training.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.nagarro.training.dto.BookDto;

import com.nagarro.training.service.BookManagementService;
import com.nagarro.training.service.LoginService;


@Controller
@SessionAttributes({  "authorized", "error" })
//@RequestMapping("/authentication")
public class LoginController {

	@Autowired
	private LoginService loginService;
	@Autowired
	private BookManagementService bookManagementService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView loginGet(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("login");
		return mv;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mv = new ModelAndView();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		boolean userAuthenticated = loginService.loginAuthentication(username, password);

		if (userAuthenticated) {
			List<BookDto> bookDtos = bookManagementService.getAllBooks();
			mv.addObject("bookDtos", bookDtos);
			mv.addObject("authorized", "true");
			mv.setViewName("homepage");
		} else {
			mv.addObject("error", "Username or Password is Invalid.");
			mv.setViewName("login");
		}
		return mv;
	}

}
