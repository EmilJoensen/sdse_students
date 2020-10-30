package org.nypl.journalsystem;

import java.io.*;
import java.util.*;

import org.apache.commons.csv.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LibrarySystem {
	
	List<Journal> journals;
	HashMap<Integer, Author> authors;
	HashMap<String, Publisher> publishers;
	
	public LibrarySystem() {
		journals = new ArrayList<Journal>();
		authors = new HashMap<Integer, Author>();
		
		// Initialize system with default journals.
		Journal journal1 = new Journal("Higher Education", "Springer", "Germany", "0018-1560");
		Journal journal2 = new Journal("System", "Elsevier", "Netherlands", "0346-2511");
		Journal journal3 = new Journal("Chem", "Elsevier", "Netherlands", "2451-9294");
		Journal journal4 = new Journal("Nature", "Nature Research", "Great Britain", "1476-4687");			
		Journal journal5 = new Journal("Society", "Springer", "Germany", "0147-2011");
		
		// Add journals to list of journals
		journals.add(journal1);
		journals.add(journal2);
		journals.add(journal3);
		journals.add(journal4);
		journals.add(journal5);
	
		
		// Is this part needed?
		/*
		for (Journal j: journals) {
			
			// List publisher with Journal
			Publisher p = new Publisher(j.name, j.location);
			
			if (publishers.containsKey(p.name)) {
				continue;
			} else {
				publishers.put(p.name, p)
			}
		}
		*/
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
			String strCurrentline, title, issn;
			List<Integer> authors;
			
			while ((strCurrentline = br.readLine()) != null) {
				//System.out.println(strCurrentline);
				
				String[] splitStr = strCurrentline.split(",");
				
				title = splitStr[1].strip().replace("\"", "");
				issn  = splitStr[3].strip();
				
				System.out.println(issn);
				
				// Split referenced authors by ';' to get a list
				String[] splitAuth = splitStr[2].replace("[", "").replace("]", "").split(";");
				// Convert to Integer
				authors = new ArrayList<Integer>();
				for(String s : splitAuth) authors.add(Integer.valueOf(s.strip()));
				
				Article article = new Article(title, authors);
				
				for (Journal j: journals) {
					
					
					if (j.issn.equals(issn)) {
						j.addarticle(article);
					}
					//System.out.println(j.articles.size());
				}
			}
			
			br.close();
		} catch (Exception error) {
			error.printStackTrace();
		}
	}
	
	
	public void listContents() {
		System.out.println("Contents:");
		//TODO: Print all journals with their respective articles and authors to the console.
		for (Journal j: journals) {
			j.listarticles();
		}
	}
	
	public static final void main(String[] args) throws Exception {
		LibrarySystem librarySystem = new LibrarySystem();
		
		librarySystem.load();
		librarySystem.listContents();
	}
}