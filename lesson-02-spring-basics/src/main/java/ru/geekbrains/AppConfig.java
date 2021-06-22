package ru.geekbrains;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.geekbrains.product.ProductRespository;

@Configuration
public class AppConfig {

    @Bean
    public ProductRespository productRespository(){
        return new ProductRespository();
    }

    @Bean
    @Scope("prototype")
    public Cart cart(){
        return new Cart();
    }
}
