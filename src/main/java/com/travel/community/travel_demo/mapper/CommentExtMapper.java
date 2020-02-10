package com.travel.community.travel_demo.mapper;


import com.travel.community.travel_demo.model.Comment;

public interface CommentExtMapper {
    int incCommentCount(Comment record);
}