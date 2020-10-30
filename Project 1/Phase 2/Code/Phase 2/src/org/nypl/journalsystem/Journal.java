package org.nypl.journalsystem;
import java.util.*;

public class Journal {
	
	public String name;
	public String publisher;
	public String location;
	public String issn;
	
	public List<Article> articles;
	
	public Journal (String name, String publisher, String location, String issn) {
		this.name = name;
		this.publisher = publisher;
		this.location = location;
		this.issn = issn;
		this.articles = new ArrayList<Article>();
	}
	
	public void addarticle(Article article) {
		// TODO
		this.articles.add(article);
	}
	
	public void listarticles() {
		
		if (articles.size() > 1) {
			System.out.format("Name: %s, Publisher: %s, Location: %s, ISSN: %s, Full issue\n", name, publisher, location, issn);
		} else {
			System.out.format("Name: %s, Publisher: %s, Location: %s, ISSN: %s, Not Full issue\n", name, publisher, location, issn);
		}
	}
}
