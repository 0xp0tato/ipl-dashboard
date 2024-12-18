package com.example.ninadsajwan.ipl_dashboard.data;

import com.example.ninadsajwan.ipl_dashboard.model.Match;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class BatchConfig {

    private final String[] FIELD_NAMES = new String[] { "id", "season", "city", "date", "match_type", "player_of_match",
            "venue", "team1", "team2", "toss_winner", "toss_decision", "winner", "result", "result_margin",
            "target_runs", "target_overs", "super_over", "method", "umpire1", "umpire2" };

    @Bean
    public Job importMatches(JobRepository jobRepository,
            JobCompletionNotificationListener listener,
            Step steps) {
        return new JobBuilder("job", jobRepository)
                .listener(listener)
                .start(steps)
                .build();
    }

    @Bean
    public Step steps(
            JobRepository jobRepository,
            DataSourceTransactionManager transactionManager,
            ItemReader<MatchInput> reader,
            ItemProcessor<MatchInput, Match> processor,
            ItemWriter<Match> writer) {
        return new StepBuilder("jobStep", jobRepository)
                .<MatchInput, Match>chunk(5, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();

    }

    // reader
    @Bean
    public FlatFileItemReader<MatchInput> reader() {
        return new FlatFileItemReaderBuilder<MatchInput>()
                .name("itemReader")
                .resource(new ClassPathResource("matches.csv"))
                .delimited()
                .names(FIELD_NAMES)
                .targetType(MatchInput.class)
                .build();
    }

    // processor

    @Bean
    public ItemProcessor<MatchInput, Match> itemProcessor() {
        return new MatchDataProcessor();
    }

    // writer

    @Bean
    public ItemWriter<Match> itemWriter(DataSource dataSource) {

        return new JdbcBatchItemWriterBuilder<Match>()
                .sql("INSERT INTO match (id, city, date, player_of_match, venue, team1, team2, toss_winner, toss_decision, match_winner, result, result_margin, umpire1, umpire2) "
                        + " VALUES (:id, :city, :date, :playerOfMatch, :venue, :team1, :team2, :tossWinner, :tossDecision, :matchWinner, :result, :resultMargin, :umpire1, :umpire2)")
                .dataSource(dataSource)
                .beanMapped()
                .build();

    }

    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
