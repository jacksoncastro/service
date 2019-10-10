package br.com.jackson.controller;

import java.util.List;

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


	@PostMapping
	public ResponseEntity<?> action(@RequestBody List<DataVO> datas) {

		if (datas != null && !datas.isEmpty()) {

			DataVO firstData = datas.remove(0);

			System.out.printf("Serviço %s com sleep %d\n", firstData.getNext(), firstData.getSleep());

			sleep(firstData);

			if (datas.size() > 0) {
				RestTemplate restTemplate = new RestTemplate();
				restTemplate = new RestTemplate();
			    HttpHeaders headers = new HttpHeaders();
			    headers.setContentType(MediaType.APPLICATION_JSON);
			    restTemplate.postForEntity(firstData.getNext(), datas, String.class);
			}
		}

		return ResponseEntity.ok().build();
	}


	private void sleep(DataVO firstData) {
		try {
			Thread.sleep(firstData.getSleep());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}