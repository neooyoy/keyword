package com.keyword.controller.keycontent;

import com.alibaba.fastjson.JSONObject;
import com.keyword.domain.keycontent.KeyContent;
import com.keyword.service.keycontent.KeyContentService;
import com.keyword.utils.ExcelUtil;
import com.keyword.utils.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller 
@RequestMapping("/keycontentController")
public class KeyContentController {

    protected Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private KeyContentService keyContentService;

    @RequestMapping("/importExcel")
    public String importHouse(Model model) {
        model.addAttribute("active", "excel");
        model.addAttribute("currentMenu_type", 1);
        model.addAttribute("currentMenu_parentName", "数据管理");
        model.addAttribute("currentMenu_menuName", "导入数据");
        return "system/importExcel";
    }



    /**
     * 导入excel数据前删除数据
     *
     * @return
     */
    @RequestMapping("/deleteOldData")
    @ResponseBody
    public JSONObject deleteOldData() {
        JSONObject json = new JSONObject();
        try {

            Integer total = this.keyContentService.deleteAll();

            json.put("total", total);
            json.put("success", true);
            json.put("message", "成功");

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            json.put("success", false);
            json.put("message", "错误");
        }
        return json;
    }

    /**
     * 导入excel数据
     *
     * @param files
     * @param request
     * @return
     */
    @RequestMapping("/doImportExcel")
    @ResponseBody
    public JSONObject importHouseExcel(@RequestParam("files") MultipartFile[] files, HttpServletRequest request) {
        JSONObject json = new JSONObject();
        try {

            List<KeyContent> keyContentList = new ArrayList<>();

            String keywords = "";

            if (files != null && files.length > 0) {
                for (int i = 0; i < files.length; i++) {
                    String filePath = saveFile(files[i], request);
                    if (filePath != null) {
                        Map<String, List<String>> keyListMap = ExcelUtil.readExcel(filePath);

                        System.out.print(filePath);

                        for (String key : keyListMap.keySet()) {

                            keywords += "," + "\'" + key + "\'" + ",";

                            List<String> contentList = keyListMap.get(key);
                            if (!contentList.isEmpty()) {
                                for (String content : contentList) {
                                    KeyContent keyContent = new KeyContent();
                                    keyContent.setKeyword(key);
                                    keyContent.setContent(content);
                                    keyContentList.add(keyContent);
                                }
                            }
                        }
                    }
                }
            }

            /*if (!keywords.isEmpty()) {
                keywords = keywords.substring(1, keywords.length()-1);
                this.keyContentService.deleteByKeywords(keywords);
            }*/

            //保存数据
            batchInsert(keyContentList);

            json.put("success", true);
            json.put("total", keyContentList.size());
            json.put("message", "文件导入成功");

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            json.put("success", false);
            json.put("message", "文件上传错误");
        }
        return json;
    }

    /**
     * 批量插入
     *
     * @param keyContentList
     */
    private void batchInsert(List<KeyContent> keyContentList) throws Exception {
        if (!keyContentList.isEmpty()) {
            if (keyContentList.size() > 1000) {

                this.keyContentService.batchInsertKeyContents(keyContentList);

            } else {
                for (int i = 0; i < keyContentList.size(); ) {
                    int endIndex = keyContentList.size() > (i + 1000) ? (i + 1000) : keyContentList.size();

                    List<KeyContent> curKeyContents = keyContentList.subList(i, endIndex);

                    this.keyContentService.batchInsertKeyContents(curKeyContents);

                    i = endIndex;
                }
            }
        }
    }


    /***
     * 保存文件
     *
     * @param file
     * @return
     */
    private String saveFile(MultipartFile file, HttpServletRequest request) {
        // 判断文件是否为空
        if (!file.isEmpty()) {
            try {
                // 文件保存路径
                String filePath = request.getSession().getServletContext().getRealPath("/") + "resources/upload/"
                        + file.getOriginalFilename();
                // 转存文件
                file.transferTo(new File(filePath));
                return filePath;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}

