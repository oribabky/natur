package api;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class provide backend endpoints from which users can retrieve information
 * @author tobias
 *
 */
@RestController
public class Controller {
	
    private static final String TEMPLATE = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    // this is only used as a test-endpoint in the application
    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(TEMPLATE, name));
    }
    
    // this endpoint is where the user retrieves attractions
    @RequestMapping("/")
    public ArrayList<NaturalAttraction> getMarkers(@RequestParam(value="attractionType", defaultValue=AttractionQuery.DEFAULT_FILTERS) 
    		String attractionType) {
    	
    	// put the query parameters in an array and make sure that it does not contain duplicates
    	String[] attractionTypes = attractionType.split(",");
    	Set<String> attractionTypesUnique = new HashSet<>();
    	for (String attraction : attractionTypes) {
    		attractionTypesUnique.add(attraction);
    	}
    	
    	// create an attraction filter
    	AttractionQuery filter = new AttractionQuery(attractionTypesUnique);
    	
    	// build a query from the given filter
    	String query = filter.buildSelectAttractionQuery();
    	
    	// perform the query
    	DatabaseConnector db = new DatabaseConnector();
    	ArrayList<NaturalAttraction> naturalAttractions = new ArrayList<>();
    	naturalAttractions = db.getAttractionsQuery(query);
    	
    	return naturalAttractions;
    }
}
