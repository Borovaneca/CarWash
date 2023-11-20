package com.example.carwash.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
@Slf4j
public class RegisterAdmin {



    @Pointcut("execution(* com.example.carwash.aop.initDB.StartSeedDB.* (..))")
    void adminRegister(){}

    @Before("adminRegister()")
    public void alertForInitializing() {
        log.info("PLEASE CONFIGURE ADMIN ACCOUNT IN THE YAML!");
    }
}
