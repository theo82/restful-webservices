package theo.restful.webservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import theo.restful.webservices.security.AppProperties;

@SpringBootApplication
public class RestfulWebservicesApplication  extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(RestfulWebservicesApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SpringApplicationContext springApplicationContext()
    {
        return new SpringApplicationContext();
    }

    @Bean(name = "AppProperties")
    public AppProperties appProperties(){
        return new AppProperties();
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(RestfulWebservicesApplication.class);
    }
}
