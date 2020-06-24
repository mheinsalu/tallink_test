package ee.mrtnh.tallink_test.config;

import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;

public class CustomTomcatConnectorCustomizer implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

    @Override
    public void customize(TomcatServletWebServerFactory factory) {
        TomcatConnectorCustomizer parseBodyMethodCustomizer = connector -> {
            connector.setParseBodyMethods("POST,GET,DELETE");
        };
        factory.addConnectorCustomizers(parseBodyMethodCustomizer);
    }
}
