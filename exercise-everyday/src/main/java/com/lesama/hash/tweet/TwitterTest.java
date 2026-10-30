package com.lesama.hash.tweet;

class TwitterTest {
    public static void main(String[] args) {
        Twitter twitter = new Twitter();
        
        // 用例 1: 发推并获取
        twitter.postTweet(1, 5);
        System.out.println(twitter.getNewsFeed(1)); // 期望 [5]
        
        // 用例 2: 关注后获取
        twitter.follow(1, 2);
        twitter.postTweet(2, 6);
        System.out.println(twitter.getNewsFeed(1)); // 期望 [6, 5]
        
        // 用例 3: 取关后获取
        twitter.unfollow(1, 2);
        System.out.println(twitter.getNewsFeed(1)); // 期望 [5]
        
        // 用例 4: 验证 10 条限制 (发 12 条，应只返回 10 条)
        for (int i = 0; i < 12; i++) {
            twitter.postTweet(3, 100 + i);
        }
        System.out.println(twitter.getNewsFeed(3).size()); // 期望 10
    }
}