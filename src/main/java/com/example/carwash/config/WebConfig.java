package com.example.carwash.config;

import com.example.carwash.interceptor.BannedUserInterceptor;
import com.example.carwash.interceptor.MaintenanceInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final BannedUserInterceptor bannedUserInterceptor;
    private final LocaleChangeInterceptor localeChangeInterceptor;
    private final MaintenanceInterceptor maintenanceInterceptor;

    @Autowired
    public WebConfig(BannedUserInterceptor bannedUserInterceptor, LocaleChangeInterceptor localeChangeInterceptor, MaintenanceInterceptor maintenanceInterceptor) {
        this.bannedUserInterceptor = bannedUserInterceptor;
        this.localeChangeInterceptor = localeChangeInterceptor;
        this.maintenanceInterceptor = maintenanceInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(bannedUserInterceptor);
        registry.addInterceptor(localeChangeInterceptor);
        registry.addInterceptor(maintenanceInterceptor);
    }
}
