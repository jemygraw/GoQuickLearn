package net.duokr.goquicklearn.config;

import java.io.Serializable;

public class LearnContent implements Serializable {
	private static final long serialVersionUID = 1L;
	private String contentName;
	private String contentAbstract;
	private String chapter;
	private String nextChapter;
	private String previousChapter;

	public String getContentName() {
		return contentName;
	}

	public void setContentName(String contentName) {
		this.contentName = contentName;
	}

	public String getContentAbstract() {
		return contentAbstract;
	}

	public void setContentAbstract(String contentAbstract) {
		this.contentAbstract = contentAbstract;
	}

	public String getChapter() {
		return chapter;
	}

	public void setChapter(String chapter) {
		this.chapter = chapter;
	}

	public String getNextChapter() {
		return nextChapter;
	}

	public void setNextChapter(String nextChapter) {
		this.nextChapter = nextChapter;
	}

	public String getPreviousChapter() {
		return previousChapter;
	}

	public void setPreviousChapter(String previousChapter) {
		this.previousChapter = previousChapter;
	}

}
