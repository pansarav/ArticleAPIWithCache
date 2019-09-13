package com.spring.article.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.article.application.model.Article;

@Service
public interface ArticleService {
	
	public String createArticle(Article article);
	
	public String modifyArticle(Article article);
	
	public String deleteArticle(String articleId);
	
	public Article loadArticle(String articleId);
	
	public List<Article> loadArticles(String articleDate);

	public List<Article> findAll();

}
