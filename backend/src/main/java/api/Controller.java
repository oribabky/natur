package api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import models.Greeting;
import models.Waterfall;

/**
 * This class provide backend endpoints from which users can retrieve information
 * @author tobias
 *
 */
@RestController
public class Controller {
	
	DatabaseConnector db = new DatabaseConnector();
    private static final String TEMPLATE = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    
    // this is only used as a test-endpoint in the application
    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(TEMPLATE, name));
    }
    
    @RequestMapping("/waterfall")
    public Collection<Waterfall> getWaterfalls() {
    	String query = QueryBuilder.waterfallQuery();
    	
    	// perform the query
    	Collection<Waterfall> waterfalls = new ArrayList<>();
    	waterfalls = db.getWaterfallsFromDatabase(query);
    	
    	return waterfalls;
    }
}
