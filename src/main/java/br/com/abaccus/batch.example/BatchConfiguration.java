package br.com.abaccus.batch.example;

import br.com.abaccus.nucleo.Retorno;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    private static final Logger log = LoggerFactory.getLogger(BatchConfiguration.class);

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public ItemReader<Map<Object, Object>> reader() {
        return new ItemReader<Map<Object, Object>>() {
            @Override
            public Map<Object, Object> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                Map<Object, Object> contexto = new HashMap<>();
                contexto.put("da", 11);
                return contexto;
            }
        };
    }

    @Bean
    public ExemploItemProcessor processor() {
        return new ExemploItemProcessor();
    }

    @Bean
    public ItemWriter<Retorno> writer() {
        return new ItemWriter<Retorno>() {
            @Override
            public void write(List<? extends Retorno> items) throws Exception {
                items.forEach(v -> log.info(v.toString()));
            }
        };
    }

    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(ItemWriter<Retorno> writer) {
        return stepBuilderFactory.get("step1")
                .<Map<Object, Object>, Retorno>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }
}
