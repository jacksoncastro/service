package br.com.jackson.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

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
	private MainService mainService;


	// ingress controller nginx
	@PostMapping
	public ResponseEntity<?> action(@RequestBody List<RequestVO> datas) {

		log.trace("Host requested: {}", getHost());

		this.mainService.speedup(datas);

		return ResponseEntity.ok().build();
	}


	private String getHost() {
		try {
			return InetAddress.getLocalHost().toString();
		} catch (UnknownHostException e) {
			return "UnknownHost" + e.getMessage();
		}
	}
}