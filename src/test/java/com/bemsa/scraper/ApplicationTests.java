package com.bemsa.scraper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ApplicationTests {

    private final Application application = new Application();

    @Test
    void contextLoads() {
        Application.main(new String[]{"0"});
        Assertions.assertThat(application).isNotNull();
    }
}
