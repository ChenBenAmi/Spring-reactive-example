package com.example.reactivebooks.service;

import org.springframework.stereotype.Service;

import com.example.reactivebooks.document.Book;
import com.example.reactivebooks.repository.BookRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{

	private final BookRepository repository;

	@Override
	public Flux<Book> getAll() {
		return repository.findAll();
	}

	@Override
	public Mono<Book> findById(String id) {
		return repository.findById(id);
	}

	@Override
	public Mono<Book> save(Mono<Book> book) {
		return book.flatMap(repository::save);
	}
	
	
}
