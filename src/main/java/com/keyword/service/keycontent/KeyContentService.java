package com.keyword.service.keycontent;

import java.util.List;

import com.keyword.domain.keycontent.KeyContent;
import com.uban.mybatis.Paging;

public interface KeyContentService{

	public KeyContent selectById(Integer id);
	
	public List<KeyContent> selectByListPage(KeyContent keycontent, Paging page);
	
	public List<KeyContent> listAll();
    
    public Integer insert(KeyContent keycontent);
    
    public Integer updateById(KeyContent keycontent);
    
    public Integer deleteById(Integer id);

}

