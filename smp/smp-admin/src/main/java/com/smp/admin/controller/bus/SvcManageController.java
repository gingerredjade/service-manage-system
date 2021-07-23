package com.smp.admin.controller.bus;

import com.alibaba.fastjson.JSON;
import com.smp.admin.constant.enums.AuditTypeEnum;
import com.smp.admin.constant.enums.ReleaseTypeEnum;
import com.smp.admin.model.po.bus.svcinfo.BusGeoSvcInfoDO;
import com.smp.admin.model.po.bus.svcinfo.BusSvcStyleDO;
import com.smp.admin.model.po.bus.svcinfo.BusSvcSubjectDO;
import com.smp.admin.model.po.bus.svcinfo.BusSvcTypeDO;
import com.smp.admin.model.po.sys.SysDeptDO;
import com.smp.admin.service.iface.bus.BusGeoSvcInfoService;
import com.smp.admin.service.iface.bus.BusSvcStyleService;
import com.smp.admin.service.iface.bus.BusSvcSubjectService;
import com.smp.admin.service.iface.bus.BusSvcTypeService;
import com.smp.admin.service.iface.sys.SysDeptService;
import com.smp.admin.util.StatusUtil;
import com.smp.common.api.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;


/**
 * 地理信息服务信息 管理Controller
 * @author Hongyu Jiang
 * @since May. 12 2020
 */
@CrossOrigin
@Controller
@Api(tags = "服务信息管理", value = "SvcManageController",
	description = "支持服务信息的注册、查询、删除等")
@RequestMapping("/service")
public class SvcManageController {

	@Value(value = "${preread.upload-path}")
	String uploadPath;

	/**
	 * 采用构造器方式,注入Service层
	 */
	private final BusGeoSvcInfoService busGeoSvcInfoService;
	private final SysDeptService sysDeptService;
	private final BusSvcSubjectService svcSubjectService;
	private final BusSvcTypeService svcTypeService;
	private final BusSvcStyleService svcStyleService;
	@Autowired
	public SvcManageController(BusGeoSvcInfoService busGeoSvcInfoService,
							   SysDeptService sysDeptService,
							   BusSvcSubjectService svcSubjectService,
							   BusSvcTypeService svcTypeService,
							   BusSvcStyleService svcStyleService) {
		this.busGeoSvcInfoService = busGeoSvcInfoService;
		this.sysDeptService = sysDeptService;
		this.svcSubjectService = svcSubjectService;
		this.svcTypeService = svcTypeService;
		this.svcStyleService = svcStyleService;
	}



	@ApiOperation(value = "删除服务信息",
		notes = "删除已注册的服务信息")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public CommonResult delete(@PathVariable Long id) {
		boolean flag = busGeoSvcInfoService.deleteSvcInfo(id);
		if (flag) {
			return CommonResultUtil.success(flag);
		}
		return CommonResultUtil.failed();
	}

	@ApiOperation(value = "删除服务信息", notes = "删除服务信息")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult delete(@RequestBody List<BusGeoSvcInfoDO> records) {
		int resultFlag = busGeoSvcInfoService.delete(records);
		if (resultFlag == 0) {
			return CommonResultUtil.success(resultFlag);
		} else {
			return CommonResultUtil.failed();
		}
	}

	@ApiOperation(value = "获取注册的服务列表",
		notes = "分页获取已注册服务列表。支持如下查询条件:<br />" +
			"所属终端类型（编号）[terminal]、<br />" +
			"机构名称[deptName]、<br />" +
			"主题名称[svcSubjectName]、<br />" +
			"类型名称[svcTypeName]、<br />" +
			"服务名称[name]、<br />" +
			"服务别名[alias]、<br />" +
			"服务版本[version]、<br />" +
			"服务关键字[svcKeyword]、<br />" +
			"服务审核状态（编号）[auditState]、<br />" +
			"服务发布状态（编号）[releaseState] <br /><br />" +
			"其中的编号对应关系见【字典管理】<br />" +
			"【温馨提示】建议查询时指定查询已发布状态的服务信息。")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult<PageVO<BusGeoSvcInfoDO>> list(
		@RequestBody PageRequestVO pageRequest) {
		PageVO<BusGeoSvcInfoDO> curPage = busGeoSvcInfoService.getPageList(pageRequest);
		System.out.println(JSON.toJSONString(curPage));
		return CommonResultUtil.success(curPage);
	}


