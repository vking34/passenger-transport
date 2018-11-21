package com.hust.itss.utils.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class ListObjectIdSerializer extends StdSerializer<List<ObjectId>> {
    // must have these 2 constructors
    public ListObjectIdSerializer(){                            // constructor 1
        this(null);
    }

    public ListObjectIdSerializer(Class<List<ObjectId>> t) {    // constructor 2
        super(t);
    }

    @Override
    public void serialize(List<ObjectId> objectIds, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeNumber(objectIds.size());
    }
}
