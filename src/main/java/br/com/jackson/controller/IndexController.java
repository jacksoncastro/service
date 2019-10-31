package br.com.jackson.controller;

import java.net.InetAddress;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {


	@RequestMapping("/")
	public String index() {
		return InetAddress.getLoopbackAddress().toString();
	}
}