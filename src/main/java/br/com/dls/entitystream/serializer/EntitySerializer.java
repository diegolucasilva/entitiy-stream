package br.com.dls.entitystream.serializer;

import br.com.dls.entitystream.writer.OutputEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface EntitySerializer {

    OutputEntity serialize(Object entity);



}
