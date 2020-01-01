package com.example.reactivebooks.initializer;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.example.reactivebooks.document.Book;
import com.example.reactivebooks.repository.BookRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitDb {
//spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration

	private final BookRepository repository;

	@PostConstruct
	public void init() {

		final Flux<Book> book = Flux.just("12 Rules of Life", "Crime and Punishment", "The subtle art")
				.map(title -> new Book(null, title)).flatMap(repository::save);

		repository.deleteAll().thenMany(book).thenMany(repository.findAll())
				.subscribe(savedBook -> log.info("The following book was saved {}", savedBook));
	}
}
