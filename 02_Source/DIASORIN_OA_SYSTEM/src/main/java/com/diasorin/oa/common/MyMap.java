package com.diasorin.oa.common;

public class MyMap implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Fields
	private String key;
	private String value;

	/** default constructor */
	public MyMap() {
	}

	/** full constructor */
	public MyMap(String key, String value) {
		this.key = key;
		this.value = value;
	}

	// Property accessors
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
