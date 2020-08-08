package br.com.dls.entitystream.writer;

import org.springframework.stereotype.Component;

@Component
public interface DataWriter {

    public void writeOutPut(OutputEntity outputEntity);

}
