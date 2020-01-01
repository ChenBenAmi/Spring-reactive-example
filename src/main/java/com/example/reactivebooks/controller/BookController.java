package com.example.reactivebooks.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reactivebooks.document.Book;
import com.example.reactivebooks.service.BookService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

	private final BookService service;

	@GetMapping("getAll")
	public Flux<Book> getAll() {
		return service.getAll();
	}

	@GetMapping("/{id}")
	public Mono<Book> findById(@PathVariable final String id) {
		return service.findById(id);
	}

	@PostMapping("/save")
	public Mono<Book> save(@RequestBody final Mono<Book> book) {
		return service.save(book);
	}

}