	@ApiOperation(value = "获取单个注册服务的详细信息",
		notes = "根据id获取已注册服务得详细信息")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public CommonResult<BusGeoSvcInfoDO> getItem(@PathVariable Long id) {
		BusGeoSvcInfoDO busGeoSvcInfoDO = busGeoSvcInfoService.getById(id);
		return CommonResultUtil.success(busGeoSvcInfoDO);
	}

	@ApiOperation(value = "获取注册的服务列表",
		notes = "获取全部已注册服务列表")
	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	@ResponseBody
	public CommonResult<List<BusGeoSvcInfoDO>> getAll() {
		List<BusGeoSvcInfoDO> list = busGeoSvcInfoService.listAll();
		return CommonResultUtil.success(list);
	}


	@ApiOperation(value = "注册服务信息",
		notes = "注册服务信息")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult save(
		//@ModelAttribute BusGeoSvcInfoDO busGeoSvcInfoDO,
		// 服务基础参数
		@ApiParam(name = "svcName", value = "服务名称")
		@RequestParam(value = "svcName", required = false) String svcName,

		@ApiParam(name = "svcAlias", value = "服务别名")
		@RequestParam(value = "svcAlias", required = false) String svcAlias,

		@ApiParam(name = "svcVersion", value = "服务版本")
		@RequestParam(value = "svcVersion", required = false) String svcVersion,

		@ApiParam(name = "svcDesc", value = "服务描述")
		@RequestParam(value = "svcDesc", required = false) String svcDesc,

		@ApiParam(name = "svcKeyword", value = "关键字")
		@RequestParam(value = "svcKeyword", required = false) String svcKeyword,

		@ApiParam(name = "homePageUrl", value = "服务主页URL")
		@RequestParam(value = "homePageUrl", required = false) String homePageUrl,

		@ApiParam(name = "interfaceSiteUrl", value = "服务接口网站URL")
		@RequestParam(value = "interfaceSiteUrl", required = false) String interfaceSiteUrl,

		@ApiParam(name = "previewUrl", value = "服务预览URL")
		@RequestParam(value = "previewUrl", required = false) String previewUrl,

		@ApiParam(name = "svcStyleId", value = "服务风格编号")
		@RequestParam(value = "svcStyleId", required = false) Long svcStyleId,

		@ApiParam(name = "deptId", value = "机构编号")
		@RequestParam(value = "deptId", required = false) Long deptId,

		@ApiParam(name = "svcSubjectId", value = "主题编号")
		@RequestParam(value = "svcSubjectId", required = false) Long svcSubjectId,

		@ApiParam(name = "svcTypeId", value = "类型编号")
		@RequestParam(value = "svcTypeId", required = false) Long svcTypeId,

		@ApiParam(name = "isGisSvc", value = "是否是地理信息服务")
		@RequestParam(value = "isGisSvc", required = false) Integer isGisSvc,

		@ApiParam(name = "terminal", value = "适用终端类型")
		@RequestParam(value = "terminal", required = false) Integer terminal,

		@ApiParam(name = "routingName", value = "服务路由指派名")
		@RequestParam(value = "routingName", required = false) String routingName,


		// 地理信息扩展相关参数
		@ApiParam(name = "svcCoverage", value = "覆盖范围")
		@RequestParam(value = "svcCoverage", required = false) String svcCoverage,

		@ApiParam(name = "svcServiceArea", value = "服务范围")
		@RequestParam(value = "svcServiceArea", required = false) String svcServiceArea,

		@ApiParam(name = "svcCoordinateSystem", value = "坐标系")
		@RequestParam(value = "svcCoordinateSystem", required = false) String svcCoordinateSystem,

		@ApiParam(name = "svcProjectionType", value = "投影类型")
		@RequestParam(value = "svcProjectionType", required = false) String svcProjectionType,

		@ApiParam(name = "svcUpdateCycle", value = "更新周期")
		@RequestParam(value = "svcUpdateCycle", required = false) String svcUpdateCycle,


		// 地理信息图层相关参数
		@ApiParam(name = "layerName", value = "图层名称")
		@RequestParam(value = "layerName", required = false) String layerName,

		@ApiParam(name = "layerDesc", value = "图层简介")
		@RequestParam(value = "layerDesc", required = false) String layerDesc,

		@ApiParam(name = "layerKeyword", value = "图层关键字")
		@RequestParam(value = "layerKeyword", required = false) String layerKeyword,

		@ApiParam(name = "layerCoverage", value = "图层覆盖范围")
		@RequestParam(value = "layerCoverage", required = false) String layerCoverage,

		@ApiParam(name = "layerServiceArea", value = "图层数据范围")
		@RequestParam(value = "layerServiceArea", required = false) String layerServiceArea,

		@ApiParam(name = "layerCoordinateSystem", value = "图层坐标系")
		@RequestParam(value = "layerCoordinateSystem", required = false) String layerCoordinateSystem,

		@ApiParam(name = "layerProjectionType", value = "图层投影类型")
		@RequestParam(value = "layerProjectionType", required = false) String layerProjectionType,

		@ApiParam(name = "layerUpdateCycle", value = "图层更新周期")
		@RequestParam(value = "layerUpdateCycle", required = false) String layerUpdateCycle,

		@ApiParam(name = "layerUpdateTime", value = "图层最新更新时间")
		@RequestParam(value = "layerUpdateTime", required = false) Date layerUpdateTime,


		@ApiParam(name = "multipartFiles", value = "预览图/缩略图")
		@RequestParam(value = "thumbfile", required = false) MultipartFile[] multipartFiles,
		HttpServletRequest request) {

		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		ZoneId zoneId = ZoneId.systemDefault();
		LocalDateTime localDateTime = LocalDateTime.now();
		ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);

