package aptech.fpt.spring;

import aptech.fpt.spring.filehandle.UploadController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.apache.coyote.http11.AbstractHttp11Protocol;


import java.io.File;
import java.util.Locale;

@SpringBootApplication
public class SpringDemoApplication implements WebMvcConfigurer {

    private int maxUploadSizeInMb = 10 * 1024 * 1024; // 10 MB

    public static void main(String[] args) {
        new File(UploadController.UPLOADED_FOLDER).mkdir();
        SpringApplication.run(SpringDemoApplication.class, args);
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.US);
        return sessionLocaleResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci
                = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

//    //Tomcat large file upload connection reset
//    //http://www.mkyong.com/spring/spring-file-upload-and-connection-reset-issue/
//    @Bean
//    public TomcatServletWebServerFactory tomcatEmbedded() {
//
//        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
//
//        tomcat.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> {
//            if ((connector.getProtocolHandler() instanceof AbstractHttp11Protocol<?>)) {
//                //-1 means unlimited
//                ((AbstractHttp11Protocol<?>) connector.getProtocolHandler()).setMaxSwallowSize(-1);
//            }
//        });
//
//        return tomcat;
//
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
