package ru.kc4kt4.resolver;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author vasilevsky.ii on 06.02.2019
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureEmbeddedDatabase
@ActiveProfiles("test")
public abstract class AbstractTest {
}
