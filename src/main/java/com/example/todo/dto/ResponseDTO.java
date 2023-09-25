package com.example.todo.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data


public class ResponseDTO<T> {
	// 타입이 정해지지 않을떄 T선언 generic type
	private String error;
	private List<T> data;
}
