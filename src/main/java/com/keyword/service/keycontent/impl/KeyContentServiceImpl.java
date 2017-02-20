package com.keyword.service.keycontent.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keyword.service.keycontent.KeyContentService;
import com.keyword.dao.keycontent.KeyContentDao;
import com.keyword.domain.keycontent.KeyContent;
import com.keyword.mybatis.Paging;

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
}

