package streamer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
@EnableWebMvc
@ComponentScan(
	useDefaultFilters = false,
	includeFilters = {
		@ComponentScan.Filter( type = FilterType.ANNOTATION, value = ControllerAdvice.class ),
		@ComponentScan.Filter( type = FilterType.ANNOTATION, value = Controller.class )
	},
	basePackages = {"streamer"}
)
public class WebConfig extends WebMvcConfigurerAdapter {

	@Override
	public void configureMessageConverters( List<HttpMessageConverter<?>> converters ) {
		converters.add( new ByteArrayHttpMessageConverter() );
		converters.add( new StringHttpMessageConverter() );
		converters.add( new FormHttpMessageConverter() );
	}

	@Override
	public void configureContentNegotiation( ContentNegotiationConfigurer configurer ) {
		super.configureContentNegotiation( configurer );
		configurer.favorPathExtension( false );
	}

	@Bean
	public MultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}
}
