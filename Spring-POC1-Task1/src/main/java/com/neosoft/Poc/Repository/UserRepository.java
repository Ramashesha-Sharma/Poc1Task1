package com.neosoft.Poc.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.neosoft.Poc.model.User;



public interface UserRepository  extends JpaRepository<User,Long>{

	public  List<User> findByFname(String fname);
	
	public List<User> findBySurname(String surname);
	
	public List<User> findByPincode(String pincode);
	
	public List<User> findByFnameOrSurnameOrPincode(String fname,String surname,String pincode);
	
	@Query("update User set deleted=true where id=?1")
	@Modifying
	@Transactional
	public void softDelete(Long id);
}
