package br.com.jackson.vo;

import java.util.Set;

import lombok.Data;


@Data
public class RequestVO {

	private Double media;

	private int desvio;

	private String service;

	private Speedup speedup;

	private Set<RequestVO> next;

}