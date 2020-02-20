package com.example.demo;

import org.springframework.data.repository.CrudRepository;

public interface Repository extends CrudRepository<Todo, Long> {
}
