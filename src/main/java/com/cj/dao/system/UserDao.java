package com.cj.dao.system;

import com.cj.annotation.DataSource;
import com.cj.domain.system.User;
import com.cj.mybatis.Paging;
import com.cj.mybatis.dao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends BaseDao<User> {
    @DataSource("")
    public User selectByUserId(@Param("userId") Integer userId);

    /**
     * 根据用户登录名查询用户
     *
     * @param loginname
     * @return
     * @author chenjun 20160629
     */
    @DataSource("")
    public User selectByLoginname(String loginname);

    /**
     * 查询人员列表
     *
     * @param user
     * @param page
     * @return
     * @throws Exception
     * @author chenjun 20160628
     */
    @DataSource("")
    public List<User> selectListPage(@Param("user") User user, @Param("page") Paging page) throws Exception;

    /**
     * 查询角色的总人数
     *
     * @param roldId
     * @return
     * @author chenjun 20160628
     */
    @DataSource("")
    public Long selectRolePersonCount(Integer roldId) throws Exception;

    /**
     * 根据用户登录名查询用户数
     *
     * @param loginname
     * @return
     * @throws Exception
     * @author chenjun 20160629
     */
    @DataSource("")
    public Long selectCountUserByLoginname(String loginname) throws Exception;
}
