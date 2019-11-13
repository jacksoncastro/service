package br.com.jackson.vo;

import java.util.Set;

import lombok.Data;


@Data
public class RequestVO {

	private int timeout;

	private int media;

	private int desvio;

	private String service;
	// TODO verificar outra forma de deixar default
	private Speedup speedup = Speedup.NONE;

	private Set<RequestVO> next;

}