package org.nypl.journalsystem;
import java.util.*;

public class Article {
	public String title;
	
	// List of Authors
	public List<Integer> authors;
	
	//public Article() {}
	
	public Article(String title, List<Integer> authors) {
		title = title;
		authors = authors;
	}
	
	public void printArticle() {
		// TODO: Implement function
	}
}
