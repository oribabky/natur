package api_util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/** 
 * general utility functions for files
 * @author tobias
 *
 */
public class FilesUtil {
	
	// check the number of lines in a file
	public static int nrLinesFile(Path file) {
		int nrLines = 0;
		
		try (
				BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8);
			) {
		    
			// read the file line for line
		    while ((reader.readLine()) != null) {
		    	nrLines ++;
		    }
		    
		} catch (FileNotFoundException e) {
			System.out.println("Could not find the file!");
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return nrLines;
	}
}
