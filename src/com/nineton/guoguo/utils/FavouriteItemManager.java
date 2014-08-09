package com.nineton.guoguo.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.nineton.guoguo.entity.Item;


import android.content.Context;
import android.util.Log;

public class FavouriteItemManager {
	private static Map<Long, Item> favouriteItemIds;
	private static String TAG = "FavouriteItemIdManager";
	private static File file;

	public static void init(Context context) {
		FavouriteItemManager.file = new File(context.getFilesDir().getPath()+"/favourite_ids");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				Log.e(TAG,"creat file failed");
				e.printStackTrace();
			}
			favouriteItemIds = new HashMap<Long, Item>();
			save();
		}else {
			try {
				ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
				favouriteItemIds = (Map<Long, Item>) inputStream.readObject();
				inputStream.close();
			} catch (Exception e) {
				Log.e(TAG,"init favouriteItemIds failed");
				e.printStackTrace();
			}
		}
	}

	public static void addItem(Item item) {
		favouriteItemIds.put(item.getNumIid(),item);
	}

	public static void removeById(Long id){
		favouriteItemIds.remove(id);
	}

	public static void save(){
		ObjectOutputStream outputStream = null;
		try {
			outputStream = new ObjectOutputStream(new FileOutputStream(file));
			outputStream.writeObject(favouriteItemIds);
			outputStream.close();
		} catch (Exception e) {
			Log.e(TAG,"init favouriteItemIds failed");
			e.printStackTrace();
		}
	}
	
	public static boolean isFavouriteItem(Long id) {
		return favouriteItemIds.containsKey(id);
	}

	public static Collection<Item> getFavouriteItems() {
		return favouriteItemIds.values();
	}
}
