package org.nypl.journalsystem;

import java.io.*;
import java.util.*;

import org.apache.commons.csv.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.nypl.journalsystem.core.*;

public class LibrarySystem implements ILibrarySystem {
	
	List<Journal> journals;
	HashMap<Integer, Author> authors;
	HashMap<Integer, Article> articles;
	
	public LibrarySystem() {
		this.journals = new ArrayList<Journal>();
		this.authors = new HashMap<Integer, Author>();
		this.articles = new HashMap<Integer, Article>();
		
		// Define publishers
		Publisher pub_springer = new Publisher("Springer", "Germany");
		Publisher pub_elsevier = new Publisher("Elsevier", "Netherlands");
		Publisher pub_naturres = new Publisher("Nature Research", "Great Britain");
		
		// Initialize system with default journals.
		Journal journal1 = new Journal("Higher Education", pub_springer, "0018-1560");
		Journal journal2 = new Journal("System", pub_elsevier, "0346-2511");
		Journal journal3 = new Journal("Chem", pub_elsevier, "2451-9294");
		Journal journal4 = new Journal("Nature", pub_naturres, "1476-4687");			
		Journal journal5 = new Journal("Society", pub_springer, "0147-2011");
		
		// Add journals to list of journals
		journals.add(journal1);
		journals.add(journal2);
		journals.add(journal3);
		journals.add(journal4);
		journals.add(journal5);
	}
	
	public void load() throws FileNotFoundException, IOException {
		loadAuthors();
		loadArticles();
	}
	
	protected void loadAuthors() throws FileNotFoundException, IOException {
		File file = new File("data/Authors.csv");

		try (FileReader fr = new FileReader(file); BufferedReader br = new BufferedReader(fr)) {
			br.readLine();
			String strCurrentline, name;
			Integer id;
			
			while ((strCurrentline = br.readLine()) != null) {
				String[] splitStr = strCurrentline.split(",");
				
				id   = Integer.parseInt(splitStr[0]);
				name = (splitStr[2] + splitStr[1]).replace("\"","");
		
				Author author = new Author(id, name);		
				authors.put(id, author);
			}
			br.close();
		} catch (Exception error) {
			error.printStackTrace();
		}
	}
	
	protected void loadArticles() throws FileNotFoundException, IOException {
		File file = new File("data/Articles.csv");

		//TODO: Load articles from file and assign them to appropriate journal
		try (FileReader fr = new FileReader(file); BufferedReader br = new BufferedReader(fr)) {
			br.readLine();
			Integer id;
			String strCurrentline, title, issn;
			List<Integer> authors;
			
			while ((strCurrentline = br.readLine()) != null) {
				
				String[] splitStr = strCurrentline.split(",");
				
				// Extract information
				id    = Integer.parseInt(splitStr[0].strip());
				title = splitStr[1].strip().replace("\"", "");
				issn  = splitStr[3].strip();
				
				// Split referenced authors by ';' to get a list
				String[] splitAuth = splitStr[2].replace("[", "").replace("]", "").split(";");
				String[] splitCite = splitStr[4].replace("[", "").replace("]", "").split(";");
				// Convert to Integer
				List<Author> l_authors = new ArrayList<Author>();
				List<Integer> l_citations = new ArrayList<Integer>();
				for(String s: splitAuth) {
					int aid = Integer.valueOf(s.strip());
					Author a = this.authors.get(aid);
					l_authors.add(a);
				}
				
				for (String s: splitCite) {
					int cid = Integer.valueOf(s.strip());
					l_citations.add(cid);
				}
				
				// Create new Article object with extracted info
				Article article = new Article(id, title, issn, l_authors, l_citations);
				
				// Save article in total list of articles
				articles.put(id, article);
				
				// Put articles in journals
				for (Journal j: journals) {
					if (j.issn.equals(issn)) {
						j.addarticle(article);
					}
				}
			}
			
			br.close();
		} catch (Exception error) {
			error.printStackTrace();
		}
	}
	
	
	public void listContents() {
		System.out.println("Contents:");

		// Call getArticles function
		for (Journal j: journals) {
			j.getArticles();
		}
	}
	
	public Collection<? extends IAuthor> getAllAuthors() {
		return this.authors.values();
	}
	
	public Collection<? extends IJournal> getAllJournals() {
		return this.journals;
	}
	
	public Collection<? extends IArticle> getArticlesByAuthor(Author i_author) {
		
		Map<Author, List<Article>> AuthorArticle = new HashMap<Author, List<Article>>();
		
		// Loop over every author in every article
		for (Article l_article: articles.values()) {
			for (Author l_author: l_article.getAuthors()) {
				// If the author already exists add article to the list
				if (AuthorArticle.containsKey(l_author)) {
					AuthorArticle.get(l_author).add(l_article);
				} else { // Else make a new list of articles for that author
					List<Article> newarticle = new ArrayList<Article>();
					newarticle.add(l_article);
					AuthorArticle.put(l_author, newarticle);
				}
			}
		}
	}

	public Collection<? extends IArticle> getArticlesCitedByArticle(Article a) {
		
		List<Integer> i_cited = a.getCitations();
		List<Article> citedarticles = new ArrayList<Article>();
		
		for (Integer i_cite: i_cited) {
			citedarticles.add(articles.get(i_cite));
		}
		
		return citedarticles;
	}
	
	public Collection<? extends IArticle> getArticlesCitingArticle(Article l_article) {
		List<Article> children_cite = new ArrayList<Article>();
		
		for (Article a: articles.values()) {
			for (Integer i_cite: l_article.getCitations()) {
				if (i_cite == a.id) {children_cite.add(a);}
			}
		}
		
		return children_cite;
	}
	
	public static final void main(String[] args) throws Exception {
		LibrarySystem librarySystem = new LibrarySystem();
		
		librarySystem.load();
		librarySystem.listContents();
	}
	
}