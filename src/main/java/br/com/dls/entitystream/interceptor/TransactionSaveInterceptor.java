package br.com.dls.entitystream.interceptor;

import br.com.dls.entitystream.writer.DataWriter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;

@Aspect
@Configuration
public interface TransactionSaveInterceptor {

    @AfterReturning(value="cutPointDataLayerExecute", returning = "responseObject")
     void interceptAfterSave(JoinPoint joinPoint,Object responseObject);

     void cutPointDataLayerExecute();
}
