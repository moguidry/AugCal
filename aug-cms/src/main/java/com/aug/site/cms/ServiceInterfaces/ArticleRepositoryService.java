package com.aug.site.cms.ServiceInterfaces;

import com.aug.site.cms.Models.Article;
import com.aug.site.cms.Models.LightArticle;
import java.util.List;

public interface ArticleRepositoryService {

	List<LightArticle> getArticleList(int start, int count);

	List<LightArticle> getHiddenArticleList(int start, int count);

	Article getArticleById(int articleId);

	Article updateArticle(String user, int articleId, Article article);

	Article publishArticle(String user, int articleId);

	Article unpublishArticle(String user, int articleId);

	Article createArticle(String user, Article article);
}
