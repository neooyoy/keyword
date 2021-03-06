package com.cj.service.keycontent.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cj.service.keycontent.KeyContentService;
import com.cj.dao.keycontent.KeyContentDao;
import com.cj.domain.keycontent.KeyContent;
import com.cj.mybatis.Paging;

@Service
public class KeyContentServiceImpl implements KeyContentService{

    @Autowired
    private KeyContentDao keycontentDao;
    
    @Override
    public KeyContent selectById(Integer id) {
    	return this.keycontentDao.selectById(id);
    }
    
    @Override
    public List<KeyContent> selectByListPage(KeyContent keycontent, Paging page) {
    	return this.keycontentDao.selectByListPage(keycontent, page);
    }
    
    @Override
    public List<KeyContent> listAll() {
    	return this.keycontentDao.listAll();
    }
    
    @Override
    public Integer insert(KeyContent keycontent) {
    	this.keycontentDao.insert(keycontent);
    	return keycontent.getId();
    }
    
    @Override
    public Integer updateById(KeyContent keycontent) {
    	return this.keycontentDao.updateById(keycontent);
    }
    
    @Override
    public Integer deleteById(Integer id) {
    	return this.keycontentDao.deleteById(id);
    }


    /**
     * 批量导入数据
     *
     * @param contentList
     * @return
     * @throws Exception
     */
    public Integer batchInsertKeyContents(List<KeyContent> contentList) throws Exception {
        return this.keycontentDao.batchInsertKeyContents(contentList);
    }

    public Integer deleteByKeywords(String keywords) throws Exception {
        return this.keycontentDao.deleteByKeywords(keywords);
    }

    public Integer deleteAll() throws Exception{
        return this.keycontentDao.deleteAll();
    }

    public List<KeyContent> selectByKeywordsListPage(String Keywords, Paging page) throws Exception {
        return this.keycontentDao.selectByKeywordsListPage(Keywords, page);
    }

    public List<KeyContent> selectByKeyArrayListPage(String keywords, Paging page) throws Exception {
        return this.keycontentDao.selectByKeyArrayListPage(keywords, page);
    }
}

