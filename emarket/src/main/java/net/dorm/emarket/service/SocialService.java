package net.dorm.emarket.service;

import net.dorm.emarket.model.SocialAccount;

public interface SocialService {

	String getAuthorizeUrl();

	SocialAccount getSocialAccount(String authToken);
}
