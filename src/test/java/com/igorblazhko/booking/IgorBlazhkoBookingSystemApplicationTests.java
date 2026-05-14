package com.igorblazhko.booking;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=PostgreSQL",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "igorblazhko.jwt.secret=change-this-secret-key-to-at-least-32-characters"
})
class IgorBlazhkoBookingSystemApplicationTests {

    @Test
    void contextLoads() {
    }
}