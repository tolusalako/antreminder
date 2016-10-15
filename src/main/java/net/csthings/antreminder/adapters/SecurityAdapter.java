package net.csthings.antreminder.adapters;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityAdapter extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests().antMatchers("/", "/schedule", "/login").permitAll().anyRequest().permitAll();
        httpSecurity.authorizeRequests().antMatchers("/reminders/**").authenticated();
        httpSecurity.csrf().csrfTokenRepository(csrfTokenRepository());
        // httpSecurity.exceptionHandling().accessDeniedHandler(accessDeniedhandler());
        // httpSecurity.formLogin().loginPage("/login").permitAll().and().logout().permitAll();

        // .usernameParameter("email")
        // .passwordParameter("password").permitAll().and().exceptionHandling().accessDeniedPage("/login")
    }

    private static CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setSessionAttributeName("_csrf");
        return repository;
    }

    private static AccessDeniedHandler accessDeniedhandler() {
        AccessDeniedHandlerImpl accessDeniedhandler = new AccessDeniedHandlerImpl();
        accessDeniedhandler.setErrorPage("/error");
        return accessDeniedhandler;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**", "/css/**", "/img/**");
    }
}
