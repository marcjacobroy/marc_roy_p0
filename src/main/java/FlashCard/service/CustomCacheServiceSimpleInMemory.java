package FlashCard.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CustomCacheServiceSimpleInMemory <T> implements CustomCacheService<T> {
	
	private Set<T> cache = new HashSet<T>();
	
	public CustomCacheServiceSimpleInMemory(Set<T> cache) {
		super();
		this.cache = cache;
	}

	public CustomCacheServiceSimpleInMemory() {
		super();
	}

	public void setCache(Set<T> cache) {
		this.cache = cache;
	}

	@Override
	public void addToCache(T obj) {
		this.cache.add(obj);
	}

	@Override
	public void removeFromCache(T obj) {
		this.cache.remove(obj);
		
	}

	@Override
	public void emptyCache() {
		this.cache.clear();
		
	}

	@Override
	public boolean contains(T obj) {
		return this.cache.contains(obj);
	}

	@Override
	public List<T> retrieveAllItems() {
		return this.cache.stream().collect(Collectors.toList());
	}

	@Override
	public List<T> filterMatching(Predicate<T> p){
		return cache.stream().filter(p).collect(Collectors.toList());
	}
	
	@Override
	public T retrieveMatchingElt(Predicate<T> p) {
		if (this.containsMatchingElt(p)) {
			return this.filterMatching(p).get(0);
		} else {
			throw new IllegalArgumentException("No such element is present in the cache.");
		}
	}
	
	@Override
	public boolean containsMatchingElt(Predicate<T> p) {
		return cache.stream().filter(p).findFirst().isPresent();
	}
	
}

