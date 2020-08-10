package br.com.dls.entitystream.serializer.jsonrdb;

import br.com.dls.entitystream.interceptor.redis.OutputRedis;
import br.com.dls.entitystream.serializer.EntitySerializer;
import br.com.dls.entitystream.serializer.jsonrdb.annotations.Column;
import br.com.dls.entitystream.serializer.jsonrdb.annotations.OneToMany;
import br.com.dls.entitystream.serializer.jsonrdb.annotations.Table;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import net.vidageek.mirror.dsl.Mirror;
import org.springframework.boot.autoconfigure.condition.ConditionalOnJava;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;
import static java.time.format.DateTimeFormatter.ofPattern;


class EntityJsonRDBSerializer implements EntitySerializer {

    private ObjectMapper mapper;

   private static final String DATE_FORMAT = "yyy-MM-dd";
    private static final String DATE_TIME_FORMAT = "yyy-MM-dd'T'HH:mm:ss";

    public OutputRedis serialize(Object object) {

        List<Object> entities = extractNestedEntitiesfromObjectToList(object, new ArrayList<>());
        mapper = configureObjectMapperSerializer();

        entities.stream().forEach(entity -> {
            final Table table = new Mirror().on(entity.getClass()).reflect().annotation(Table.class).atClass();

            try{
                if(table != null){
                    String data  = mapper.writeValueAsString(entity);
                    OutputRedis.Entity entityJson = OutputRedis.Entity.builder().name(table.name()).data(data).build();
                    System.out.println(entityJson);
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

        });


        return null;
    }



    private List<Object> extractNestedEntitiesfromObjectToList(Object entity, List<Object> entities){
        Field fields[] = entity.getClass().getDeclaredFields();

        for(Field field: fields){
            if(field.getAnnotation(OneToMany.class) != null){

                try{
                    field.setAccessible(true);
                    List<Object> nestedObjects = (List<Object>) field.get(entity);
                    if(nestedObjects != null)
                        nestedObjects.forEach(object -> extractNestedEntitiesfromObjectToList(object, entities));
                }catch (IllegalAccessException e){
                    e.printStackTrace();
                }
            }
        }
        entities.add(entity);
        return entities;
    }

    private ObjectMapper configureObjectMapperSerializer(){
        mapper = new ObjectMapper();
        SerializerFactory serializerFactory = BeanSerializerFactory.instance.withSerializerModifier(new CustomBeanSerializerModifier());
        mapper.setSerializerFactory(serializerFactory);
        mapper.setPropertyNamingStrategy(new PropertyNameStrategyMapper());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(ofPattern(DATE_FORMAT)));
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(ofPattern(DATE_TIME_FORMAT)));
        return mapper;
    }
}
