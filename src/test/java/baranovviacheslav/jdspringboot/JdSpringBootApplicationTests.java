package baranovviacheslav.jdspringboot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JdSpringBootApplicationTests {
    @Autowired
    TestRestTemplate restTemplate;

    private static final GenericContainer<?> devApp = new GenericContainer<>("devapp")
            .withExposedPorts(8080);
    private static final GenericContainer<?> prodApp = new GenericContainer<>("prodapp")
            .withExposedPorts(8081);

    @BeforeAll
    public static void setUp() {
        devApp.start();
        prodApp.start();
    }

    @Test
    void testDevApp() {
        //arrange
        String expected = "Current profile is dev";

        //act
        ResponseEntity<String> entityDevApp = restTemplate.getForEntity("http://localhost:" + devApp.getMappedPort(8080) + "/profile", String.class);
        String result = entityDevApp.getBody();

        //assert
        Assertions.assertEquals(expected, result);
    }

    @Test
    void testProdApp() {
        //arrange
        String expected = "Current profile is production";

        //act
        ResponseEntity<String> entityProdApp = restTemplate.getForEntity("http://localhost:" + prodApp.getMappedPort(8081) + "/profile", String.class);
        String result = entityProdApp.getBody();

        //assert
        Assertions.assertEquals(expected, result);
    }
}
