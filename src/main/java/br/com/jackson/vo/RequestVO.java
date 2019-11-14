package br.com.jackson.vo;

import java.util.Set;

import lombok.Data;


@Data
public class RequestVO {

	private Long timeout;

	private Integer average;

	private Integer deviation;

	private String service;

	private Speedup speedup;

	private Set<RequestVO> next;

}