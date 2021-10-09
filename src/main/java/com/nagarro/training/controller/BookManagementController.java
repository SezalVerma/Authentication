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
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.nagarro.training.dto.BookDto;

import com.nagarro.training.service.BookManagementService;


@Controller
@SessionAttributes({ "bookDtos", "bookDto" })
public class BookManagementController {

	@Autowired
	private BookManagementService bookManagementService;

	@RequestMapping(value = "/homepage", method = RequestMethod.GET)
	public ModelAndView resultGet(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		List<BookDto> bookDtos = bookManagementService.getAllBooks();
		mv.addObject("bookDtos", bookDtos);
		mv.setViewName("homepage");
		return mv;
	}

	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public ModelAndView uploadBookGet(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("upload");
		return mv;
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ModelAndView uploadBook(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		int code = Integer.parseInt(request.getParameter("code"));
		String name = request.getParameter("name");
		String author = request.getParameter("author");
		String doc = request.getParameter("doc");
		BookDto bookDto = new BookDto(code,name, author, doc);
		bookManagementService.addBook(bookDto);
		List<BookDto> bookDtos = bookManagementService.getAllBooks();
		mv.addObject("bookDtos", bookDtos);
		mv.setViewName("homepage");
		return mv;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editBookGet(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		int code = Integer.parseInt(request.getParameter("code"));
		BookDto bookDto = bookManagementService.getBook(code);
		mv.addObject("bookDto", bookDto);
		mv.setViewName("edit");
		return mv;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView editBook(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		int code = Integer.parseInt(request.getParameter("code"));
		String name = request.getParameter("name");
		String author = request.getParameter("author");
		String doc = request.getParameter("doc");
		BookDto bookDto = new BookDto(code, name, author, doc);
		bookManagementService.updateBook(bookDto);
		List<BookDto> bookDtos = bookManagementService.getAllBooks();
		mv.addObject("bookDtos", bookDtos);
		mv.setViewName("homepage");
		return mv;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView deleteBookGet(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		int code = Integer.parseInt(request.getParameter("code"));
		bookManagementService.deleteBook(code);
		List<BookDto> bookDtos = bookManagementService.getAllBooks();
		mv.addObject("bookDtos", bookDtos);
		mv.setViewName("homepage");
		return mv;
	}
	
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public ModelAndView downloadEmployees(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		List<BookDto> bookDtos = bookManagementService.getAllBooks();
		response.setContentType("text/csv");
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", "Books.csv");
		response.setHeader(headerKey, headerValue);
		ICsvBeanWriter csvWriter;

		try {
			csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
			String[] headers = { "Code", "Name", "Author", "Date" };
			csvWriter.writeHeader(headers);
			for (BookDto bookDto : bookDtos) {
				csvWriter.write(bookDto, headers);
			}
			csvWriter.close();
		} catch (Exception e) {
		}
		mv.addObject("bookDtos", bookDtos);
		mv.setViewName("homepage");
		return mv;
	}

	
}
