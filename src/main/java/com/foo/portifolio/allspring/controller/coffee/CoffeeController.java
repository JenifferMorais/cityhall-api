package com.foo.portifolio.allspring.controller.coffee;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("coffee")
public class CoffeeController {
	@GetMapping
	public ResponseEntity<Dto> imATeapot() {
		return new ResponseEntity<Dto>(new Dto("I'm a teapot!"),HttpStatus.I_AM_A_TEAPOT);
	}

	public class Dto {
		public Dto(String message) {
			super();
			this.message = message;
		}

		private String message;

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public LocalDateTime getNow() {
			return LocalDateTime.now();
		}

	}
}