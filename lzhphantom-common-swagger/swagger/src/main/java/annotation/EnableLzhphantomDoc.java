package annotation;

import com.lzhphantom.swagger.config.SwaggerAutoConfiguration;
import com.lzhphantom.swagger.support.SwaggerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启 lzhphantom spring doc
 * @author lzhphantom
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableConfigurationProperties(SwaggerProperties.class)
@Import({SwaggerAutoConfiguration.class})
public @interface EnableLzhphantomDoc {
}
