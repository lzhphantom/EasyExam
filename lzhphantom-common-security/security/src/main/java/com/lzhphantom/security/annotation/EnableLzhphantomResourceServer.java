package com.lzhphantom.security.annotation;

import com.lzhphantom.security.component.LzhphantomResourceServerAutoConfiguration;
import com.lzhphantom.security.component.LzhphantomResourceServerConfiguration;
import com.lzhphantom.security.feign.LzhphantomFeignClientConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import java.lang.annotation.*;

@Documented
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@EnableMethodSecurity
@Import({LzhphantomResourceServerAutoConfiguration.class, LzhphantomResourceServerConfiguration.class,
        LzhphantomFeignClientConfiguration.class})
public @interface EnableLzhphantomResourceServer {
}
