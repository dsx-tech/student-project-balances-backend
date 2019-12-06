package dsx.bcv.server.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ToJsonConverterService {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static String toJson(final Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }
}
