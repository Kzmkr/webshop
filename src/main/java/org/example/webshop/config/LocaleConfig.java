package org.example.webshop.config;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 * Configuration class for locale and internationalization settings.
 * Sets up locale resolution and allows changing the locale via a request parameter.
 */
@Configuration
public class LocaleConfig implements WebMvcConfigurer {

    /**
     * Configures a {@link LocaleResolver} that uses cookies to store the user's locale preference.
     *
     * @return a {@link CookieLocaleResolver} with the default locale set to English.
     */
    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver resolver = new CookieLocaleResolver();
        resolver.setCookieName("lang");
        resolver.setDefaultLocale(Locale.ENGLISH);
        return resolver;
    }

    /**
     * Configures a {@link LocaleChangeInterceptor} to allow changing the locale via a request parameter.
     *
     * @return a {@link LocaleChangeInterceptor} with the parameter name set to "lang".
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

    /**
     * Adds the {@link LocaleChangeInterceptor} to the application's interceptor registry.
     *
     * @param registry the {@link InterceptorRegistry} to which the interceptor is added.
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}