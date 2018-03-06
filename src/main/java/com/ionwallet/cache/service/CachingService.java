package com.ionwallet.cache.service;

import com.ionwallet.cache.container.LRUCache;
import com.ionwallet.expensemgrutility.common.dtos.TokenDTO;

/**
 * @author sharath.m
 *
 */
public interface CachingService {

	public static LRUCache<String ,TokenDTO> tokenCache= new LRUCache<>(1000);
	
}
