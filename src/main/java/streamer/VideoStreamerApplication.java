package streamer;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class VideoStreamerApplication implements WebApplicationInitializer {

    @Override
    public void onStartup( ServletContext container ) throws ServletException {
        AnnotationConfigWebApplicationContext rootContext = createRootContext();
        addContextLifecycleManagerListener( rootContext, container );

        AnnotationConfigWebApplicationContext dispatcherContext = createDispatcherContext();
        ServletRegistration.Dynamic dispatcher = registerDispatcherServlet( dispatcherContext, container );

        setMultipartConfig( dispatcher );
    }

    private AnnotationConfigWebApplicationContext createRootContext() {
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        // rootContext.register();
        return rootContext;
    }

    private void addContextLifecycleManagerListener( WebApplicationContext context, ServletContext container ) {
        container.addListener( new ContextLoaderListener( context ) );
    }

    private AnnotationConfigWebApplicationContext createDispatcherContext() {
        AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
        dispatcherContext.register(
            WebConfig.class
        );
        return dispatcherContext;
    }

    private ServletRegistration.Dynamic registerDispatcherServlet( AnnotationConfigWebApplicationContext dispatcherContext, ServletContext container ) {
        ServletRegistration.Dynamic dispatcher = container.addServlet( "dispatcher", new DispatcherServlet( dispatcherContext ) );
        dispatcher.setLoadOnStartup( 1 );
        dispatcher.addMapping( "/" );
        dispatcher.setInitParameter( "dispatchOptionsRequest", "true" );
        return dispatcher;
    }

    private void setMultipartConfig( ServletRegistration.Dynamic dispatcher ) {
        // TODO: When implementing multipart, verify these settings
        dispatcher.setMultipartConfig( new MultipartConfigElement( "/tmp", 1024 * 1024 * 5, 1024 * 1024 * 5 * 5, 1024 * 1024 ) );
    }

}
