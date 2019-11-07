package br.com.jackson.service;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.jackson.controller.MainController;
import br.com.jackson.vo.Environments;
import br.com.jackson.vo.RequestVO;

@Service
public class MainService {

	private static final Logger log = LoggerFactory.getLogger(MainController.class);

	private static final long DEFAULT_SLEEP = 1000L;

	private static final int DEFAULT_DEVIATION = 100;

	@Autowired
	private RestTemplateBuilder restTemplateBuilder;

	@Autowired
	private Random random;

	@Autowired
    private Environment env;


	public void speedup(List<RequestVO> datas) {

		if (datas != null && !datas.isEmpty()) {
			datas.forEach(this::call);
		}

		long sleep = getSleep();
		log.trace("Sleep environment: {}", sleep);
		sleep(sleep);
	}


	/**
	 * Call one RequestVO
	 *
	 * @author Jackson Castro
	 * @date 2019-11-03
	 *
	 * @version 1.0.0
	 */
	private void call(RequestVO data) {

		if (data.getNext() != null && !data.getNext().isEmpty()) {
			data.getNext().forEach(this::call);
		}

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
			           .setReadTimeout(duration)
			           .build();
	}


	/**
	 * Return a normal distribution, also sometimes called a Gaussian distribution.
	 *
	 * @see <a href="https://www.javamex.com/tutorials/random_numbers/gaussian_distribution_2.shtml">Gaussian Distribution</a>
	 *
	 * @param average of distribution
	 * @param deviation standard deviation of distribution
	 *
	 * @author Jackson Castro
	 * @date 2019-11-03
	 *
	 * @version 1.0.0
	 */
	private long getNormalDistribution(long average, int deviation) {
		return (long) (this.random.nextGaussian() * deviation + average);
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
			long normalDistribution = getNormalDistribution(sleep, DEFAULT_DEVIATION);
			log.trace("Sleep by normal distribution: {}", normalDistribution);
			Thread.sleep(normalDistribution);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Get sleep
	 *
	 * @author Jackson Castro
	 * @date 2019-11-06
	 *
	 * @version 1.0.0
	 */
	private long getSleep() {
		return this.env.getProperty(Environments.SERVICE_SLEEP, Long.class, DEFAULT_SLEEP);
	}
}