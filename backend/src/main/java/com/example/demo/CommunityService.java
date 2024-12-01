package com.example.demo;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import java.io.Reader;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

//import com.dislab2.backend.Community;

import org.springframework.stereotype.Service;

@Service
public class CommunityService {

    static List<Community> communities = LoadFromJSON();
    
    public List<Community> findAll() {
        return communities;
    }
    
    public Community findById(String id) {
        Community result = communities.stream().filter(community -> id.equals(community.getId())).findAny().orElse(null);
        return result;
    }
    
    public Community save(Community community) {
        // update Community
        if (!community.getId().equals("")) {
            String _id = community.getId();
            Boolean updatedCommunity = false;
            // find the community
            for (int idx = 0; idx < communities.size(); idx++) {
                if (_id.equals(communities.get(idx).getId())) {
                    communities.set(idx, community);
                    updatedCommunity = true;
                    break;
                }
            }
            if (updatedCommunity) {
                //TODO: save JSON file
                return community;
            }
        }
        // create new Community
        if (community.getId().equals("")) {
            community.setId(UUID.randomUUID().toString());
        }
        communities.add(community);
        //TODO: save JSON file
        return community;
    }
    
    public void deleteById(String id) {
        communities.removeIf(community -> id.equals(community.getId()));
    }
    
    public void deleteAll() {
        communities.removeAll(communities);
    }

    public static List<Community> LoadFromJSON() {  
        String filePathJSON = new Config().getProperty("jsonFilePath");
    
        List<Community> communities = new ArrayList<Community>();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (Reader reader = new FileReader(filePathJSON)) {
            // Parse JSON to a list of comunities objects
            Type listType = new TypeToken<List<Community>>() {}.getType();
            communities = gson.fromJson(reader, listType);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return communities;
    }
    
}
