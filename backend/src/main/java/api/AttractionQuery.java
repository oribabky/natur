package api;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * this class is used to build attraction queries to communicate with the designated MySql database
 * @author tobias
 *
 */
public class AttractionQuery {
	public static final String ATTRACTION_WATERFALL = "Waterfall";
	public static final String ATTRACTION_CANYON = "Canyon";
	public static final String[] AVAILABLE_ATTRACTIONS = {ATTRACTION_WATERFALL, ATTRACTION_CANYON};
	
	// the following constant is used in the controller to give default values for the query, which is to show all attractions
	static final String DEFAULT_FILTERS = ATTRACTION_WATERFALL + "," + ATTRACTION_CANYON; 
	
	List<String> attractionsToShow = new ArrayList<>();
	
	// this constructor takes no arguments, in which case all type of attractions are shown
	public AttractionQuery () {
		for (String attraction : AVAILABLE_ATTRACTIONS) {
			attractionsToShow.add(attraction);
		}
	}
	
	// this constructor takes an input on which type of attractions to show
	public AttractionQuery (Set<String> attractionTypes) {

		// make sure any invalid attraction names provided are removed
		attractionTypes = removeInvalidAttractionNames(attractionTypes);

		// see if no valid attractions were provided; in which case show all attractions
		if (attractionTypes.isEmpty()) {
			for (String attraction : AVAILABLE_ATTRACTIONS) {
				attractionsToShow.add(attraction);
			}
		}
		else {
			for (String attraction : attractionTypes) {
				attractionsToShow.add(attraction);
			}
		}
	}
	
	// removes any attractions provided that are not valid attraction names
	public static Set<String> removeInvalidAttractionNames (Set<String> attractionTypes) {
		Set<String> res = new HashSet<>();
		
		for (String attraction : attractionTypes) {
			// check if the provided attraction has a valid name
			for (String validAttraction : AttractionQuery.AVAILABLE_ATTRACTIONS) {
				if (attraction.equals(validAttraction)) {
					res.add(attraction);
					break;
					}
				}
		}
		return res;
	}	
	
	// builds a SQL query string from the options set in this class
	public String buildSelectAttractionQuery () {
		String res = "SELECT * FROM " + DatabaseConnector.DATABASE_NAME + "." + 
				DatabaseConnector.ATTRACTION_TABLE + " WHERE " + 
				DatabaseConnector.ATTRACTION_NAME_COLUMN + "='";
		
		// go through the attractions in the filter and add to the query
		boolean firstAttraction = true;
		for (String attraction : attractionsToShow) {
			
			// make sure the first attraction is added without "OR.." in front of it
			if (firstAttraction) {
				res += attraction + "' ";
				firstAttraction = false;
			}
			else {
				res += "OR " + DatabaseConnector.ATTRACTION_NAME_COLUMN + "='" + attraction + "' ";
			}
			
		}
		
		return res;
	}
}
	


