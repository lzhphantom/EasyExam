package com.lzhphantom.job.annotation;

import com.lzhphantom.job.JobAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 激活xxl-job配置
 *
 * @author lishangbu
 * @date 2020/9/14
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({ JobAutoConfiguration.class })
public @interface EnableLzhphantomXxlJob {
}
