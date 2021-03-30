package com.pluralsight.conferencedemo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class PersistenceConfiguration {
    /*@Value("${DB_URL}")
    private String dataSrcUrl;
    @Value("${DB_USERNAME}")
    private String dbUsername;
    @Value("${DB_PASSWORD}")
    private String dbPassword;*/

    /* Any methods in here can rtn bean defs that get stored in the Spring contxt */

    //How2 override data src bean provided by Spring from the Spring JPA starter
    //This hard coded Bean and the localhost db won't work when deploying to the cloud. The localhost db doesn't exist
    // in the cloud.
    @Bean
    public DataSource dataSource() {
        //when rtns the data src obj, spring attempts to find one already in the Spring context.
        //if one already exists in the Spring context, Spring will replace the pre-existing data src w/ this 1

        //Basic data src bldr
        DataSourceBuilder builder = DataSourceBuilder.create();
        builder.url("jdbc:postgresql://localhost:5432/conference_app");
        builder.username("postgres");
        builder.password("Welcome");

        //These didn't work when ran Jar file from cmd line
        /*builder.url(dataSrcUrl);
        builder.username(dbUsername);
        builder.password(dbPassword);*/
        System.out.println("Custom datasrc bean has been init'd and set. ");
        return builder.build();
    }
}
