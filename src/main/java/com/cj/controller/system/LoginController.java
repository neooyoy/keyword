package com.cj.controller.system;

import com.cj.constant.Constant;
import com.cj.controller.base.BaseController;
import com.cj.domain.keycontent.KeyContent;
import com.cj.domain.system.User;
import com.cj.domain.system.UserRole;
import com.cj.mybatis.Paging;
import com.cj.service.keycontent.KeyContentService;
import com.cj.service.system.SystemService;
import com.cj.utils.AES_DE;
import com.cj.utils.PageJsonUtil;
import com.cj.vo.JsonObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 登录入口
 * User: chenjun
 * Date: 2016/06/29
 * Time: 17:19
 */
@Controller
public class LoginController extends BaseController {

    @Resource
    public SystemService systemService;

    @Autowired
    private KeyContentService keyContentService;

    @Value("#{config['sso.login.sys.token']}")
    private String sso_login_sys_token;


    private void setWrongCount(User user, Model model) throws Exception {
        if (user.getWrongCount() == null) {
            model.addAttribute("wrongCount", 1);
        } else {
            if (user.getWrongCount() > 3) {
                model.addAttribute("showVerifycode", 1);
                model.addAttribute("wrongCount", user.getWrongCount() + 1);
            } else {
                model.addAttribute("wrongCount", user.getWrongCount() + 1);
            }
        }
    }

