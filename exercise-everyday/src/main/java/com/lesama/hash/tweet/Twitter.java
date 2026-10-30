package com.lesama.hash.tweet;

import java.util.*;

public class Twitter {
    // 全局时间戳，用于比较推文的新旧
    private int timeStamp;
    // 用户 -> 关注的人集合（使用 Set 自动去重）
    private Map<Integer, Set<Integer>> followMap;
    // 用户 -> 自己的推文链表（头插法，最新在头）
    private Map<Integer, Tweet> tweetMap;

    private class Tweet {
        int id;
        int time;
        Tweet next;
        Tweet(int id, int time) {
            this.id = id;
            this.time = time;
        }
    }

    public Twitter() {
        timeStamp = 0;
        followMap = new HashMap<>();
        tweetMap = new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {
        // 如果用户不存在，推文链表默认为空
        Tweet newTweet = new Tweet(tweetId, timeStamp++);
        // 头插法，保证链表头部是最新推文
        newTweet.next = tweetMap.get(userId);
        tweetMap.put(userId, newTweet);
    }
    
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> res = new ArrayList<>();
        // 最大堆：按推文时间降序排列
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> b.time - a.time);
        
        // 1. 把用户自己放进关注列表（以便获取自己的推文）
        Set<Integer> users = followMap.getOrDefault(userId, new HashSet<>());
        users.add(userId); 
        
        // 2. 将所有相关用户的最新一条推文（链表头）加入堆
        for (int id : users) {
            Tweet tweet = tweetMap.get(id);
            if (tweet != null) {
                pq.offer(tweet);
            }
        }
        
        // 3. 取出堆顶（最新），然后将该用户的下一条推文加入堆，直到取满10条或堆空
        while (!pq.isEmpty() && res.size() < 10) {
            Tweet tweet = pq.poll();
            res.add(tweet.id);
            if (tweet.next != null) {
                pq.offer(tweet.next);
            }
        }
        return res;
    }
    
    public void follow(int followerId, int followeeId) {
        // 题目允许关注自己，但获取新闻流时已经手动添加了自己，所以这里直接存即可
        followMap.computeIfAbsent(followerId, k -> new HashSet<>()).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        if (followMap.containsKey(followerId)) {
            followMap.get(followerId).remove(followeeId);
        }
    }
}