		BusGeoSvcInfoDO busGeoSvcInfoDO = null;

		String svcId = request.getParameter("svcId");
		if (svcId == null || svcId.length() == 0) {

			/*
			 * 若注册的服务如下内容相同,则拒绝注册
			 * - 服务适用终端类型、服务所属机构、服务主题、服务类型、服务名称、服务版本
			 */
			List<BusGeoSvcInfoDO> hasExist = busGeoSvcInfoService.listByConditions(terminal, deptId,
				svcSubjectId, svcTypeId, svcName, svcVersion);
			if (hasExist != null && hasExist.size() !=0) {
				return CommonResultUtil.failed("违反服务唯一性！【服务适用终端类型、服务所属机构、服务主题、服务类型、服务名称、服务版本】均相同，不允许重复注册");
			} else {
				busGeoSvcInfoDO = new BusGeoSvcInfoDO();
				busGeoSvcInfoDO.setCreateBy(userName);
				busGeoSvcInfoDO.setCreateTime(Date.from(zonedDateTime.toInstant()));
			}
		} else {
			busGeoSvcInfoDO = busGeoSvcInfoService.getById(Long.valueOf(svcId));
			if (busGeoSvcInfoDO == null) {
				// 新增
				busGeoSvcInfoDO = new BusGeoSvcInfoDO();
				busGeoSvcInfoDO.setCreateBy(userName);
				busGeoSvcInfoDO.setCreateTime(Date.from(zonedDateTime.toInstant()));
			}
		}

		busGeoSvcInfoDO.setLastUpdateBy(userName);
		busGeoSvcInfoDO.setLastUpdateTime(Date.from(zonedDateTime.toInstant()));

		/*
		 * 1-- 服务基础参数
		 */
		if (svcName != null && svcName.length() != 0) {
			busGeoSvcInfoDO.setSvcName(svcName);
		}
		if (svcAlias != null && svcAlias.length() != 0) {
			busGeoSvcInfoDO.setSvcAlias(svcAlias);
		}
		if (svcVersion != null && svcVersion.length() != 0) {
			busGeoSvcInfoDO.setSvcVersion(svcVersion);
		}
		if (svcDesc != null && svcDesc.length() != 0) {
			busGeoSvcInfoDO.setSvcDesc(svcDesc);
		}
		if (svcKeyword != null && svcKeyword.length() != 0) {
			busGeoSvcInfoDO.setSvcKeyword(svcKeyword);
		}

