package api;

public class QueryBuilder {
	public static String waterfallQuery() {
		return "SELECT * FROM " + DatabaseConnector.DATABASE_NAME + "." + DatabaseConnector.WATERFALL_TABLE + ";";
	}
}
