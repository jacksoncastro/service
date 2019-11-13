package br.com.jackson.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jackson.service.MainService;
import br.com.jackson.vo.RequestVO;

@RestController
@RequestMapping(value = "/api")
public class MainController {

	private static final Logger log = LoggerFactory.getLogger(MainController.class);

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private MainService mainService;


	// ingress controller nginx
	@PostMapping
	public ResponseEntity<?> action(@RequestBody RequestVO data) {

		log.trace("Host requested: {}", this.request.getRequestURL());

		this.mainService.speedup(data);

		return ResponseEntity.ok().build();
	}
}