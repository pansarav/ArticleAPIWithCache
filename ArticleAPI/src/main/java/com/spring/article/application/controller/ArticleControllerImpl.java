package com.spring.article.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.article.application.model.Article;
import com.spring.article.application.service.ArticleService;

@RestController()
@RequestMapping(path = "/", produces = "application/json")
public class ArticleControllerImpl {

	@Autowired
	private ArticleService articleService;

	@PostMapping(path = "/createArticle", consumes = "application/json")
	public String createArticle(@RequestBody Article article) {
		String validationResult = validateCreateRequest(article);
		if (validationResult.isEmpty()) {
			return articleService.createArticle(article);
		}
		return validationResult;
	}

	private String validateCreateRequest(Article article) {
		String errorMessage = "";
		if (article.getArticleId() == null || article.getArticleId().isEmpty()) {
			errorMessage += "Article id is mandatory";
		}

		if (article.getArticleTitle() == null) {
			errorMessage += "Article title is mandatory";
		} else if (!isValidTitleLength(article.getArticleTitle())) {
			errorMessage += "Article title length can't be more than 64 characters";
		}

		if (article.getArticleBody() == null) {
			errorMessage += "Article body is mandatory";
		}

		return errorMessage;
	}

	@PostMapping(path = "/modifyArticle", consumes = "application/json")
	public String modifyArticle(@RequestBody Article article) {
		String validationResult = validateModifyRequest(article);
		if (validationResult.isEmpty()) {
			return articleService.modifyArticle(article);
		}
		return validationResult;
	}

	private String validateModifyRequest(Article article) {
		String errorMessage = "";
		if (article.getArticleId() == null || article.getArticleId().isEmpty()) {
			errorMessage += "Article id is mandatory";
		}

		if (article.getArticleTitle() == null && article.getArticleBody() == null) {
			errorMessage += "Article title or body is mandatory";
		} else if (article.getArticleTitle() != null && !isValidTitleLength(article.getArticleTitle())) {
			errorMessage += "Article title length can't be more than 64 characters";
		}

		return errorMessage;
	}

	private boolean isValidTitleLength(String articleTitle) {
		return articleTitle.length() > 64 ? false : true;
	}

	@GetMapping("/deleteArticle/{articleId}")
	public String deleteArticle(@PathVariable String articleId) {
		return articleService.deleteArticle(articleId);
	}

	@GetMapping("/loadArticle/{articleId}")
	public Article loadArticle(@PathVariable String articleId) {
		return articleService.loadArticle(articleId);
	}

	@GetMapping("/loadArticles")
	public List<Article> loadArticles(@RequestParam(name = "date", required = true) String articleDate) {
		return articleService.loadArticles(articleDate);
	}

	@GetMapping("/loadAllArticles")
	public List<Article> loadArticles() {
		return articleService.findAll();
	}

}
