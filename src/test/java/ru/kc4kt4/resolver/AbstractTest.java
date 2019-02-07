package ru.kc4kt4.resolver;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;

import java.util.Arrays;

/**
 * The type Abstract test.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureEmbeddedDatabase
@ActiveProfiles("test")
@DirtiesContext
public abstract class AbstractTest {

    /**
     * Rabbit mq generic container.
     *
     * @return the generic container
     */
    @ClassRule
    public static GenericContainer rabbitMq() {
        GenericContainer genericContainer = new GenericContainer("rabbitmq:management");
        genericContainer.setPortBindings(Arrays.asList("5672:5672", "15672:15672"));
        return genericContainer;
    }
}
