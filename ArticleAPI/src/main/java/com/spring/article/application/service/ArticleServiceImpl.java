package com.spring.article.application.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.spring.article.application.model.Article;

@Service
@CacheConfig(cacheNames = { "articles" })
public class ArticleServiceImpl implements ArticleService {

	private Map<String, Article> articles = new HashMap<>();

	@PostConstruct
	private void fillUsers() {
		articles.put("1", new Article("1", "articleTitle1", "articleBody1", new Date()));
		articles.put("2", new Article("2", "articleTitle2", "articleBody2", new Date()));
		articles.put("3", new Article("3", "articleTitle3", "articleBody3", new Date()));
		articles.put("4", new Article("4", "articleTitle4", "articleBody4", new Date()));
	}

	// http://localhost:8080/createArticle
	@Override
	public String createArticle(Article article) {
		if (this.articles.containsKey(article.getArticleId())) {
			return "Article already exists with this id.";
		} else {
			article.setCreationTime(new Date());
			this.articles.put(article.getArticleId(), article);
			return "Article is added.";
		}
	}

	//http://localhost:8080/modifyArticle
	@Override
	public String modifyArticle(Article article) {
		if (this.articles.containsKey(article.getArticleId())) {
			//To avoid changing creation time
			Article tempArticle = this.articles.get(article.getArticleId());
			article.setCreationTime(tempArticle.getCreationTime());
			
			this.articles.put(article.getArticleId(), article);
			return "Article is modified.";
		} else {
			return "Article does not exist. Please create first.";
		}
	}

	// http://localhost:8080/deleteArticle/3
	@Override
	public String deleteArticle(String articleId) {
		if (this.articles.containsKey(articleId)) {
			Article article = articles.get(articleId);

			Date currentDate = Calendar.getInstance().getTime();
			Date articleDate = article.getCreationTime();

			long diffInMillies = Math.abs(currentDate.getTime() - articleDate.getTime());
			long dayDifference = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

			if (dayDifference > 365) {
				this.articles.remove(articleId);
				return "Article is deleted.";
			} else {
				return "Article is only deleted if it's more than 1 year old.";
			}
		} else {
			return "Article does not exist. Please create first.";
		}
	}

	// http://localhost:8080/loadArticle/1
	@Override
	public Article loadArticle(String articleId) {
		if (this.articles.containsKey(articleId)) {
			return this.articles.get(articleId);
		} else {
			return null;
		}
	}

	// http://localhost:8080/loadArticles?date=10/09/2019
	@Override
	public List<Article> loadArticles(String articleInputDate) {
		List<Article> articleList = new ArrayList<>();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Calendar articleCreationCal = Calendar.getInstance();
		Calendar articleInputCal = Calendar.getInstance();
		
		this.articles.forEach((articleId, article) -> {
			try {
				articleCreationCal.setTime(article.getCreationTime());
				articleInputCal.setTime(formatter.parse(articleInputDate));
				
				if (articleCreationCal.get(Calendar.DATE)==(articleInputCal.get(Calendar.DATE))) {
					articleList.add(article);
				}
			} catch (ParseException e) {
				System.out.print("Invalid date entered");
			}
		});

		return articleList;
	}

	@Override
	public List<Article> findAll() {
		List<Article> articleList = new ArrayList<>();
		this.articles.forEach((articleId, article) -> {
			articleList.add(article);
		});

		return articleList;
	}

}
