package com.aug.site.cms.ServiceImpls;

import com.aug.site.cms.Models.Article;
import com.aug.site.cms.Models.LightArticle;
import com.aug.site.cms.ServiceInterfaces.ArticleRepositoryService;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.inject.Inject;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.markdown4j.Markdown4jProcessor;

public class HibernateArticleRepositoryService implements ArticleRepositoryService {

	Session session;

	@Inject
	public HibernateArticleRepositoryService(Session session) {
		this.session = session;
	}

	@Override
	public Article getArticleById(int articleId) {
		return (Article) session.get(Article.class, articleId);
	}

	@Override
	public Article updateArticle(String user, int articleId, Article update) {
		Article article = (Article) session.get(Article.class, articleId);

		article.setTitle(update.getTitle());
		article.setContent(update.getContent());
		article.setMiniContent(getMiniContent(update.getContent()));
		article.setAuthorId(user);
		article.setLastModified(new Date());

		session.beginTransaction();

		session.update(article);

		session.getTransaction().commit();

		return article;
	}

	@Override
	public Article publishArticle(String user, int articleId) {
		Article article = (Article) session.get(Article.class, articleId);

		article.setActive(true);
		article.setAuthorId(user);
		article.setLastModified(new Date());

		session.beginTransaction();

		session.update(article);

		session.getTransaction().commit();

		return article;
	}

	@Override
	public Article unpublishArticle(String user, int articleId) {
		Article article = (Article) session.get(Article.class, articleId);

		article.setActive(false);
		article.setAuthorId(user);
		article.setLastModified(new Date());

		session.beginTransaction();

		session.update(article);

		session.getTransaction().commit();

		return article;
	}

	@Override
	public Article createArticle(String user, Article article) {

		article.setAuthorId(user);
		article.setDateCreated(new Date());
		article.setLastModified(new Date());
		article.setMiniContent(getMiniContent(article.getContent()));
		article.setActive(true);

		session.beginTransaction();

		session.save(article);

		session.getTransaction().commit();

		return article;
	}

	@Override
	public List<LightArticle> getArticleList(int start, int count) {

		List<Article> articles = session.createCriteria(Article.class)
				.addOrder(Order.desc("dateCreated"))
				.add(Restrictions.gt("id", 0))
				.add(Restrictions.eq("active", true))
				.setFirstResult(start).setMaxResults(count).list();

		List<LightArticle> ret = new LinkedList<LightArticle>();
		for (Article article : articles) {
			ret.add(new LightArticle(article));
		}

		return ret;
	}

	@Override
	public List<LightArticle> getHiddenArticleList(int start, int count) {
		List<Article> articles = session.createCriteria(Article.class)
				.addOrder(Order.desc("dateCreated"))
				.add(Restrictions.gt("id", 0))
				.add(Restrictions.eq("active", false))
				.setFirstResult(start).setMaxResults(count).list();

		List<LightArticle> ret = new LinkedList<LightArticle>();
		for (Article article : articles) {
			ret.add(new LightArticle(article));
		}

		return ret;
	}

	private String getMiniContent(String content) {
		StringBuilder strb = new StringBuilder();

		int count = 0;
		int max = 1;
		int start = 0;
		while (count < max && start < content.length()) {
			int index = content.indexOf("\n", start);
			if (index > 0) {
				strb.append(content.substring(start, index));
			} else {
				break;
			}
			count++;
			start = index + 1;
		}

		// hardcoded field limitation of 300 characters, so take extra html into account
		// 100 characters *should* be enough, although this really should be a text field.
		String result = strb.toString();
		if (result.length() > 200) {
			result = result.substring(0, 200);
		}

		try {
			return new Markdown4jProcessor().process(result);
		} catch (Exception ex) {
			return result;
		}
	}
}
