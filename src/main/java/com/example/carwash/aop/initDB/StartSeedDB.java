package com.example.carwash.aop.initDB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartSeedDB implements CommandLineRunner {

    private final SeedDataBase seedDataBase;

    @Autowired
    public StartSeedDB(SeedDataBase seedDataBase) {
        this.seedDataBase = seedDataBase;
    }


    @Override
    public void run(String... args) throws Exception {
        seedDataBase.registerAdmin();
    }
}
