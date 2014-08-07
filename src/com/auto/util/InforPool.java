package com.auto.util;

import java.util.HashMap;
import java.util.Map;

import com.auto.msg.resp.Article;

public class InforPool {

	private final static Map<String, Article> newsPool = new HashMap<String, Article>();
	
	public Article getArticleFromPool(String key){
		Article article = newsPool.get(key);
		if(article==null|article.getPicUrl().length()==0){
			//Load from JDBC
		}
		return article;
	}
	
}
