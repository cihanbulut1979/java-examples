package com.java.kafka.java.client.custom;

import java.util.Map;

import org.apache.kafka.common.serialization.Serializer;

public class CustomObjectSerializer implements Serializer {

	public void configure(Map map, boolean b) {

	}

	public byte[] serialize(String s, Object o) {
		return SerializationUtils.serialize(o);
	}

	public void close() {

	}

}
