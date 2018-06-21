package com.cardprocessor.cardprocessorbatchproject.configuration;


import com.cardprocessor.cardprocessorbatchproject.domain.MerchantFieldSetMapper;
import com.cardprocessor.cardprocessorbatchproject.domain.MerchantTransaction;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
@EnableBatchProcessing
public class JobConfiguration {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step step1() {
        return stepBuilderFactory.
                get("step1").
                <MerchantTransaction, MerchantTransaction>chunk(10).reader(merchantTransactionItemReader()).writer(merchantTransactionItemWriter()).taskExecutor(new SimpleAsyncTaskExecutor()).build();
    }

    @Bean
    public   FlatFileItemReader<MerchantTransaction> merchantTransactionItemReader() {
        FlatFileItemReader<MerchantTransaction> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("/data/Merchant1.csv"));
        DefaultLineMapper<MerchantTransaction> merchantLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(new String[]{"transactionId", "amount", "currencyISO", "transactionDate", "customerId"});
        merchantLineMapper.setLineTokenizer(tokenizer);
        merchantLineMapper.setFieldSetMapper(new MerchantFieldSetMapper());
        merchantLineMapper.afterPropertiesSet();
        reader.setLineMapper(merchantLineMapper);
        return reader;
    }

    @Bean
    public ItemWriter<MerchantTransaction> merchantTransactionItemWriter() {
        return items ->
        {
            for (MerchantTransaction item : items) {
                System.out.println(item.toString());
            }
        };
    }

    @Bean
    public Job cardProcessorJob() {

        return jobBuilderFactory.get("cardProcessorJob").start(step1()).build();
    }
}