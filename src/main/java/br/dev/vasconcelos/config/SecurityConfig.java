package br.dev.vasconcelos.config;

import br.dev.vasconcelos.security.jwt.JwtAuthFilter;
import br.dev.vasconcelos.security.jwt.JwtService;
import br.dev.vasconcelos.service.impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Autowired
    private JwtService jwtService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public OncePerRequestFilter jwtFilter() {
        return new JwtAuthFilter(jwtService, usuarioService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeRequests()

                    /** API de clientes **/
                    .antMatchers("/api/clientes/**")
                        .hasAnyRole("USER")

                    /** API de pedidos **/
                    .antMatchers("/api/pedidos/**")
                        .hasAnyRole("USER")

                    /** API de produtos **/
                    .antMatchers("/api/produtos/**")
                        .hasRole("ADMIN")

                    /** API de usuários **/
                    .antMatchers(HttpMethod.POST,"/api/usuarios/**")
                        .permitAll()

                    /** Regra para outras requisições **/
                    .anyRequest().authenticated()

                .and()
                    /** Não permite sessão e obriga a autenticar em todas as requisições */
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);

    }
}