		busGeoSvcInfoDO.setHomePageUrl(homePageUrl);
		busGeoSvcInfoDO.setInterfaceSiteUrl(interfaceSiteUrl);
		busGeoSvcInfoDO.setPreviewUrl(previewUrl);
		busGeoSvcInfoDO.setRoutingName(routingName);

		busGeoSvcInfoDO.setIsGisSvc(isGisSvc);
		busGeoSvcInfoDO.setTerminal(terminal);

		// 设置服务风格编号
		if (svcStyleId != null) {
			BusSvcStyleDO svcStyleDO = svcStyleService.getById(svcStyleId);
			busGeoSvcInfoDO.setSvcStyleDO(svcStyleDO);
		}

		// 设置部门编号、主题编号、类型编号
		if (deptId != null) {
			SysDeptDO deptDO = sysDeptService.getById(deptId);
			busGeoSvcInfoDO.setSysDeptDO(deptDO);
		}
		if (svcTypeId != null) {
			BusSvcTypeDO svcType = svcTypeService.getById(svcTypeId);
			if (svcSubjectId != null) {
				BusSvcSubjectDO svcSubject = svcSubjectService.getById(svcSubjectId);
				if (svcSubject != null) {
					svcType.setBusSvcSubjectDO(svcSubject);
				}
			}
			busGeoSvcInfoDO.setSvcTypeDO(svcType);
		}

		// 属于地理信息服务时设定
		/*if (isGisSvc == 1) {*/
		if (true) {
			/*
			 * 2-- 地理信息扩展相关参数（isGisSvc==1时）
			 */
			busGeoSvcInfoDO.setSvcCoverage(svcCoverage);
			busGeoSvcInfoDO.setSvcServiceArea(svcServiceArea);
			busGeoSvcInfoDO.setSvcCoordinateSystem(svcCoordinateSystem);
			busGeoSvcInfoDO.setSvcProjectionType(svcProjectionType);
			busGeoSvcInfoDO.setSvcUpdateCycle(svcUpdateCycle);


			/*
			 * 3-- 地理信息图层相关参数（isGisSvc==1时）
			 */
			busGeoSvcInfoDO.setLayerName(layerName);
			busGeoSvcInfoDO.setLayerDesc(layerDesc);
			busGeoSvcInfoDO.setLayerKeyword(layerKeyword);
			busGeoSvcInfoDO.setLayerCoverage(layerCoverage);
			busGeoSvcInfoDO.setLayerServiceArea(layerServiceArea);
			busGeoSvcInfoDO.setLayerCoordinateSystem(layerCoordinateSystem);
			busGeoSvcInfoDO.setLayerProjectionType(layerProjectionType);
			busGeoSvcInfoDO.setLayerUpdateCycle(layerUpdateCycle);
			busGeoSvcInfoDO.setLayerUpdateTime(layerUpdateTime);
		}

		/*
		 * 4-- 预览图处理，将预览图存储路径存到库里
		 */
		if (multipartFiles.length > 0) {
			String thumb = uploadThumbnails(multipartFiles, request);
			busGeoSvcInfoDO.setThumbnails(thumb);
		}

