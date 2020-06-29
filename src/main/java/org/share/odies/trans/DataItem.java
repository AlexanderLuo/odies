package org.share.odies.trans;


import java.util.Map.Entry;



public class DataItem {

	private final String key;

	private final byte[] data;

	public DataItem(String key, byte[] data) {
		this.key = key;
		this.data = data;
	}

	public DataItem(Entry<byte[],byte[]> entry){
		this.key = new String(entry.getKey());
		this.data = entry.getValue();
	}

	public String getKey() {
		return key;
	}

	public byte[] getData() {
		return data;
	}



}
