package com.spring.article.application.model;

import java.util.Date;

public class Article {
	
	private String articleId;
	private String articleTitle;
	private String articleBody;
	private Date creationTime;
	
	public Article() {}
	
	public Article(String articleId, String articleTitle, String articleBody, Date creationTime) {
		super();
		this.articleId = articleId;
		this.articleTitle = articleTitle;
		this.articleBody = articleBody;
		this.creationTime = creationTime;
	}

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public String getArticleBody() {
		return articleBody;
	}

	public void setArticleBody(String articleBody) {
		this.articleBody = articleBody;
	}

	public String getArticleId() {
		return articleId;
	}
	
	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}
	
	public Date getCreationTime() {
		return creationTime;
	}
	
	public void setCreationTime(Date date) {
		this.creationTime = date;
	}
	
	@Override
	public String toString() {
		return "Article [articleId=" + articleId + ", article=" + articleBody + ", creationTime=" + creationTime + "]";
	}

	@Override
	public int hashCode() {
		return Integer.parseInt(articleId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Article other = (Article) obj;
		return (articleId != other.articleId) ? false : true;
	}
	
}
