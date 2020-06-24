package ee.mrtnh.tallink_test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySources({
        @PropertySource("classpath:/properties/application.properties"),
        @PropertySource("classpath:/properties/actuator.properties"),
        @PropertySource("classpath:/properties/datasource.properties"),
        @PropertySource("classpath:/properties/h2-console.properties"),
        @PropertySource("classpath:/properties/server.properties")
})
public class PropertiesLoader {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfig() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
