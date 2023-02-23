package com.moorabi.reelsapi.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moorabi.reelsapi.model.SignalMessage;

public class WebRTCUtil {
	private static final ObjectMapper objectMapper = new ObjectMapper();

    private WebRTCUtil() {

	}

    public static SignalMessage getObject(final String message) throws Exception {
        return objectMapper.readValue(message, SignalMessage.class);
    }

    public static String getString(final SignalMessage message) throws Exception {
        return objectMapper.writeValueAsString(message);
    }
}
