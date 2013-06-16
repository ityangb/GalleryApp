/**
 *
 * @author sleepygarden
 *
 */
package edu.coppola.galleryapp;

import java.util.HashMap;
import java.util.Map;

import android.graphics.drawable.Drawable;

public class ImageCache {
	
	static Map<String, Drawable> cache;
	
	public static void initCache() {
		cache = new HashMap<String, Drawable>();
	}
	
	public static Drawable getImage(String imgName) {
		if (cache.containsKey(imgName)) {
			return cache.get(imgName);
		}
		return null;
	}
	
	public static void putImage(String imgName, Drawable img) {
		if (!cache.containsKey(imgName)){
			cache.put(imgName, img);
		}
	}
	
	public static boolean hasImage(String imgName) {
		return cache.containsKey(imgName);
	}
	
	public static int size() {
		return cache.size();
	}
	
	public static Iterable<Drawable> getIterable() {
		return cache.values();
	}
	
	

}
