package br.com.abaccus.batch.example;

import br.com.abaccus.motor.web.AbaccusMotorService;
import br.com.abaccus.nucleo.Retorno;
import io.micrometer.core.ipc.http.HttpSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class ExemploItemProcessor implements ItemProcessor<Map<Object, Object>, Retorno> {

    private static final Logger log = LoggerFactory.getLogger(ExemploItemProcessor.class);

    @Autowired
    private AbaccusMotorService abaccusMotorService;

    @Override
    public Retorno process(Map<Object, Object> request) throws Exception {
        try {
            ResponseEntity<Retorno> response = abaccusMotorService.motor("testebatch", request);
            return response.getBody();
        } catch (Throwable t) {
            throw new Exception(t);
        }
    }
}
