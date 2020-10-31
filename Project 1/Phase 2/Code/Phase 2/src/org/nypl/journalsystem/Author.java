package org.nypl.journalsystem;

import org.nypl.journalsystem.core.IAuthor;

public class Author implements IAuthor {
	public String name;
	public Integer id;
	
	public Author (Integer aid, String aname) {
		id = aid;
		name = aname;
	}
	
	public String getName() {
		return this.name;
	}
}
