package com.keyword.domain.keycontent;  


import com.keyword.mybatis.domain.BaseDomain;

public class KeyContent extends BaseDomain{  

    private static final long serialVersionUID = 1L;
    
    private Integer id; 
    
    private String keyword; 
    
    private String content; 


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
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
