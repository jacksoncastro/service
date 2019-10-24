package br.com.jackson.controller;

import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import br.com.jackson.vo.DataVO;

@RestController
@RequestMapping(value = "/api")
public class MainController {

	@Autowired
	private RestTemplateBuilder restTemplateBuilder;


	@PostMapping
	public ResponseEntity<?> action(@RequestBody List<DataVO> datas) {

		if (datas != null && !datas.isEmpty()) {

			DataVO firstData = datas.remove(0);

			System.out.printf("Serviço %s com sleep %d\n", firstData.getNext(), firstData.getSleep());

			sleep(firstData.getSleep());

			if (datas.size() > 0) {
				RestTemplate restTemplate = getRestTemplate(firstData.getTimeout());
			    HttpHeaders headers = new HttpHeaders();
			    headers.setContentType(MediaType.APPLICATION_JSON);
			    restTemplate.postForEntity(datas.get(0).getNext(), datas, String.class);
			}
		}

		return ResponseEntity.ok().build();
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
					   .setConnectTimeout(duration)
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
	private void sleep(long sleep) {
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}