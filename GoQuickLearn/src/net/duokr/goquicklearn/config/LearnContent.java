package net.duokr.goquicklearn.config;

import java.io.Serializable;

public class LearnContent implements Serializable {
	private static final long serialVersionUID = 1L;
	private String contentName;
	private String contentAbstract;
	private String chapter;

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

}
