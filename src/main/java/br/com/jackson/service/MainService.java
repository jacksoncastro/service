package br.com.jackson.service;

import java.time.Duration;
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
import br.com.jackson.vo.Speedup;

@Service
public class MainService {

	private static final Logger log = LoggerFactory.getLogger(MainController.class);

	private static final long DEFAULT_SLEEP = 1000L;

	private static final int DEFAULT_DEVIATION = 20;

	private static final int ONE_HUNDRED = 100;

	@Autowired
	private RestTemplateBuilder restTemplateBuilder;

	@Autowired
	private Random random;

	@Autowired
    private Environment env;


	public void speedup(RequestVO requestVO) {
		if (requestVO != null) {
			if (requestVO.getNext() != null && !requestVO.getNext().isEmpty()) {
				requestVO.getNext().forEach(this::next);
			}
			long sleep = calculeSleep(requestVO);
			log.trace("Sleep environment: {}", sleep);
			sleep(sleep);
		}
	}


	/**
	 * Call one RequestVO
	 *
	 * @author Jackson Castro
	 * @date 2019-11-03
	 *
	 * @version 1.0.0
	 */
	private void next(RequestVO data) {
		String service = data.getService();
		if (service != null && !service.trim().isEmpty()) {
			// clear service
			data.setService(null);
			// timeout
			RestTemplate restTemplate = getRestTemplate(10000L);
			restTemplate.postForEntity(service, data, String.class);
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
//		deviation / average
		return (long) (this.random.nextGaussian() * deviation + average);
	}

	private double getPercent(double value, int percent) {
		return (value * percent) / ONE_HUNDRED;
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
			Thread.sleep(0);
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
	 * @param requestVO 
	 */
	private long calculeSleep(RequestVO requestVO) {
		if (requestVO.getSpeedup() == Speedup.REAL) {
			
		}
		return this.env.getProperty(Environments.SERVICE_SLEEP, Long.class, DEFAULT_SLEEP);
	}
}