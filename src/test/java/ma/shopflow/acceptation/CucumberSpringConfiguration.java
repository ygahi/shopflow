package ma.shopflow.acceptation;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Import;

import io.cucumber.spring.CucumberContextConfiguration;

import ma.shopflow.TestcontainersConfiguration;

/**
 * Démarre ShopFlow une seule fois pour tous les scénarios, sur un port libre, avec PostgreSQL.
 */
@CucumberContextConfiguration
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Import(TestcontainersConfiguration.class)
public class CucumberSpringConfiguration {
}
