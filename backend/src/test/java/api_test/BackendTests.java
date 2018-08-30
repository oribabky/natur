package api_test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import api.AttractionQuery;
import api.DatabaseConnector;
import api_util.FilesUtil;

public class BackendTests {
	
	// test that the database.txt file can be accessed
    @Test 
    public void testConnectivity () {
    	DatabaseConnector dbc = new DatabaseConnector();
    }
    
    // test that the query looks as expected when building a query
    @Test
    public void testQueryBuild () {
    	
    	Set<String> set = new HashSet<>();
    	set.add(AttractionQuery.ATTRACTION_CANYON);
    	set.add(AttractionQuery.ATTRACTION_WATERFALL);
    	
    	AttractionQuery filter = new AttractionQuery(set);
    	
    	set.remove(AttractionQuery.ATTRACTION_CANYON);
    	AttractionQuery filter1 = new AttractionQuery(set);
    	
    	set.remove(AttractionQuery.ATTRACTION_WATERFALL);
    	set.add(AttractionQuery.ATTRACTION_CANYON);
    	AttractionQuery filter2 = new AttractionQuery(set);
    	
    	set.remove(AttractionQuery.ATTRACTION_CANYON);
    	AttractionQuery filter3 = new AttractionQuery(set);
    	
    	
    	String q1 = filter.buildSelectAttractionQuery();
    	String q2 = filter3.buildSelectAttractionQuery();
    	assertEquals(q1, q2);
    	// System.out.println(q1);
    	
    	q1 = filter1.buildSelectAttractionQuery();
    	q2 = filter2.buildSelectAttractionQuery();
    	assertNotEquals(q1, q2);
    	// System.out.println("Waterfall query:" + q1);
    	// System.out.println("Canyon query:" + q2);
    }
    
    @Test 
    public void testRemoveInvalidAttractions () {
    	Set<String> list = new HashSet<>();
  
    	list.add(AttractionQuery.ATTRACTION_CANYON);
    	list.add(AttractionQuery.ATTRACTION_WATERFALL);
    	list.add("asd");
    	list.add("iopkop");
    	list.add("waterfall");
    	
    	int lenList = list.size();
    	assertEquals(lenList, 5);
    	
    	list = AttractionQuery.removeInvalidAttractionNames(list);
    	lenList = list.size();
    	assertEquals(lenList, 2);
    	
    	Set<String> list2 = new HashSet<>();
    	list2.add(AttractionQuery.ATTRACTION_CANYON);
    	
    	list2 = AttractionQuery.removeInvalidAttractionNames(list2);
    	lenList = list2.size();
    	assertEquals(1, lenList);
    }
    
    @Test
    public void testNrLinesFile() {
    	String testFileName = "testFile.txt";
    	Path file = FileSystems.getDefault().getPath(testFileName);
    	PrintWriter writer;
    	
    	int nrLinesExpected;
    	int nrLinesActual;
    	
    	// test an empty file:
    	// create an empty file
		try {
			writer = new PrintWriter(testFileName, "UTF-8");
	    	writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	nrLinesExpected = 0;
    	nrLinesActual = FilesUtil.nrLinesFile(file);
    	assertEquals(nrLinesExpected, nrLinesActual);
    	
    	// test a non-empty file:
    	// create an empty file
		try {
			writer = new PrintWriter(testFileName, "UTF-8");
			writer.println("The first line");
			writer.println("The second line");
	    	writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		nrLinesExpected = 2;
    	nrLinesActual = FilesUtil.nrLinesFile(file);
    	assertEquals(nrLinesExpected, nrLinesActual);
    	
    	// delete the test file
    	try {
			Files.delete(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
 }