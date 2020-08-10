package br.com.dls.entitystream.interceptor.redis;

import br.com.dls.entitystream.writer.OutputEntity;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
@Builder
public class OutputRedis extends OutputEntity implements  Serializable{
    private final String nameDb;
    private List<String> entities;


    @Getter
    @Builder
    public static class Entity implements Serializable{
        private final String name;
        private final String data;

    }
}
