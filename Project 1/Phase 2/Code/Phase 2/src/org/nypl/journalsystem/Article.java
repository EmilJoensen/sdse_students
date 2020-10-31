package org.nypl.journalsystem;
import java.util.*;

import org.nypl.journalsystem.core.IArticle;
import org.nypl.journalsystem.core.IAuthor;

public class Article implements IArticle {
	public Integer id;
	public String title;
	public String issn;
	public List<Author> authors;
	public List<Integer> citedarticlesid;
	
	public Article(Integer id, String title, String issn, List<Author> authors, List<Integer> citedarticlesid) {
		this.id = id;
		this.title = title;
		this.issn = issn;
		this.authors = authors;
		this.citedarticlesid = citedarticlesid;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public List<Integer> getCitations() {
		return this.citedarticlesid;
	}
	
	public List<? extends IAuthor> getAuthors() {
		return this.authors;
	}
}
