package org.example;

import java.util.List;

public class ArticleService {
    private List<Article> articleList;

    public ArticleService(List<Article> articleList) {
        this.articleList = articleList;
    }
    public void write(int lastId, String title, String body, int authorId){
        Article newArticle = new Article();
        newArticle.setId(lastId);
        newArticle.setTitle(title);
        newArticle.setBody(body);
        newArticle.setRegDate(Util.getNowDate());
        newArticle.setAuthorId(authorId);
        articleList.add(newArticle);
    }

    public List<Article> getArticles(){
        return articleList;
    }

    public Article getArticleById(int id){
        for(Article article : articleList){
            if(article.getId() == id){
                return article;
            }
        }
        return null;
    }

    public void modify(Article article, String title, String body){
        article.setTitle(title);
        article.setBody(body);
    }
    public void delete(Article article){
        articleList.remove(article);
    }
}
