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

import api.DatabaseConnector;
import api_util.FilesUtil;

public class BackendTests {
	
	// test that the database.txt file can be accessed
    @Test 
    public void testConnectivity () {
    	DatabaseConnector dbc = new DatabaseConnector();
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
			e.printStackTrace();
		}
    }
 }