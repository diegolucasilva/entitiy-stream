package br.com.dls.entitystream.serializer.jsonrdb;

import br.com.dls.entitystream.serializer.jsonrdb.annotations.*;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class CustomBeanSerializerModifier extends BeanSerializerModifier {

    public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
        List<BeanPropertyWriter> beanPropertyIterator = beanProperties;
        List<BeanPropertyWriter> writers = new ArrayList<>();

        beanPropertyIterator.stream().forEach(property ->{

            if(property.getAnnotation(OneToMany.class) != null){
                if(property.getAnnotation(OneToOne.class) != null || property.getAnnotation(ManyToOne.class) != null){
                    if(property.getAnnotation(JoinColumn.class) != null){
                        property.assignSerializer(new SerializerForeignKey());
                        writers.add(property);
                    }
                }else{
                    writers.add(property);
                }
            }
        });
        return writers;
    }
}
