package br.com.jackson.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

	@Autowired
	private HttpServletRequest request;


	@RequestMapping("/")
	public String index() {
		return this.request.getRequestURL().toString();
	}
}