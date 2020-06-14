package ee.mrtnh.tallink_test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySources({
        @PropertySource("/properties/application.properties"),
        @PropertySource("/properties/actuator.properties"),
        @PropertySource("/properties/datasource.properties"),
        @PropertySource("/properties/h2-console.properties"),
        @PropertySource("/properties/server.properties")
})
public class PropertiesLoader {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfig() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
