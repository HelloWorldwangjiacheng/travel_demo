package com.travel.community.travel_demo.mapper;


import com.travel.community.travel_demo.model.Comment;

/**
 * @author w1586
 */
public interface CommentExtMapper {
    /**
     * 增加评论数
     * @param record
     * @return
     */
    int incCommentCount(Comment record);
}