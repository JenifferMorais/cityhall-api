package com.foo.portifolio.allspring.controller.todos;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foo.portifolio.allspring.controller.BaseController;
import com.foo.portifolio.allspring.model.dto.todo.TodoDTO;
import com.foo.portifolio.allspring.model.entity.todo.Todo;
import com.foo.portifolio.allspring.service.BaseService;

@RestController
@RequestMapping("todo")
public class TodoController extends BaseController<Todo,TodoDTO,Integer>{

	public TodoController(BaseService<Todo, TodoDTO, Integer> service) {
		super(service);
	}

}
