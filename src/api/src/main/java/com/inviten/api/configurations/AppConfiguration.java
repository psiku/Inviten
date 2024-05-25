package com.inviten.api.configurations;

import com.inviten.api.features.meetings.IMeetingRepository;
import com.inviten.api.features.meetings.MeetingRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class AppConfiguration {

    @Bean
    public IMeetingRepository getMeetingRepository() {
        return new MeetingRepository(getAmazonDynamoDBClient());
    }

    @Bean
    public DynamoDbEnhancedClient getAmazonDynamoDBClient() {
        var dynamoDbClient = DynamoDbClient.builder()
            .region(Region.EU_NORTH_1)
            .build();

        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
    }
}
