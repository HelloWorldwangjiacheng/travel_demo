package com.travel.community.travel_demo.model;

import lombok.Data;

@Data
public class Question {
    private Long id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long createor;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;
}
