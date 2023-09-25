package com.example.todo.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo.dto.ResponseDTO;
import com.example.todo.dto.TodoDTO;
import com.example.todo.model.TodoEntity;
import com.example.todo.service.TodoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("todo")
public class TodoController {
	@Autowired
	private TodoService service;
	
	@PostMapping
	public ResponseEntity<?>createTodo(@RequestBody TodoDTO dto){
		try {
			//Post localhost:8080/todo 
			log.info("Log: createTodo entrance");
			
			//dto를 이용해 테이블에 저장하기 위한 entity를 생성 
			TodoEntity entity = TodoDTO.toEntity(dto);
			log.info("Log : dto => entity ok!");
			
			// entity userId를 임시로 지정한다 
			entity.setUserId("temporary-user");
			
			// service.create를 통해 repository에 entity를 저장 
			// 이떄 넘어오는 값이 없을수도 있으므로 List가 아닌 Optional로 한다 
			Optional<TodoEntity> entities = service.create(entity);
			log.info("Log : service.create ok!");
			
			// entities를 dtos로 스트림 변환 
			List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
			
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
			log.info("Log : responsedto ok!");
			
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			String error = e.getMessage();
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
		
	@GetMapping
	public ResponseEntity<?>retrieveTodoList(){
		String temporaryUserId = "temporary-user";
		List<TodoEntity> entities = service.retrieve(temporaryUserId);
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
			
		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
			
		return ResponseEntity.ok().body(response);
	}
	
}
