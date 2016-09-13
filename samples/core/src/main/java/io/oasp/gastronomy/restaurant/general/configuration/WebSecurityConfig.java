package io.oasp.gastronomy.restaurant.general.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import io.oasp.module.basic.configuration.SpringProfileConstants;

/**
 * Security configuration based on {@link WebSecurityConfigurerAdapter}. This configuration is by purpose designed most
 * simple for two channels of authentication: simple login form and rest-url. (Copied from
 * {@link io.oasp.gastronomy.restaurant.general.configuration.BaseWebSecurityConfig}
 *
 */
@Configuration
@EnableWebSecurity
@Profile(SpringProfileConstants.NOT_JUNIT)
public class WebSecurityConfig extends BaseWebSecurityConfig {

}
