package br.com.jackson.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jackson.service.MainService;
import br.com.jackson.vo.RequestVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api")
public class MainController {


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