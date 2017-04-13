package com.cj.dao.keycontent;

import com.cj.annotation.DataSource;
import com.cj.constant.DataSourceConstant;
import com.cj.mybatis.Paging;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cj.mybatis.dao.BaseDao;
import com.cj.domain.keycontent.KeyContent;

import java.util.List;

@Repository
public interface KeyContentDao extends BaseDao<KeyContent>{


    /**
     * 批量导入数据
     *
     * @param contentList
     * @return
     * @throws Exception
     */
    @DataSource(DataSourceConstant.MASTERDATASOURCE)
    public Integer batchInsertKeyContents(@Param("contentList") List<KeyContent> contentList) throws Exception;

    @DataSource(DataSourceConstant.MASTERDATASOURCE)
    public Integer deleteAll() throws Exception;

    @DataSource(DataSourceConstant.MASTERDATASOURCE)
    public Integer deleteByKeywords(@Param("keywords") String keywords) throws Exception;

    @DataSource("")
    public List<KeyContent> selectByKeywordsListPage(@Param("keywords") String Keywords, @Param("page") Paging page) throws Exception;

    @DataSource("")
    public List<KeyContent> selectByKeyArrayListPage(@Param("keywords") String keywords,  @Param("page") Paging page) throws Exception;
}

