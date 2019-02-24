package org.umsl.ylnf2.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;
import org.umsl.ylnf2.entity.Admin;
import org.umsl.ylnf2.entity.User;
import org.umsl.ylnf2.service.UserService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private UserService userService;
	
	@Autowired(required=true)
	@Qualifier(value="userService")
	public void setUserService(UserService us) {
		this.userService = us;
	}
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
	public ResponseEntity<?> getUserById(@PathVariable("userId") int id) {
		try {
			User result = userService.getUserById(id);
			logger.info("/user/" + id + " GET request received and successful");
			return new ResponseEntity<User>(result, HttpStatus.OK);
		}catch (Exception e) {
			String errorMessage = e + " <=== Error";
			logger.error("/user/" + id + " GET request received but failed to process: " + errorMessage);
			return new ResponseEntity<String>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/user/{userId}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUserById(@PathVariable("userId") int id, @RequestBody User u) {
		try {
			if(u == null) {
				logger.info("/user/" + id + " PUT request received and successful");
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			User result = userService.getUserById(id);
			result.setUsername(null!=u.getUsername() ? u.getUsername() : result.getUsername());
			result.setPassword(null!=u.getPassword() ? u.getPassword() : result.getPassword());
			result.setFirstName(null!=u.getFirstName() ? u.getFirstName() : result.getFirstName());
			result.setLastName(null!=u.getLastName() ? u.getLastName() : result.getLastName());
			userService.updateUser(result);
			logger.info("/user/" + id + " PUT request received and successful. Received: " + u);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}catch (Exception e) {
			String errorMessage = e + " <=== Error";
			logger.error("/user/" + id + " PUT request received but failed to process: " + errorMessage);
			return new ResponseEntity<String>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/user/{userId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUserById(@PathVariable("userId") int id, @RequestBody Admin a) {
		try {
			if(null == a || !a.isAdmin()) {
				logger.info("/user/" + id + " DELETE request received but failed because admin validation failed. Received: " + a);
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}
			User u = userService.getUserById(id);
			userService.deleteUser(u);
			logger.info("/user/" + id + " DELETE request received and successful. Received: " + id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}catch (Exception e) {
			if(e.getClass() == org.hibernate.ObjectNotFoundException.class) {
				logger.info("/user/" + id + " DELETE request received but failed because record does not exist. Received: " + id);
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			String errorMessage = e + " <=== Error";
			logger.error("/user/" + id + " DELETE request received but failed to process: " + errorMessage);
			return new ResponseEntity<String>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public ResponseEntity<?> addUser(@RequestBody User u, UriComponentsBuilder ucBuilder) {
		try {
			List<String> emptyFieldName = new ArrayList<>();
			if(u.getLastName() == null) emptyFieldName.add("last name");
			if(u.getFirstName() == null) emptyFieldName.add("first name");
			if(u.getPassword() == null) emptyFieldName.add("password");
			if(u.getUsername() == null) emptyFieldName.add("username");
			if(emptyFieldName.size() != 0) {
				logger.info("/user POST request received but failed due to missing field(s). Missing: " + emptyFieldName.toString());
				String error = "Missing " + emptyFieldName.toString();
				return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
			}
			User result = userService.addUser(u);
			logger.info("/user POST request received and successful. Created: " + result);
			HttpHeaders headers = new HttpHeaders();
		    headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(result.getUsersID()).toUri());
			return new ResponseEntity<User>(result, headers, HttpStatus.CREATED);
		}catch (Exception e) {
			String errorMessage = e + " <=== Error";
			logger.error("/user POST request received but failed to process: " + errorMessage);
			return new ResponseEntity<String>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public ResponseEntity<?> getUsers() {
		try {
			List<User> result = userService.listUsers();
			logger.info("/user GET request received and successful");
			return new ResponseEntity<List<User>>(result, HttpStatus.OK);
		}catch (Exception e) {
			String errorMessage = e + " <=== Error";
			logger.error("/user GET request received but failed to process: " + errorMessage);
			return new ResponseEntity<String>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.OPTIONS)
	public ResponseEntity<?> getUserOptions() {
		try {
			HttpHeaders headers = new HttpHeaders();
		    headers.set("Allow","GET, OPTIONS, POST");
			logger.info("/user OPTIONS request received and successful");
			return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
		}catch (Exception e) {
			String errorMessage = e + " <=== Error";
			logger.error("/user GET request received but failed to process: " + errorMessage);
			return new ResponseEntity<String>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/user/{userId}", method = RequestMethod.OPTIONS)
	public ResponseEntity<?> getUserWithIDOptions() {
		try {
			HttpHeaders headers = new HttpHeaders();
		    headers.set("Allow","DELETE, GET, OPTIONS, PUT");
			logger.info("/user/{userId} OPTIONS request received and successful");
			return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
		}catch (Exception e) {
			String errorMessage = e + " <=== Error";
			logger.error("/user GET request received but failed to process: " + errorMessage);
			return new ResponseEntity<String>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
