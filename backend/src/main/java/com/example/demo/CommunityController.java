package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// Access to Swagger: http://localhost:8080/swagger-ui/index.html

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class CommunityController {
    @Autowired
	CommunityService communityService;
	
	@GetMapping("/communities")
	public ResponseEntity<List<Community>> getAllCommunities() {
		try {
			List<Community> communities = new ArrayList<Community>();
			communityService.findAll().forEach(communities::add);
						
			if (communities.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<>(communities, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/communities/{id}")
	public ResponseEntity<Community> getCommunityById(@PathVariable("id") String id) {
		Community community = communityService.findById(id);
		
		if (community != null) {
			return new ResponseEntity<>(community, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/communities")
	public ResponseEntity<Community> createCommunity(@RequestBody Community community) {
		try {
			Community _community = communityService.save(
                new Community(
                    community.getId(), 
                    community.getFrom().getComunidad(), 
                    community.getFrom().getProvincia(), 
                    community.getTo().getComunidad(), 
                    community.getTo().getProvincia(), 
                    community.getTimeRange().getPeriod(),
                    community.getTotal()
                    ));
			return new ResponseEntity<>(_community, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/communities/{id}")
	public ResponseEntity<Community> updateCommunity(@PathVariable("id") String id, @RequestBody Community community) {
		Community _community = communityService.findById(id);
		try {
			if (_community != null) {
				_community.setFrom(community.getFrom());
				_community.setTo(community.getTo());
				_community.setTimeRange(community.getTimeRange());
				_community.setTotal(community.getTotal());
				return new ResponseEntity<>( communityService.save(_community), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/communities/{id}")
	public ResponseEntity<HttpStatus> deleteCommunity(@PathVariable("id") String id) {
		try {
			communityService.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/communities")
	public ResponseEntity<HttpStatus> deleteAllCommunitys() {
		try {
			communityService.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
}
