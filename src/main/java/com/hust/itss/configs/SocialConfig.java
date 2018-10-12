package com.hust.itss.configs;

import com.hust.itss.repositories.social.CustomSocialUsersConnectionRepository;
import com.hust.itss.repositories.social.SocialUserConnectionRepository;
import com.hust.itss.utils.social.ConnectionSignUpImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.*;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;

@Configuration
@EnableSocial
public class SocialConfig implements SocialConfigurer {

    @Autowired
    private SocialUserConnectionRepository socialUserConnectionRepository;

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment){
        // Facebook configuration
        String fbClientId = environment.getProperty("spring.social.facebook.clientId");
        String fbClientSecret = environment.getProperty("spring.social.facebook.clientSecret");

        FacebookConnectionFactory facebookConnectionFactory = new FacebookConnectionFactory(fbClientId, fbClientSecret);
        facebookConnectionFactory.setScope(environment.getProperty("spring.social.facebook.scope"));

        connectionFactoryConfigurer.addConnectionFactory(facebookConnectionFactory);
    }

    // UserIdSource determines the userId
    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        CustomSocialUsersConnectionRepository usersConnectionRepository = new CustomSocialUsersConnectionRepository(socialUserConnectionRepository, connectionFactoryLocator);

        ConnectionSignUp connectionSignUp = new ConnectionSignUpImpl();
        usersConnectionRepository.setConnectionSignUp(connectionSignUp);

        return usersConnectionRepository;
    }

    @Bean
    public ConnectController connectController(ConnectionFactoryLocator connectionFactoryLocator, ConnectionRepository connectionRepository){
        return new ConnectController(connectionFactoryLocator, connectionRepository);
    }
}
