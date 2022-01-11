package com.neosoft.Poc.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.neosoft.Poc.Repository.UserRepository;
import com.neosoft.Poc.model.User;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	public User addUser(User user) {
		return userRepo.save(user);
	}
	
	public Optional<User> deleteUser(Long id) {
		userRepo.deleteById(id);
		return null;
	}
	
	public List<User> GetUser(){
	 return	userRepo.findAll();
	}
	
	public User updateById(@PathVariable Long id,@RequestBody User user) {
		User u1=userRepo.getById(id);
		u1.setFname(user.getFname());
		u1.setSurname(user.getSurname());
		u1.setDOB(user.getDOB());
		u1.setJoiningDate(user.getJoiningDate());
		u1.setMobileNo(user.getMobileNo());
		
		userRepo.save(u1);
		return null;
		
		
	}
	

}
