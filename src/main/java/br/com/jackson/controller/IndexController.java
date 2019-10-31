package br.com.jackson.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {


	@RequestMapping("/")
	public String index() {
		try {
			return InetAddress.getLocalHost().toString();
		} catch (UnknownHostException e) {
			return "UnknownHost" + e.getMessage();
		}
	}
}