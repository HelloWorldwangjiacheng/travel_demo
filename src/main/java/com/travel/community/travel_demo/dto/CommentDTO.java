package com.travel.community.travel_demo.dto;

import com.travel.community.travel_demo.model.User;
import lombok.Data;

@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private String content;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
//    private Long gmtModified;
    private Integer likeCount;
    private User user;
    private Integer commentCount;
}
