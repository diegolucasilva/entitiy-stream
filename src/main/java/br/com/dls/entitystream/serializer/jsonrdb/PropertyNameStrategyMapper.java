package br.com.dls.entitystream.serializer.jsonrdb;

import br.com.dls.entitystream.serializer.jsonrdb.annotations.Column;
import br.com.dls.entitystream.serializer.jsonrdb.annotations.JoinColumn;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PropertyNameStrategyMapper  extends PropertyNamingStrategy {

    public String nameForGetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
        Column column = method.getAllAnnotations().get(Column.class);
        JoinColumn joinColumn = method.getAllAnnotations().get(JoinColumn.class);

        if(column != null){
            return column.name();
        }else if(joinColumn != null){
            return joinColumn.name() + "_FK";
        }
        return defaultName;
    }

}
