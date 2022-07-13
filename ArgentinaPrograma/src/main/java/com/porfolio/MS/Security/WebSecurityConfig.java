package com.porfolio.MS.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author MANUEL SAPONARO
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {



    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .requiresChannel()
                .requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null)
                .requiresSecure()
                .and()
                .csrf().disable()//.cors().disable()//desabilita el Error 403 cuando no se usa el navegador
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                //autorizacion
                .authorizeRequests()

                    .antMatchers("/api/admin").hasRole("ADMIN")
                    .antMatchers("/api/user").authenticated() //pagina resguardada
                    //.antMatchers(HttpMethod.GET, "/**").permitAll() //permite acceder al porfolio personal
                    //.antMatchers("/api/auth/**").permitAll()//permite acceder a la autentificacion
                    .antMatchers("/registro","/**","/image/**").permitAll()
                    .anyRequest().authenticated()//
                    //.anyRequest().permitAll()
                .and()
                    .httpBasic()

                //autentificacion
                .and()
                    .formLogin()
                    //.loginPage("/account/login")
                    .usernameParameter("email") //define el campo email como username
                    //.defaultSuccessUrl("/") //redireccion al autorizar
                    .failureUrl("/login?error=true")
                    .permitAll()
                .and()
                    .logout()
                    //.logoutSuccessUrl("/") //redireccion al cerrar sesion
                    .invalidateHttpSession(true)
                    .permitAll();


    }

/*
    //CREA DOS USUARIOS INICIALES
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails manuel = User.builder().username("ms@gmail.com").password(passwordEncoder()
                .encode("user")).roles("USER").build();
        UserDetails admin = User.builder().username("ma@gmail.com").password(passwordEncoder()
                .encode("admin")).roles("ADMIN").build();
        return new InMemoryUserDetailsManager(manuel, admin);
    }
*/
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

}

