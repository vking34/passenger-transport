package com.hust.itss.utils.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.hust.itss.constants.format.DateForm;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

public class DateDeserializer extends StdDeserializer<Date> {

    private static final Date AD = new Date(0,0,0);
    Date date;

    public DateDeserializer(){
        this(null);
    }

    public DateDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String dateString = jsonParser.getValueAsString();
        try {
            date = DateForm.SIMPLE_DATE_FORMAT.parse(dateString);
        } catch (ParseException e) {
            return AD;
        }
        return date;
    }
}
