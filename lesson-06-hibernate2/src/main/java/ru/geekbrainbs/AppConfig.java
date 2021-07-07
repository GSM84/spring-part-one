package ru.geekbrainbs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.geekbrainbs.persist.BuyerDAO;
import ru.geekbrainbs.persist.ProductDAO;
import ru.geekbrainbs.persist.Service;
import ru.geekbrainbs.persist.SessionProvider;

@Configuration
public class AppConfig {

    @Bean
    public SessionProvider sessionProvider(){return new SessionProvider();}

    @Bean
    public BuyerDAO buyerDAO() {return new BuyerDAO(sessionProvider());}

    @Bean
    public ProductDAO productDAO() {return new ProductDAO(sessionProvider());}

    @Bean
    public Service service(){return new Service(sessionProvider());}


}


