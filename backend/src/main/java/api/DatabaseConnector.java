package api;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import api_util.FilesUtil;

/**
 * this class is used to connect to a MySQL database and to perform queries
 * @author tobias
 *
 */
public class DatabaseConnector {
	private String database;
	private String databaseUsername;
	private String databasePassword;
	
	private static final String DATABASE_FILENAME = "database-remote.txt";
	private static final Path DATABASE_FILE = FileSystems.getDefault().getPath(DATABASE_FILENAME);
	
	static final String DATABASE_NAME = "naturdb";
	static final String ATTRACTION_TABLE = "Attraction";
	static final String ATTRACTION_NAME_COLUMN = "AttractionType";
	static final String WATERFALL_TABLE = "Waterfall";

	public DatabaseConnector () {
		setDatabaseInformation();
	}
	
	// this method sets information on the database from a locally stored file
	private void setDatabaseInformation () {
		String[] databaseInformation = {"", "", ""};
		String line;
		
		// check that the database config file contains the correct number of lines
		if (FilesUtil.nrLinesFile(DATABASE_FILE) != databaseInformation.length) {
			throw new IllegalArgumentException(DATABASE_FILENAME + " must be three lines long!");
		}
		
		// read the database config file
		try (
				BufferedReader reader = Files.newBufferedReader(DATABASE_FILE, StandardCharsets.UTF_8);
			) {
			// read the file line for line
			int i = 0;
		    while ((line = reader.readLine()) != null) {
		    	databaseInformation[i] = line;
		    	i ++;
		    }
		} catch (FileNotFoundException e) {
			throw new IllegalStateException("Could not find the file: " + DATABASE_FILENAME + "!");
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		database = databaseInformation[0];
		databaseUsername = databaseInformation[1];
		databasePassword = databaseInformation[2];
	}
	
	
	// this method aims to get attractions from a database
	public ArrayList<NaturalAttraction> getAttractionsQuery (String query) {
		ArrayList<NaturalAttraction> naturalAttractions = new ArrayList<>();
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String dateString = dateFormat.format(date);
		
		System.out.println(dateString + " Connecting to the database...");

		try (Connection connection = DriverManager.getConnection(database, databaseUsername, databasePassword)) {
		    System.out.println(dateString + " Database connected!");
		    
		    Statement stmt = connection.createStatement();
		    ResultSet result = stmt.executeQuery(query);
		    
		    // process each query result separately
		    while (result.next()) {
		    	NaturalAttraction naturalAttraction = new NaturalAttraction(
		    			result.getString("Name"),
		    			result.getString("Country"),
		    			result.getDouble("Latitude"),
		    			result.getDouble("Longitude"),
		    			result.getString("AttractionType")
		    			);
		    	naturalAttractions.add(naturalAttraction);
		    }
		} catch (SQLException e) {
		    throw new IllegalStateException(dateString + " Cannot connect to the database!", e);
		}
		return naturalAttractions;
	}
	
	public Collection<Waterfall> getWaterfallsFromDatabase(String query) {
		Collection<Waterfall> waterfalls = new ArrayList<>();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String dateString = dateFormat.format(new Date());
		
		System.out.println(dateString + " Connecting to the database...");
		try (Connection connection = DriverManager.getConnection(database, databaseUsername, databasePassword)) {
		    System.out.println(dateString + " Database connected!");
		    
		    Statement stmt = connection.createStatement();
		    ResultSet result = stmt.executeQuery(query);
		    
		    // process each query result separately
		    while (result.next()) {
		    	Waterfall waterfall = new Waterfall(
		    			result.getString("Name"),
		    			result.getString("Country"),
		    			result.getInt("Height"),
		    			result.getDouble("Latitude"),
		    			result.getDouble("Longitude"),
		    			result.getString("ImageUrl"),
		    			result.getString("ImageContrib"),
		    			result.getString("WikiUrl")
		    			);
		    	waterfalls.add(waterfall);
		    }
		} catch (SQLException e) {
		    throw new IllegalStateException(dateString + " Cannot connect to the database!", e);
		}
		return waterfalls;
	}
	
	
}
