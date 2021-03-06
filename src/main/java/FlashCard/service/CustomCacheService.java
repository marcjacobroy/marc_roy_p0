package FlashCard.service;

import java.util.List;
import java.util.function.Predicate;

// Structure of cache used for storing different objects 
public interface CustomCacheService <T> {

	public void addToCache(T obj);
	
	public void removeFromCache(T obj);
	
	public void emptyCache();
	
	public boolean contains(T obj);
	
	public List<T> retrieveAllItems();
	
	public List<T> filterMatching(Predicate<T> p);
	
	public T retrieveMatchingElt(Predicate<T> p);
	
	public boolean containsMatchingElt(Predicate<T> p);
	
	public List<String> toStringList();
	
}
