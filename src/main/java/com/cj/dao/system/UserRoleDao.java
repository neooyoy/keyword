package com.cj.dao.system;

import com.cj.annotation.DataSource;
import com.cj.domain.system.UserRole;
import com.cj.mybatis.dao.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleDao extends BaseDao<UserRole> {
    /**
     * 修改用户角色
     *
     * @param userRole
     * @throws Exception
     * @author chenjun 20160629
     */
    @DataSource("")
    public void updateByUserId(UserRole userRole) throws Exception;

    /**
     * 查询用户角色
     *
     * @param userId
     * @throws Exception
     * @author chenjun 20160629
     */
    @DataSource("")
    public UserRole selectByUserId(Integer userId) throws Exception;
}