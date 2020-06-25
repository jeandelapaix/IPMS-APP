package rw.imps;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import rw.imps.service.DailyOperationTrackService;
import springfox.documentation.builders.*;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableSwagger2
public class ImpsApplication {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";

    @Autowired
    private DailyOperationTrackService dailyOperationTrackService;

    public static void main(String[] args) {
        SpringApplication.run(ImpsApplication.class, args);
    }

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("API References for IMPS")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("rw.imps")).build()
                .securitySchemes(Arrays.asList(apiKey()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("IMPS APIs")
                .description("API references for developers")
                .termsOfServiceUrl("https://ipms.rw")
                .contact("jeandelapaixd@gmail.com").license("IMPS License")
                .licenseUrl("jeandelapaixd@gmail.com").version("1.0").build();
    }

    private ApiKey apiKey() {
        return new ApiKey("jwtToken", "Authorization", "header");
    }

    @PostConstruct
    public void openDailyTrack() {
        try {
            dailyOperationTrackService.openTodayOperationTrack();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

//    @Bean
//    public MessageSourceAccessor createMessageSourceAccessor() {
//        return new MessageSourceAccessor(messageSource());
//    }
//
//    @Bean(name = "messageSource")
//    public ReloadableResourceBundleMessageSource messageSource() {
//        ReloadableResourceBundleMessageSource resource = new ReloadableResourceBundleMessageSource();
//        resource.setBasename("classpath:messages");
//        resource.setDefaultEncoding("UTF-8");
//        return resource;
//    }
//
//    @Bean(name = "validator")
//    public LocalValidatorFactoryBean validator() {
//        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
//        bean.setValidationMessageSource(messageSource());
//        return bean;
//    }
//
//    @Bean
//    public MessageSource messageSource1() {
//        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
//        messageSource.setBasename("messages");
//        return messageSource;
//    }

}
