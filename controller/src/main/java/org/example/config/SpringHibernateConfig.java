package org.example.config;

import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
@PropertySource("classpath:db.properties")
public class SpringHibernateConfig {

  @Value("${url}")
  private String URL;
  @Value("${userdb}")
  private String USER;
  @Value("${password}")
  private String PASSWORD;
  @Value("${driver}")
  private String DRIVER_NAME;


  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(DRIVER_NAME);
    dataSource.setUrl(URL);
    dataSource.setUsername(USER);
    dataSource.setPassword(PASSWORD);
    return dataSource;
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean factory() {
    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
    factory.setDataSource(dataSource());
    factory.setPersistenceUnitName("jpa-unit");
    factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    factory.setPackagesToScan("org.example");
    factory.setJpaProperties(jpaProperties());
    return factory;
  }

  @Bean
  public JpaTransactionManager jpaTransactionManager() {
    JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
    jpaTransactionManager.setEntityManagerFactory(factory().getObject());
    return jpaTransactionManager;
  }

  public Properties jpaProperties() {
    Properties properties = new Properties();
    properties.setProperty("connection.pool_size", "20");
    properties.setProperty("dialect", "org.hibernate.dialect.PostgreSQL10Dialect");
    properties.setProperty("show_sql", "true");
    properties.setProperty("current_session_context_class", "thread");
    properties.setProperty("hbm2ddl.auto", "none");
    properties.setProperty("hibernate.dbcp.initialSize", "5");
    properties.setProperty("hibernate.dbcp.maxTotal", "20");
    properties.setProperty("hibernate.dbcp.maxIdle", "10");
    properties.setProperty("hibernate.dbcp.minIdle", "5");
    properties.setProperty("hibernate.dbcp.maxWaitMillis", "-1");
    properties.setProperty("hibernate.enable_lazy_load_no_trans", "true");
    return properties;
  }
}