		int flag = busGeoSvcInfoService.save(busGeoSvcInfoDO);
		if (flag == 0) {
			return CommonResultUtil.success(null, "注册成功");
		}
		return CommonResultUtil.failed();
	}


	/**
	 * 上传文件到服务器，并返回文件存储的路径
	 * @param multipartFiles 多媒体文件列表
	 * @param request request
	 * @return 文件存储的路径
	 */
	private String uploadThumbnails(MultipartFile[] multipartFiles, HttpServletRequest request) {
		String thumn = "";					// 以”,“分割拼接文件的地址
		int len = multipartFiles.length;	// 文件的个数
		try {
			for (int i=0; i < len; i++) {
				// 获取文件的原始名称（带格式）
				String originalFileName = multipartFiles[i].getOriginalFilename();
				// 获取文件的类型
				String type = originalFileName.substring(originalFileName.lastIndexOf(".")+1);
				// 获取文件名（不带格式）
				String name = originalFileName.substring(0,originalFileName.lastIndexOf("."));

				// 生成新的文件名
				String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
				String fileName = name + "." + type;

				// 在指定路径下创建一个文件
				String dateDir = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + File.separator;

				// 检测上传目录是否存在，否则建立
				File fileDestPath = new File(uploadPath + dateDir);
				if (!fileDestPath.exists() && !fileDestPath.isDirectory()) {
					// 目标路径不存在，需要创建
					fileDestPath.mkdirs();
				}

				File targetFile = new File(fileDestPath + File.separator + fileName);
				// 将文件保存到服务器
				multipartFiles[i].transferTo(targetFile);

				// 拼接文件地址，支持存储同时上传的多个(http://localhost:8998/preview/2020-05-23/QQ浏览器截图20200228200344.png)
				if(i<len-1){
					thumn += File.separator +"preview" + File.separator + dateDir + fileName + ",";
				}else{
					thumn += File.separator + "preview" + File.separator + dateDir + fileName;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return thumn;
	}

	@ApiOperation(value = "设置服务审核状态",
		notes = "设置一条数据的状态")
	@RequestMapping(value = "/status/audit/{param}", method = RequestMethod.GET)
	@ResponseBody
	public CommonResult<String> updateAuditStatus(@PathVariable String param,
											 @RequestParam(value="id", required = false) Long id,
											 @RequestParam(value="auditOpinion", required = false) String auditOpinion) {
		// 更新审核状态、审核意见
		AuditTypeEnum auditTypeEnum = StatusUtil.getAuditTypeEnum(param);
		if (busGeoSvcInfoService.updateAuditStatusAndOpinion(auditTypeEnum, id, auditOpinion)) {
			return CommonResultUtil.success(auditTypeEnum.getMessage() + "成功");
		} else {
			return CommonResultUtil.failed(auditTypeEnum.getMessage() + "失败，请重新操作");
		}
	}

	@ApiOperation(value = "设置服务发布状态",
		notes = "设置一条或多条数据的发布状态")
	@RequestMapping(value = "/status/release/{param}", method = RequestMethod.GET)
	@ResponseBody
	public CommonResult<String> updateReleaseStatus(@PathVariable String param,
												  @RequestParam(value="ids", required = false) List<Long> ids) {
		// 更新发布状态
		ReleaseTypeEnum releaseTypeEnum = StatusUtil.getReleaseTypeEnum(param);
		if (busGeoSvcInfoService.updateReleaseStatus(releaseTypeEnum, ids)) {
			return CommonResultUtil.success(releaseTypeEnum.getMessage() + "成功");
		} else {
			return CommonResultUtil.failed(releaseTypeEnum.getMessage() + "失败，请重新操作");
		}
	}

	@ApiOperation(value = "查询全部服务目录",
		notes = "查询全部服务目录")
	@RequestMapping(value = "/catalog/all", method = RequestMethod.GET)
	@ResponseBody
	public CommonResult<List> getServiceCatalog() {
		List list = busGeoSvcInfoService.getCatalogAll();
		return CommonResultUtil.success(list);
	}

	@ApiOperation(value = "根据条件查询服务信息",
		notes = "支持根据以下条件查询服务信息:<br />" +
			"所属终端类型（编号）[terminal]、<br />" +
			"机构名称[deptName]、<br />" +
			"主题名称[svcSubjectName]、<br />" +
			"类型名称[svcTypeName]、<br />" +
			"服务名称[name]、<br />" +
			"服务别名[alias]、<br />" +
			"服务版本[version]、<br />" +
			"服务关键字[svcKeyword]、<br />" +
			"服务审核状态（编号）[auditState]、<br />" +
			"服务发布状态（编号）[releaseState] <br /><br />" +
			"其中的编号对应关系见【字典管理】<br />" +
			"【温馨提示】建议查询时指定查询已发布状态的服务信息。")
	@RequestMapping(value = "/filter", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult<List<BusGeoSvcInfoDO>> filter(
		@RequestBody UniversalRequestVO requestVO) {
		List<BusGeoSvcInfoDO> list = busGeoSvcInfoService.getList(requestVO);
		return CommonResultUtil.success(list);
	}


}
