package com.article.recommend;

import com.article.recommend.quartz.job.ArticleRecommendJob;
import org.junit.Test;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

public class RecommendJobTest extends ArticlerecommendApplicationTests {
    @Autowired
    private ArticleRecommendJob articleRecommendJob;
    @Test
    public  void execute(){
        articleRecommendJob.execute(null);

    }
}
