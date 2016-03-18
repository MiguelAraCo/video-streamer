package streamer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

@RestController
public class TestController {
	@RequestMapping( value = "", method = RequestMethod.GET )
	public ResponseEntity<Object> getHTML(HttpServletRequest request, HttpServletResponse response) {
		return new ResponseEntity<>( "<html><head></head><body><video src=\"video\" controls></body>", HttpStatus.OK );
	}

	@RequestMapping( value =  "video", method = RequestMethod.GET )
	public ResponseEntity<Object> getVideo(HttpServletRequest request, HttpServletResponse response) {
		try {
			MultipartFileSender.fromFile( new File( "/fullmetal.mp4" ) ).with( request ).with( response ).serveResource();
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return new ResponseEntity<>( HttpStatus.OK );
	}
}
