package com.example.data.dao;

import com.example.data.models.Testing;
import org.springframework.data.repository.CrudRepository;

public interface TestingDao extends CrudRepository<Testing, Integer> {
}
