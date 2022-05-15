package com.abchau.hdddeda.subscribe.infrastructure.persistence;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class InMemoryDataStore {
	
    private Map<String, Map> stores;
	
	public InMemoryDataStore() {
		this.stores = new ConcurrentHashMap<>();
	}	

	public Mono<Object> findById(final String table, final Object key) {
		stores.putIfAbsent(table, new ConcurrentHashMap<>());

		return Mono.just(stores.get(table).get(key));
	}

	public Flux<Object> findAll(final String table) {
		stores.putIfAbsent(table, new ConcurrentHashMap<>());
		
		return Flux.empty();
	}

	public Mono<Object> save(final String table, final Object key, final Object value) {
		stores.putIfAbsent(table, new ConcurrentHashMap<>());
		
		return Mono.just(stores.get(table).put(key, value));
	}

}
