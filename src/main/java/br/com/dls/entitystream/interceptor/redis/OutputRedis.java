package br.com.dls.entitystream.interceptor.redis;

import br.com.dls.entitystream.writer.OutputEntity;

import java.util.List;

public class OutputRedis extends OutputEntity {
    private String nameDb;
    private List<String> data;
}
