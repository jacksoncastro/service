package br.com.jackson.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import br.com.jackson.vo.DataVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api")
public class MainController {

	@Autowired
	private RestTemplateBuilder restTemplateBuilder;

	

	// ingress controller nginx
	@PostMapping
	public ResponseEntity<?> action(@RequestBody List<DataVO> datas) {

		if (datas != null && !datas.isEmpty()) {
			datas.forEach(this::call);
		}
		log.info("Host requested: {}", getHost());
		return ResponseEntity.ok().build();
	}


	private String getHost() {
		try {
			return InetAddress.getLocalHost().toString();
		} catch (UnknownHostException e) {
			return "UnknownHost" + e.getMessage();
		}
	}


	public void call(DataVO data) {

		// sleep
		sleep(data.getSleep());

		if (data.getService() != null && !data.getService().trim().isEmpty()) {
			if (data.getNext() == null) {
				data.setNext(Collections.emptySet());
			}
			// timeout
			RestTemplate restTemplate = getRestTemplate(data.getTimeout());
			restTemplate.postForEntity(data.getService(), data.getNext(), String.class);
		}
	}


	/**
	 * Generate a custom RestTemplate
	 *
	 * @author Jackson Castro
	 * @date 2019-10-23
	 *
	 * @param timeout 
	 *
	 * @version 1.0.0
	 */
	private RestTemplate getRestTemplate(Long timeout) {

		if (timeout == null) {
			return new RestTemplate();
		}

		Duration duration = Duration.ofMillis(timeout);

		return this.restTemplateBuilder
//					   .setConnectTimeout(duration)// TODO deixar default
			           .setReadTimeout(duration)
			           .build();
	}


	/**
	 * Quietly sleep
	 *
	 * @author Jackson Castro
	 * @date 2019-10-23
	 *
	 * @version 1.0.0
	 */
	private void sleep(Long sleep) {
		if (sleep == null) {
			return;
		}
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}