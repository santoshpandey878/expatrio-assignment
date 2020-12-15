package com.expatrio.usermanagement;

import org.junit.ClassRule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;

@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
public class AbstractIntegrationTest {

	@ClassRule
	public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>().withPassword("docker")
	.withUsername("postgres").withInitScript("schema.sql").withDatabaseName("postgres");

	@Value("${local.server.port}") 
	protected int portNumber;

	public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

		@Override
		public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
			TestPropertyValues values = TestPropertyValues.of(
					"spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
					"spring.datasource.password=" + postgreSQLContainer.getPassword(),
					"spring.datasource.username=" + postgreSQLContainer.getUsername()
					);
			values.applyTo(configurableApplicationContext);
		}
	}
}