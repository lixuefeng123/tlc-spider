package cn.com.fero.tlc.spider.annotation;

/**
 * Created by wanghongmeng on 2015/6/2.
 */

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Admin {

}