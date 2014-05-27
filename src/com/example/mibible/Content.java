package com.example.mibible;

public class Content {
	//Private variables
	int _book;
	int _chapter;
	int _verse;
	
	public Content(int book, int chapter, int verse) {
		this._book = book;
		this._chapter = chapter;
		this._verse = verse;
	}
	
	public int getBook() {
		return this._book;
	}
	
	public void setBook(int book) {
		this._book = book;
	}

	public int getChapter() {
		return this._chapter;
	}
	
	public void setChapter(int chapter) {
		this._chapter = chapter;
	}
	
	public int getVerse() {
		return this._verse;
	}
	
	public void setVerse(int verse) {
		this._verse = verse;
	}	
}