    /**
     * 用户登录
     *
     * @param user
     * @return
     * @author chenjun 20160629
     */
    @RequestMapping("/login")
    public String login(User user, Model model, HttpServletRequest request) {
        String url = "";
        try {
            if (StringUtils.isBlank(user.getLoginname()) && StringUtils.isBlank(user.getPassword())) {
                setWrongCount(user, model);
                return "system/login";
            }

            if (StringUtils.isBlank(user.getLoginname()) || StringUtils.isBlank(user.getPassword())) {
                setWrongCount(user, model);
                model.addAttribute("message", "请输入登录名和密码");
                model.addAttribute("loginname", user.getLoginname());
                return "system/login";
            }

            if (StringUtils.isNotBlank(user.getImgcode()) &&
                    !((String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY)).equals(user.getImgcode())) {
                model.addAttribute("message", "验证码不正确");
                model.addAttribute("loginname", user.getLoginname());
                model.addAttribute("showVerifycode", 1);
                return "system/login";
            }

            User curUser = systemService.selectByLoginname(user.getLoginname());
            if (curUser == null) {
                model.addAttribute("message", "登录名或密码错误");
                model.addAttribute("loginname", user.getLoginname());
                model.addAttribute("password", user.getPassword());
                setWrongCount(user, model);
                return "system/login";
            }

            /*if (curUser.getForbidden().equals(1)) {
                Integer curMinute = (int) (System.currentTimeMillis() / 1000);
                if ((curUser.getLastLoginAt() + 15 * 60) > curMinute) {
                    model.addAttribute("message", "请" + ((15 * 60 - (curMinute - curUser.getLastLoginAt())) / 60) + "分钟后再登录");
                    model.addAttribute("loginname", user.getLoginname());
                    return "system/login";
                } else {
                    curUser.setWrongCount(0);
                    curUser.setForbidden(0);
                }
            }*/

            if (!AES_DE.encrypt2(user.getPassword() + user.getLoginname(), sso_login_sys_token).toLowerCase().equals(curUser.getPassword())) {
                User modifyUser = new User();
                modifyUser.setId(curUser.getId());
                modifyUser.setWrongCount(curUser.getWrongCount() + 1);
                modifyUser.setForbidden(0);
                systemService.modifyUser(modifyUser);

                model.addAttribute("message", "密码错误");
                model.addAttribute("loginname", user.getLoginname());
                model.addAttribute("password", user.getPassword());
                return "system/login";
            }

            /*User modifyUser = new User();
            modifyUser.setId(curUser.getId());
            modifyUser.setWrongCount(0);
            modifyUser.setForbidden(0);
            modifyUser.setLastLoginAt((int) (System.currentTimeMillis() / 1000));
            systemService.modifyUser(modifyUser);*/


            UserRole userRole = systemService.selectByUserId(curUser.getId());
            if (userRole != null) {
                curUser.setRoleId(userRole.getRoleId());
            }

            curUser.setPassword(null);

            //保存session
            HttpSession session = request.getSession();
            session.setAttribute("user", curUser);

            if (curUser.getRoleId().equals(Constant.COMMON_ROLE_ID)) {
                url = "redirect:/keycontentController/importExcel";
            } else {
                url = "redirect:/system/user";
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            model.addAttribute("message", "系统错误");
            return "system/login";
        }

        return url;
    }

    /**
     * 登录页
     *
     * @return
     * @author chenjun 20160629
     */
  /*  @RequestMapping("/login")
    public String login(Model model) {
        return "system/login";
    }*/
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(HttpSession session) throws IOException {
        session.invalidate();
        return "system/login";
    }





    /**
     * 系统首页
     */
    @RequestMapping("/")
    public String index() throws Exception {
        return "keyword/list";
    }


    /**
     * 详情
     *
     * @param id
     * @return
     */
    @RequestMapping("/detail")
    public String keywordDetail(Integer id, String keyword, Model model) {
        KeyContent keyContent = null;
        if (id != null) {

            try {

                keyContent = this.keyContentService.selectById(id);
                if (keyContent != null) {
                    String content = keyContent.getContent().replaceAll(" ", "&nbsp;").replaceAll("\\n", "<br/>");

                    if (StringUtils.isNotBlank(keyword)) {
//                        keyword = new String(keyword.getBytes("ISO-8859-1"), "UTF-8");

                        String[] keywordArray = keyword.split(" ");
                        for (int i=0; i<keywordArray.length; i++) {
                            if (StringUtils.isNotBlank(keywordArray[i])) {
                                content = content.replaceAll(keywordArray[i], "<font color='red'>" + keywordArray[i] + "</font>");
                            }
                        }
                    }

                    keyContent.setContent(content);
                }
            } catch (Exception e) {
                logger.error(e);
            }
        }

        model.addAttribute("length", keyContent != null && keyContent.getContent() != null ? keyContent.getContent().length() : 0);
        model.addAttribute("keyContent", keyContent);

        return "keyword/detail";
    }

    /**
     * 列表查询
     *
     * @return
     */
    @RequestMapping("/querylist")
    @ResponseBody
    public JsonObject querylist(String keywords, Paging page) {
        JsonObject jsonObject = new JsonObject();

        if (StringUtils.isBlank(keywords)) {
            return jsonObject;
        }

		try {

            String sql = "";
            String[] keywordArray = keywords.split(" ");
            List<String> keywordList = new ArrayList<>();

            for (int i=0; i<keywordArray.length; i++) {
                if (StringUtils.isNotBlank(keywordArray[i])) {
                    keywordList.add(keywordArray[i]);
                }
            }

            for (int i=0; i<keywordList.size(); i++) {
                if (i == 0) {
                    sql += " kc.content like '%" + keywordList.get(i) + "%'";
                } else {
                    sql += " and kc.content like '%" + keywordList.get(i) + "%'";
                }
            }

            List<KeyContent> keyContentList = this.keyContentService.selectByKeyArrayListPage(sql, page);
            if (keyContentList.isEmpty()) {

                sql = "";

                for (int i=0; i<keywordList.size(); i++) {
                    if (i == 0) {
                        sql += " kc.content like '%" + keywordList.get(i) + "%'";
                    } else {
                        sql += " or kc.content like '%" + keywordList.get(i) + "%'";
                    }
                }

                keyContentList = this.keyContentService.selectByKeyArrayListPage(sql, page);
            }

			jsonObject = PageJsonUtil.toPageJson(page, keyContentList);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
        return jsonObject;
    }

}