package br.com.dls.entitystream.serializer.jsonrdb;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Field;

@Slf4j
@Component
public class SerializerForeignKey extends JsonSerializer<Object> {

    @Override
    public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        Field fields[] = o.getClass().getDeclaredFields();

        for(Field field: fields){
            if(field.getAnnotation(Id.class) != null){
                try{
                    field.setAccessible(true);
                    Long property = (Long) field.get(o);
                    jsonGenerator.writeString(property.toString());
                }catch (IllegalAccessException e){
                    log.error("Serializer foreign key failed", e);
                }
            }
        }
    }
}
