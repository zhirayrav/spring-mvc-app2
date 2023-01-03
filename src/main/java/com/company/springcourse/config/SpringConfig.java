package com.company.springcourse.config;

import java.util.Objects;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

@Configuration
@ComponentScan("com.company.springcourse")
@EnableWebMvc
@EnableJpaRepositories("com.company.springcourse.repositories")
@EnableTransactionManagement
@PropertySource("classpath:hibernate.properties")
public class SpringConfig implements WebMvcConfigurer{
	private final ApplicationContext applicationContext;
	private final Environment environment;

    @Autowired
     public SpringConfig(ApplicationContext applicationContext, Environment environment) {
		super();
		this.applicationContext = applicationContext;
		this.environment = environment;
	}

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        resolver.setCharacterEncoding("UTF-8");

        registry.viewResolver(resolver);
    }
    @Bean
    public DataSource dataSource() {
    	DriverManagerDataSource dataSource = new DriverManagerDataSource();
    	dataSource.setDriverClassName(Objects.requireNonNull(environment
    			.getRequiredProperty("hibernate.driver_class")));
    	dataSource.setUsername(environment.getRequiredProperty("hibernate.connection.username"));
    	dataSource.setUrl(environment.getRequiredProperty("hibernate.connection.url"));
    	dataSource.setPassword(environment.getRequiredProperty("hibernate.connection.password"));
		return dataSource;
    }
    @Bean
    public JdbcTemplate jdbcTemplate() {
    	return new JdbcTemplate(dataSource());
    }
    private Properties hibernateProperties() {
    	Properties properties = new Properties();
    	properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
    	properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
    	return properties;
    }
    
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    	final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    	em.setJpaProperties(hibernateProperties());
    	em.setDataSource(dataSource());
    	final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    	em.setJpaVendorAdapter(vendorAdapter);
    	em.setPackagesToScan("com.company.springcourse.models");
    		return em;
    }
    @Bean
    PlatformTransactionManager transactionManager(){
    	JpaTransactionManager transactionManager = new JpaTransactionManager();
    	transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
    	return transactionManager;
    }
}
