package com.fdmgroup.apmproject.service;

import org.springframework.stereotype.Service;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.fdmgroup.apmproject.model.User;
import com.fdmgroup.apmproject.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepo;
	
	private static Logger logger = LogManager.getLogger(UserService.class);
	
	public UserService(UserRepository userRepo) {
		this.userRepo = userRepo;
	}
	
	public void persist(User user) {
		Optional<User> returnedUser = userRepo.findByUsername(user.getUsername());
		if (returnedUser.isEmpty()) {
			userRepo.save(user);
			logger.info("Customer successfully registered");
		} else {
			logger.warn("Customer already exists");
		}
	}
	
	public void update(User user) {
		Optional<User> returnedUser = userRepo.findByUsername(user.getUsername());
		if (returnedUser.isEmpty()) {
			logger.warn("Customer does not exist in database");
		} else {
			userRepo.save(user);
			logger.info("Customer successfully updated");
		}
	}
	
	public User findCustomerById(long customerId) {
		Optional<User> returnedUser = userRepo.findById(customerId);
		if (returnedUser.isEmpty()) {
			logger.warn("Could not find Customer in Database");
			return null;
		} else {
			logger.info("Returning customer's details");
			return returnedUser.get();
		}
	}
	
	public User findCustomerByUsername(String username) {
		Optional<User> returnedUser = userRepo.findByUsername(username);
		if (returnedUser.isEmpty()) {
			logger.warn("Could not find Customer in Database");
			return null;
		} else {
			logger.info("Returning customer's details");
			return returnedUser.get();
		}
	}
	
	public void deleteById(long id) {
		Optional<User> returnedUser = userRepo.findById(id);

		if (returnedUser.isEmpty()) {
			logger.warn("Customer does not exist in database");
		} else {
			userRepo.deleteById(id);
			logger.info("Customer deleted from Database");
		}
	}
}
