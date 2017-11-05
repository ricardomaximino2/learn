package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.beans.User;
import com.example.demo.repository.UserRepository;

@Transactional
@Service
public class UserService {

	@Autowired
	private UserRepository repository;
	
	public User save(User user) {
		return repository.save(user);
	}
	
	public User getUserById(long id) {
		return repository.findOne(id);
	}
	
	public List<User> getAllUsers(){
		List<User> list = new ArrayList<>();
		for(User u : repository.findAll()) {
			list.add(u);
		}
		return list;
	}
	
	public boolean deleteUserById(long id) {
		boolean result = false;
		User user = repository.findOne(id);
		if(user == null) {
			throw new NoSuchElementException("This user with id: " + id + " do not exist");
		}else {
			repository.delete(user);
			result = true;
		}
		return result;
	}
}
