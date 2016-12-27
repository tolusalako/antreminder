package net.csthings.antreminder.adapters;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import net.rossillo.spring.web.mvc.CacheControlHandlerInterceptor;

/**
 * Created on: Jul 28, 2016
 * @author Toluwanimi Salako
 * Last edited: Jul 28, 2016
 * @purpose - 
 */
@Configuration
public class MVCAdapter extends WebMvcConfigurerAdapter implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CacheControlHandlerInterceptor());
    }
}
