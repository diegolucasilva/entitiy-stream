package br.com.dls.entitystream.serializer;

import br.com.dls.entitystream.writer.OutputEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface EntitySerializer {

    OutputEntity serialize(List<Object> entities);

    default void extractNestedEntitiesfromObjectToList(Object entity, List<Object> entities){

    }


}
