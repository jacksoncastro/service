package br.com.jackson.service;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.uncommons.maths.random.GaussianGenerator;

import br.com.jackson.controller.MainController;
import br.com.jackson.vo.RequestVO;
import br.com.jackson.vo.Speedup;
import br.com.jackson.vo.TypeRequest;
import br.com.jackson.vo.TypeSpeedup;

@Service
public class MainService {

	private static final Logger log = LoggerFactory.getLogger(MainController.class);

	private static final int DEFAULT_AVERAGE = 0;

	private static final int DEFAULT_DEVIATION = 20;

	private static final double ONE_HUNDRED = 100d;

	private static final double ZERO = 0;

	@Autowired
	private RestTemplateBuilder restTemplateBuilder;

	@Autowired
	private Random random;

	@Autowired
	private ExecutorService executorService;


	/**
	 * Process a request object
	 *
	 * @author Jackson Castro
	 * @date 2019-11-14
	 *
	 * @version 1.0.0
	 */
	public void speedup(RequestVO requestVO) {

		if (requestVO != null) {
			if (requestVO.getNext() != null && !requestVO.getNext().isEmpty()) {

				if (requestVO.getType() == TypeRequest.ASYNC) {
					ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(10);
				}

				List<Callable<Void>> callables = requestVO.getNext()
														  	.stream()
														  	.map(this::next)
														  	.collect(Collectors.toList());

				try {
					this.executorService.invokeAll(callables);
				} catch (InterruptedException e) {
					throw new RuntimeException("Error to invoke all.", e);
				}
			}
			long sleep = calculateSleep(requestVO);
			log.trace("Sleeping {} milliseconds", sleep);
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
	 * @return 
	 */
	private Callable<Void> next(RequestVO data) {

		String service = data.getService();
		if (service != null && !service.trim().isEmpty()) {
			Long timeout = data.getTimeout();

			// clear service
			data.setService(null);
			data.setTimeout(null);

			// request next service
			return asyncRequest(service, data, timeout);

//			RestTemplate restTemplate = getRestTemplate(timeout);
//			restTemplate.postForEntity(service, data, String.class);
		}
		return null;
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


    public Callable<Void> asyncRequest(String url, RequestVO data, Long timeout) {
		return () -> {
			// request next service
			RestTemplate restTemplate = getRestTemplate(timeout);
			restTemplate.postForEntity(url, data, String.class);
			return null;
		};
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
	private long getNormalDistribution(double average, int deviation) {
		double percent = getPercent(average, deviation);
		GaussianGenerator generator = new GaussianGenerator(average, percent * 0.5, this.random);
		return generator.nextValue().longValue();
	}


	/**
	 * Get percent of a value.
	 *
	 * @author Jackson Castro
	 * @date 2019-11-13
	 *
	 * @version 1.0.0
	 */
	private double getPercent(double value, double percent) {
		if (percent < 0) {
			percent = ZERO;
		}
		if (percent > ONE_HUNDRED) {
			percent = ONE_HUNDRED;
		}
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
	private void sleep(long sleep) {
		try {
			log.trace("Sleeping {}", sleep);
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Calculate sleep.
	 *
	 * @author Jackson Castro
	 * @date 2019-11-06
	 *
	 * @version 1.0.0
	 * @param request 
	 */
	private long calculateSleep(RequestVO request) {

		// set default values
		setDefaut(request);

		// speedup real
		if (request.getSpeedup().getType() == TypeSpeedup.REAL) {
			return calculateSpeedupReal(request);
		}
		// speedup virtual direct
		if (request.getSpeedup().getType() == TypeSpeedup.VIRTUAL_DIRECT) {
			return calculateSpeedupVirtualDirect(request);
		}
		// speedup virtual indirect
		if (request.getSpeedup().getType() == TypeSpeedup.VIRTUAL_INDIRECT) {
			return calculateSpeedupVirtualIndirect(request);
		}

		return request.getAverage();
	}


	/**
	 * Set default value in request
	 *
	 * @author Jackson Castro
	 * @date 2019-11-13
	 *
	 * @version 1.0.0
	 */
	private void setDefaut(RequestVO request) {

		if (request.getAverage() == null) {
			request.setAverage(DEFAULT_AVERAGE);
		}

		if (request.getDeviation() == null) {
			request.setAverage(DEFAULT_DEVIATION);
		}

		if (request.getSpeedup() == null) {
			request.setSpeedup(new Speedup());
			request.getSpeedup().setType(TypeSpeedup.NONE);
		}
	}


	/**
	 * Calculate speedup real
	 *
	 * @author Jackson Castro
	 * @date 2019-11-13
	 *
	 * @version 1.0.0
	 */
	private long calculateSpeedupReal(RequestVO request) {

		int average = request.getAverage();
		int deviation = request.getDeviation();
		double value = request.getSpeedup().getValue();
		double percent = getPercent(average, value);

		double subtract = average - percent;

		return getNormalDistribution(subtract, deviation);
	}


	/**
	 * Calculate speedup virtual direct
	 *
	 * @author Jackson Castro
	 * @date 2019-11-13
	 *
	 * @version 1.0.0
	 */
	private long calculateSpeedupVirtualDirect(RequestVO request) {

		int average = request.getAverage();
		int deviation = request.getDeviation();
		double value = request.getSpeedup().getValue();
		double percent = getPercent(average, value);

		double plus = average + percent;

		return getNormalDistribution(plus, deviation);
	}


	/**
	 * Calculate speedup virtual indirect
	 *
	 * @author Jackson Castro
	 * @date 2019-11-13
	 *
	 * @version 1.0.0
	 */
	private long calculateSpeedupVirtualIndirect(RequestVO request) {

		int average = request.getAverage();
		int deviation = request.getDeviation();

		int distribution = (int) getNormalDistribution(average, deviation);

		double value = request.getSpeedup().getValue();
		double percent = getPercent(average, value);

		return (long) (distribution + percent);
	}
}