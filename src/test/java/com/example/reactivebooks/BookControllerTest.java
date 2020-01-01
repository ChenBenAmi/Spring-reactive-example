package com.example.reactivebooks;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import com.example.reactivebooks.controller.BookController;
import com.example.reactivebooks.document.Book;
import com.example.reactivebooks.repository.BookRepository;
import com.example.reactivebooks.service.BookService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RunWith(SpringJUnit4ClassRunner.class)
@WebFluxTest({ BookController.class, BookService.class })
public class BookControllerTest {
	@Autowired
	private WebTestClient client;

	@MockBean
	private BookRepository repository;

	private Book book;

	@Before
	public void init() {
		book = new Book("001ID", "TestBook");

		Mockito.when(repository.findAll()).thenReturn(Flux.fromIterable(data()));
		Mockito.when(repository.findById(book.getId())).thenReturn(Mono.just(book));

		Mockito.when(repository.save(book)).thenReturn(Mono.just(book));
	}

	private List<Book> data() {
		return List.of(new Book(null, "12 Rules of Life"), new Book(null, "Crime and Punishment"),
				new Book(null, "The subtle art"));
	}

	@Test
	public void getAll() {
		client.get().uri("/books/getAll").exchange().expectStatus().isOk().expectBodyList(Book.class).hasSize(3);
	}

	@Test
	public void findById() {
		client.get().uri("/books/{id}", book.getId()).exchange().expectStatus().isOk().expectBody(Book.class)
				.isEqualTo(book);
	}

	@Test
	public void save() {
		client.post().uri("/books/save").contentType(MediaType.APPLICATION_JSON).body(Mono.just(book), Book.class).exchange()
				.expectStatus().isOk().expectBody(Book.class).isEqualTo(book);
	}
}
