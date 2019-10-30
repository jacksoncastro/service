package br.com.jackson.vo;

import java.util.Set;

import lombok.Data;

@Data
public class DataVO {

	private Long timeout;

	private Long sleep;

	private String service;

	private Set<DataVO> next;

}