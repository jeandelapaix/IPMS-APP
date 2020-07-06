package rw.imps;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import rw.imps.domain.Owner;
import rw.imps.domain.Role;
import rw.imps.domain.User;
import rw.imps.domain.UserRole;
import rw.imps.security.UserDTO;
import rw.imps.service.DailyOperationTrackService;
import rw.imps.service.JwtUserDetailsService;
import rw.imps.service.UserService;
import springfox.documentation.builders.*;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.*;

@SpringBootApplication
@EnableSwagger2
@PropertySource(value = "classpath:application.properties")
public class ImpsApplication extends WebMvcConfigurerAdapter {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";

    @Autowired
    private DailyOperationTrackService dailyOperationTrackService;

    @Autowired
    private JwtUserDetailsService userService;

    @Autowired
    private UserService userService2;

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

    @PostConstruct
    public void savingUser() {
        try {
            if (userService2.findAll().size() == 0) {
                UserDTO user = new UserDTO();
                user.setPassword("Patrick123!");
                user.setUsername("admin");
                user.setFullName("Patrick Nt");
                user.setUserType("admin");
                user.setEmail("patrick@gmail.com");
                user.setPhone("0788894545");
                Role role = new Role();
                role.setName("admin");
                role.setDescription("having all access");

                User savedUser = userService.save(user);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();

        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }


}
