package com.example.reactivebooks.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.example.reactivebooks.document.Book;

@Repository
public interface BookRepository extends ReactiveCrudRepository<Book, String> {
	
}