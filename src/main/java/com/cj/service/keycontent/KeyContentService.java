package com.cj.service.keycontent;

import java.util.List;

import com.cj.domain.keycontent.KeyContent;
import com.cj.mybatis.Paging;

public interface KeyContentService{

	public KeyContent selectById(Integer id);
	
	public List<KeyContent> selectByListPage(KeyContent keycontent, Paging page);
	
	public List<KeyContent> listAll();
    
    public Integer insert(KeyContent keycontent);
    
    public Integer updateById(KeyContent keycontent);
    
    public Integer deleteById(Integer id);

    public Integer deleteByKeywords(String keywords) throws Exception;


    public Integer deleteAll() throws Exception;

    /**
     * 批量导入数据
     *
     * @param contentList
     * @return
     * @throws Exception
     */
    public Integer batchInsertKeyContents(List<KeyContent> contentList) throws Exception;

    public List<KeyContent> selectByKeywordsListPage(String Keywords, Paging page) throws Exception;

    public List<KeyContent> selectByKeyArrayListPage(String keywords, Paging page) throws Exception;
}

