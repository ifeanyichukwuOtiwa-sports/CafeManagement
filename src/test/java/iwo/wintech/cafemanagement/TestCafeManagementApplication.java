package iwo.wintech.cafemanagement;

import org.springframework.boot.SpringApplication;

public class TestCafeManagementApplication {

    public static void main(String[] args) {
        SpringApplication.from(CafeManagementApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
