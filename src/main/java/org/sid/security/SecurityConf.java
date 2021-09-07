package org.sid.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConf extends WebSecurityConfigurerAdapter {



  /*  @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        BCryptPasswordEncoder bCryptPasswordEncoder=getBCPE();
        auth.inMemoryAuthentication().withUser("admin").password(bCryptPasswordEncoder.encode("1234")).roles("ADMIN","USER");
        auth.inMemoryAuthentication().withUser("user1").password(bCryptPasswordEncoder.encode("1234")).roles("USER");
    }
*/

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable();
        //http.formLogin();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
       // super.configure(http);   // Desactive la securite (Authetification basee sur les session) par defaut
        http.authorizeRequests().antMatchers("/categories/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/products/**").hasAuthority("USER");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilterBefore( new JWTAuthorization(), UsernamePasswordAuthenticationFilter.class);
    }

    /*@Bean
    public BCryptPasswordEncoder getBCPE(){  // crypte les mdp
        return  new BCryptPasswordEncoder();
    }*/
}
