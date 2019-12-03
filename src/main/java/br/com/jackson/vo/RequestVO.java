package br.com.jackson.vo;

import java.util.List;

import lombok.Data;


@Data
public class RequestVO {

	private Long timeout;

	private Integer average;

	private Integer deviation;

	private String service;

	private Speedup speedup;

	private TypeRequest type;

	private List<RequestVO> next;

}