package com.smp.admin.controller.bus;

import com.smp.admin.model.vo.ServiceCountVO;
import com.smp.admin.model.vo.ServiceStatVO;
import com.smp.admin.service.iface.bus.BusSvcStatService;
import com.smp.common.api.CommonResult;
import com.smp.common.api.CommonResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 服务统计 管理Controller
 * @author Hongyu Jiang
 * @since Sep. 7 2020
 */
@CrossOrigin
@Controller
@Api(tags = "服务统计管理", value = "SvcStatController",
	description = "支持服务的统计查询")
@RequestMapping("/stat")
public class SvcStatController {

	/**
	 * 采用构造器方式,注入Service层
	 */
	private final BusSvcStatService busSvcStatService;
	@Autowired
	public SvcStatController(BusSvcStatService busSvcStatService) {
		this.busSvcStatService = busSvcStatService;
	}

	@ApiOperation(value = "统计服务主题对应的服务数量",
		notes = "统计服务主题对应的服务数量")
	@RequestMapping(value = "/subject", method = RequestMethod.GET)
	@ResponseBody
	public CommonResult statBySubject() {
		ServiceStatVO result = busSvcStatService.statBySubject();
		return CommonResultUtil.success(result);
	}

	@ApiOperation(value = "统计服务类型对应的服务数量",
		notes = "统计服务类型对应的服务数量")
	@RequestMapping(value = "/type", method = RequestMethod.GET)
	@ResponseBody
	public CommonResult statByType() {
		ServiceStatVO result = busSvcStatService.statByType();
		return CommonResultUtil.success(result);
	}

	@ApiOperation(value = "统计机构对应的服务数量",
		notes = "统计机构对应的服务数量")
	@RequestMapping(value = "/dept", method = RequestMethod.GET)
	@ResponseBody
	public CommonResult statByDept() {
		ServiceStatVO result = busSvcStatService.statByDept();
		return CommonResultUtil.success(result);
	}

	@ApiOperation(value = "统计终端对应的服务数量",
		notes = "统计终端对应的服务数量")
	@RequestMapping(value = "/terminal", method = RequestMethod.GET)
	@ResponseBody
	public CommonResult statByTerminal() {
		ServiceStatVO result = busSvcStatService.statByTerminal();
		return CommonResultUtil.success(result);
 	}

	@ApiOperation(value = "统计GIS服务或者非GIS服务对应服务数量",
		notes = "统计GIS服务或者非GIS服务对应服务数量")
	@RequestMapping(value = "/gis", method = RequestMethod.GET)
	@ResponseBody
	public CommonResult statByGisOrNo() {
		ServiceStatVO result = busSvcStatService.statByGisOrNo();
		return CommonResultUtil.success(result);
	}

	@ApiOperation(value = "统计服务数量",
		notes = "针对服务的发布状态、审核状态统计对应服务数量")
	@RequestMapping(value = "/countByState", method = RequestMethod.GET)
	@ResponseBody
	public CommonResult countSvcByState() {
		ServiceCountVO scVO = new ServiceCountVO();

		Long grossNumber = busSvcStatService.countSvcGross();
		Long releasedNumber = busSvcStatService.countSvcByReleased();
		Long unreleasedNumber = busSvcStatService.countSvcByUnreleased();
		Long auditedPassNumber = busSvcStatService.countSvcByAuditedPass();
		Long unAuditedNumber = busSvcStatService.countSvcByUnaudited();

		scVO.setGrossNumber(grossNumber);
		scVO.setReleasedNumber(releasedNumber);
		scVO.setUnReleasedNumber(unreleasedNumber);
		scVO.setAuditedPassNumber(auditedPassNumber);
		scVO.setUnAuditedNumber(unAuditedNumber);

		return CommonResultUtil.success(scVO);
	}

}
