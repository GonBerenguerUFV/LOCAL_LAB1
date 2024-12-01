package com.example.demo;

//import org.junit.jupiter.api.Test;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class BackendApplicationTests {
	@Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CommunityService communityService;

    @LocalServerPort
    private int port;

    private String baseUrl;

    @Before
    public void setUp() {
        baseUrl = "http://localhost:" + port + "/api/communities";
    }

	@Test
    public void testGetAllCommunities() {
        // Calculate all the records in the repository
        int numberCommunities = communityService.findAll().size();

        // Check that when queried all the communities we get the same number as in the repository
        ResponseEntity<List<Community>> response = restTemplate.exchange(
                baseUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<Community>>() {});

        // Check that the status is OK
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Check the number of results
        assertThat(response.getBody()).hasSize(numberCommunities);
    }

    @Test
    public void testGetCommunityById() {
        // Create a community that we know exists in the JSON file
        Community community = new Community(
            "cdc9bf1c-8352-49e6-8d40-937266f61e00", 
            "Total Nacional", 
            "", 
            "", 
            "", 
            "2024M07", 
            18338774L);

        // Seach for the community
        ResponseEntity<Community> response = restTemplate.getForEntity(
                baseUrl + "/" + community.getId(), Community.class);

        // Check that the status is OK
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Check that the community retrieved is the one we were searching for
        assertThat(response.getBody().getId()).isEqualTo(community.getId());
    }

    @Test
    public void testCreateCommunity() {
        // Create a community
        Community community = new Community(
            UUID.randomUUID().toString(), 
            "Madrid", 
            "Madrid", 
            "Aragón", 
            "Teruel", 
            "2024M07", 
            2000L);

        // Create the community using the service
        ResponseEntity<Void> response = restTemplate.postForEntity(
                baseUrl, community, Void.class);

        // Check the result of the operation is OK
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        // Search the community and check that they are the same
        Community communityFound = communityService.findById(community.getId());
        assertThat(communityFound.toString()).isEqualTo(community.toString());
    }

    @Test
    public void testUpdateCommunity() {
        // Create a community
        Community community = new Community(
            UUID.randomUUID().toString(), 
            "Cataluña", 
            "Barcelona", 
            "Aragón", 
            "Teruel", 
            "2024M07", 
            3000L);

        // Create the community using the service
        ResponseEntity<Void> response = restTemplate.postForEntity(
                baseUrl, community, Void.class);

        // Check the result of the operation
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        // Update the community
        community.setTotal(4000L);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Community> requestEntity = new HttpEntity<>(community, headers);
        response = restTemplate.exchange(
                baseUrl + "/" + community.getId(), HttpMethod.PUT, requestEntity, Void.class);

        // Check that when we search the community, the result is updated
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Community communityFound = communityService.findById(community.getId());
        assertThat(communityFound.getTotal()).isEqualTo(community.getTotal());

    }


    @Test
    public void testDeleteCommunity() {
        // Create a community
        Community community = new Community(
            UUID.randomUUID().toString(), 
            "Cataluña", 
            "Barcelona", 
            "Aragón", 
            "Teruel", 
            "2024M07", 
            3000L);
        communityService.save(community);

        restTemplate.delete(baseUrl + "/" + community.getId());

        assertThat(communityService.findById(community.getId())).isNull();;
    }

}
