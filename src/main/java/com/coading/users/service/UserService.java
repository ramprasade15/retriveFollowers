package com.coading.users.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.coading.users.FollowerInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.URI;

@Service

public class UserService {

	private static String getUserurl = "https://api.github.com/users/{id}";
	private static Integer pageNumber = 1;
	private static Integer perPage = 5;
	
	RestTemplate restTemplate = new RestTemplate();
	
	@Autowired
	private Gson gson;
	
	FollowerInfo followerInfoList;
	
	public String getFollowersList(String id) {
		String followerJson = null;
		Map<String, Map> followerMap2 = new HashMap<String, Map>();
		List<FollowerInfo> f = new ArrayList<FollowerInfo>();
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		
		UriComponentsBuilder followerurl = UriComponentsBuilder.fromUriString(getUserurl);
		
		String result = restTemplate.getForEntity(followerurl.buildAndExpand(params).toUri(), String.class).getBody();
		
		Type followerListType = new TypeToken<FollowerInfo>() { }.getType(); 
		followerInfoList = gson.fromJson(result, followerListType);
		if (followerInfoList != null && followerInfoList.getFollowers_url() != null) {

			Map<String, List<FollowerInfo>> followerMap = getUserFollowerFollower(followerInfoList.getFollowers_url());
			if (followerMap != null && !followerMap.isEmpty()) {
				followerMap2.put(followerInfoList.getLogin(), followerMap);

			}
			
			followerJson = gson.toJson(followerMap2);

		}
		return followerJson;
	}
	
	
	private Map<String, List<FollowerInfo>> getUserFollowerFollower(String url) {

		Map<String, List<FollowerInfo>> followerMap = new HashMap<String, List<FollowerInfo>>();
		List<FollowerInfo> userFollowerList = getUserFollowers(url);
		if (userFollowerList != null && !userFollowerList.isEmpty()) {
			for (FollowerInfo f1 : userFollowerList) {
				if (f1.getFollowers_url() != null) {
					followerMap.put(f1.getLogin(), subFollowers(f1.getFollowers_url()));

				}
			}

		}

		return followerMap;

	}
	
	private List<FollowerInfo> getUserFollowers(String url){ 
		
		String followerInfoResult = restTemplate.getForEntity(getFullurl(url), String.class).getBody();
		Type listType = new TypeToken<List<FollowerInfo>>() {
		}.getType();
		return  gson.fromJson(followerInfoResult, listType);
		
	}
	private List<FollowerInfo> subFollowers(String FollowersUrl){
			return getUserFollowers(FollowersUrl);
		
	}
	
	private URI getFullurl(String url) {
		
		UriComponentsBuilder completeUrlBuilder = UriComponentsBuilder.fromUriString(url)
		      
		        .queryParam("per_page", perPage)
		        .queryParam("page", pageNumber);
		
		return completeUrlBuilder.buildAndExpand().toUri();
		
	}

}
