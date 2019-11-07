package br.com.jackson.vo;

import java.util.Set;

import lombok.Data;


@Data
public class RequestVO {

	private Long timeout;

	private String service;

	private Set<RequestVO> next;

}