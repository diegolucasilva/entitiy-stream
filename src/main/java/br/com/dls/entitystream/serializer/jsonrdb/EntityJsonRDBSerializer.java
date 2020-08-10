package br.com.dls.entitystream.serializer.jsonrdb;

import br.com.dls.entitystream.interceptor.redis.OutputRedis;
import br.com.dls.entitystream.serializer.EntitySerializer;

import java.util.List;


class EntityJsonRdbSerializer implements EntitySerializer {

   public OutputRedis serialize(List<Object> entities) {
        return new OutputRedis();
    }
}
