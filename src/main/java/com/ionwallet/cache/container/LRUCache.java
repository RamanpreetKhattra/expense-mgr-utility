package com.ionwallet.cache.container;

import java.util.LinkedHashMap;

/**
 * @author sharath.m
 * @param <K>
 * @param <V>
 */
public class LRUCache<K,V> extends LinkedHashMap<K, V> {

	private static int capacity=100;
	private static float loadFactor=0.75f;
	private static final long serialVersionUID = 1L;

	/**
	 * Initialize a LRU cache with specified loadFactor and specified capacity
	 * @param capacity
	 * @param loadFactor
	 */
	public LRUCache(int capacity, float loadFactor){
		super(capacity, loadFactor, true);
		LRUCache.capacity = capacity;
		LRUCache.loadFactor = loadFactor;
	}
	
	/**
	 * Initialize a LRU Cache with default(0.75) loadFactor and specified capacity
	 * @param capacity
	 */
	public LRUCache(int capacity){
		super(capacity, 0.75f, true);
		LRUCache.capacity = capacity;
	}
	
	/**
	 * Initialize a LRU Cache with default(0.75) loadFactor and capacity(100)
	 * @param capacity
	 */
	public LRUCache() {
		super(capacity, 0.75f, true);
	}

	/**
	 * 
	 */

	 @Override
	 protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
		 return size() > LRUCache.capacity;
	 }



}
