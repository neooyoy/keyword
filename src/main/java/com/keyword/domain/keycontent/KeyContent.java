package com.keyword.domain.keycontent;  


import com.uban.mybatis.domain.BaseDomain;

public class KeyContent extends BaseDomain{  

    private static final long serialVersionUID = 1L;
    
    private Integer id; 
    
    private String key; 
    
    private String content; 


    public Integer getId() {  
        return id;  
    }  
  
    public void setId(Integer id) {  
        this.id = id;  
    }  

    public String getKey() {  
        return key;  
    }  
  
    public void setKey(String key) {  
        this.key = key;  
    }  

    public String getContent() {  
        return content;  
    }  
  
    public void setContent(String content) {  
        this.content = content;  
    }  

}  