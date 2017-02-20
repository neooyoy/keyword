package com.keyword.controller.keyword;

import com.keyword.domain.banner.Banner;
import com.keyword.domain.office.Office;
import com.keyword.domain.office.OfficeImgs;
import com.keyword.domain.partners.Partners;
import com.keyword.domain.servicehotline.Servicehotline;
import com.keyword.mybatis.Paging;
import com.keyword.service.banner.BannerService;
import com.keyword.service.office.OfficeImgsService;
import com.keyword.service.office.OfficeService;
import com.keyword.service.partners.PartnersService;
import com.keyword.service.servicehotline.ServicehotlineService;
import com.keyword.utils.Logger;
import com.keyword.vo.JsonObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
* Created by ZDD on 2016/6/28.
		*/
@Controller
@RequestMapping("/")
public class KeywordController {

	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 详情
	 *
	 * @param id
	 * @return
     */
	@RequestMapping("/detail")
	public String foregroundDetail(Integer id) {
		return "keyword/detail";
	}

	/**
	 * 列表
	 *
	 * @return
	 * @author chenjun 20160628
	 */
	@RequestMapping("list")
	public String list() {
		return "keyword/list";
	}

	/**
	 * 列表查询
	 *
	 * @return
	 */
	@RequestMapping("querylist")
	@ResponseBody
	public JsonObject querylist(Office office, Paging page) {
		JsonObject jsonObject = new JsonObject();
	/*	try {
			office.setStatus(1);
			List<Office> officeList = this.officeService
					.selectBuildingsListPage(office, page);
			jsonObject = PageJsonUtil.toPageJson(page, officeList);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}*/
		return jsonObject;
	}

}