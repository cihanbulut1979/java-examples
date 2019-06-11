package com.java.kafka.java.client.custom;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;

public class CustomObjectDeserializer implements Deserializer<CustomObject> {

	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {

	}

	@Override
	public CustomObject deserialize(String topic, byte[] data) {
		return (CustomObject)SerializationUtils.deserialize(data);
	}

	@Override
	public void close() {

	}

}
