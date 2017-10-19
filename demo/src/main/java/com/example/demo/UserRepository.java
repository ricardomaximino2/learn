package com.example.demo;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.beans.User;

public interface UserRepository extends CrudRepository<User, Long>{

}
