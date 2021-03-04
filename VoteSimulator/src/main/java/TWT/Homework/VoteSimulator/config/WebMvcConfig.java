package TWT.Homework.VoteSimulator.config;

import TWT.Homework.VoteSimulator.interceptor.UserInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getUserInterceptor())
                .excludePathPatterns("/logIn")
                .excludePathPatterns("/getUser")
                .excludePathPatterns("/addUser")
                .excludePathPatterns("/findCode")
                .excludePathPatterns("/userName")

                .excludePathPatterns("/result")
                .excludePathPatterns("/vote")
                .excludePathPatterns("/choice")
                .excludePathPatterns("/question")

                .excludePathPatterns("/testConnection");
    }

    @Bean
    public UserInterceptor getUserInterceptor(){
        return new UserInterceptor();
    }
}
