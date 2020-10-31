package org.nypl.journalsystem;
import java.util.*;

import org.nypl.journalsystem.core.*;

import org.nypl.journalsystem.core.IJournal;

public class Journal implements IJournal {
	
	public String name;
	public Publisher publisher;
	public String location;
	public String issn;
	
	public List<Article> articles;
	
	public Journal (String name, Publisher publisher, String issn) {
		this.name = name;
		this.publisher = publisher;
		this.issn = issn;
		this.articles = new ArrayList<Article>();
	}
	
	public void addarticle(Article article) {
		this.articles.add(article);
	}
	
	public Collection<? extends IArticle> getArticles() {
		
		System.out.format("Name: %s, Publisher: %s, Location: %s, ISSN: %s, Full issue: %s\n", 
						  name, 
						  this.publisher.name, 
						  this.publisher.location, 
						  issn, 
						  isFullIssue()
		);
		
		for (Article a: this.articles) {
			System.out.println(a.getTitle());
		}
		
		return articles;
	
	}
	
	public String getIssn() {
		return this.issn;
	}
	
	public String getName() {
		return this.name;
	}
	
	public boolean isFullIssue() {
		if (articles.size() > 1) {
			return true;
		} else {
			return false;
		}
	}
	
	public iPublisher getPublisher () {
		
	}
}
