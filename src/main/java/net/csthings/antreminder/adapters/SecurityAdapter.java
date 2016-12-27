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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import net.csthings.antreminder.security.LogoutManager;

@Configuration
@EnableWebSecurity
public class SecurityAdapter extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests().antMatchers("/", "/schedule", "/login", "/logout", "/register").permitAll()
                .anyRequest().permitAll();
        httpSecurity.authorizeRequests().antMatchers("/reminders/**").authenticated();
        httpSecurity.csrf().csrfTokenRepository(csrfTokenRepository());
        httpSecurity.formLogin().loginPage("/login").permitAll().and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
                .deleteCookies(LogoutManager.SESSION_NAME).addLogoutHandler(new LogoutManager())
                .invalidateHttpSession(true);
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
        web.ignoring().antMatchers("/js/**", "/css/**", "/res/**");
    }
}
