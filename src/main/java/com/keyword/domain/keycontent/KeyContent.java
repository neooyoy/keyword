package com.keyword.domain.keycontent;  


import com.keyword.mybatis.domain.BaseDomain;

import java.util.List;

public class KeyContent extends BaseDomain{  

    private static final long serialVersionUID = 1L;
    
    private Integer id; 
    
    private String keyword; 
    
    private String content;

    private String simpleContent;

    public String getSimpleContent() {
        return simpleContent;
    }

    public void setSimpleContent(String simpleContent) {
        this.simpleContent = simpleContent;
    }

    public Integer getId() {
        return id;  
    }  
  
    public void setId(Integer id) {  
        this.id = id;  
    }  

    public String getContent() {
        return content;  
    }  
  
    public void setContent(String content) {  
        this.content = content;

        if (content != null) {
            if (content.length() > 100) {
                this.setSimpleContent(content.substring(0, 100) + "...");
            } else {
                this.setSimpleContent(content);
            }
        } else {
            this.setSimpleContent("");
        }
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
