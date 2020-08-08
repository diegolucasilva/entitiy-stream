package br.com.dls.entitystream.interceptor.redis;

import br.com.dls.entitystream.writer.OutputEntity;
import br.com.dls.entitystream.interceptor.TransactionSaveInterceptor;
import br.com.dls.entitystream.serializer.EntitySerializer;
import br.com.dls.entitystream.writer.DataWriter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Pointcut;

import java.util.ArrayList;
import java.util.List;


class AspectRedisOnSave implements TransactionSaveInterceptor {

    private final DataWriter dataWriter;

    private final EntitySerializer entitySerializer;


    AspectRedisOnSave(DataWriter dataWriter, EntitySerializer entitySerializer) {
        this.dataWriter = dataWriter;
        this.entitySerializer = entitySerializer;
    }


    public void interceptAfterSave(JoinPoint joinPoint, Object responseObject) {
        List<Object> objects = new ArrayList<>();
        entitySerializer.extractNestedEntitiesfromObjectToList(responseObject,objects);

        OutputEntity outputEntity = entitySerializer.serialize(objects);
        dataWriter.writeOutPut(outputEntity);

    }

    @Pointcut("path ")
    public void cutPointDataLayerExecute() {}
}
