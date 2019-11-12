/**
 * 
 */
package com.restful.webservice.restfulwebservice.users;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.restful.webservice.restfulwebservice.users.exception.CustomResponseEntityExceptionHandler;
import com.restful.webservice.restfulwebservice.users.exception.UserNotFoundException;

/**
 * @author Balaji
 *
 */
@RestController
public class UserRestController extends CustomResponseEntityExceptionHandler {

	@Autowired
	private UserDAOService userDAOService;

	@GetMapping(path = "/users")
	public List<User> retrieveAlUsers(){
		return userDAOService.findAll();
	}

	@GetMapping(path = "/users/{id}")
	public User findUser(@PathVariable Integer id) {
		User findUser = userDAOService.findUser(id);
		if(null == findUser) {
			throw new UserNotFoundException("id = " + id);			
		}
		return findUser;
	}
	
	@PostMapping("/save")
	public ResponseEntity<Object> save(@Valid @RequestBody User user){
		User savedUser = userDAOService.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(savedUser.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/users/delete/{id}")
	public User delete(@PathVariable Integer id) {
		User deletedUser = userDAOService.deleteUser(id);
		if(null == deletedUser) {
			throw new UserNotFoundException("id = " + id);			
		}
		return deletedUser;
	}
}