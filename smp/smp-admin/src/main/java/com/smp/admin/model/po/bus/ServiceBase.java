package com.smp.admin.model.po.bus;

import com.smp.admin.model.po.bus.svcinfo.BusSvcStyleDO;
import com.smp.admin.model.po.bus.svcinfo.BusSvcTypeDO;
import com.smp.admin.model.po.sys.SysDeptDO;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 服务信息基础模型
 * @author Hongyu Jiang
 * @since  Apr. 26 2020
 */
@MappedSuperclass
public class ServiceBase implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id								// 标识该属性为主键
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long svcId;				// 编号

	@Column
	private String createBy;		// 创建人

	@Column
	//@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;		// 创建时间

	@Column
	private String  lastUpdateBy;	// 更新人

	@Column
	//@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateTime;	// 更新时间

	@Column
	private String svcName;				// 服务名称

	@Column
	private String svcAlias;			// 服务别名

	@Column
	private String svcVersion;			// 服务版本

	@Column
	private String svcSubType;			// 服务子类型

	@Column
	private String svcDesc;				// 服务描述/简介

	@Column
	private String svcKeyword;			// 关键字

	@Column
	private String homePageUrl;			// 服务主页URL

	@Column
	private String interfaceSiteUrl;	// 服务接口网站URL

	@Column
	private String previewUrl;			// 服务预览URL

	@ManyToOne
	@JoinColumn(name = "styleId")
	private BusSvcStyleDO svcStyleDO;	// 服务风格


	/**
	 * 所属机构信息
	 * 	注意：此处的id是SysDeptDO的主键id
	 */
	@ManyToOne
	@JoinColumn(name = "id")
	private SysDeptDO sysDeptDO;

	@ManyToOne
	@JoinColumn(name = "typeId")
	private BusSvcTypeDO svcTypeDO;		// 服务类型（类型可追溯到所属主题）

	@Column
	private String thumbnails;			// 服务缩略图

	@Column
	private Integer isGisSvc = 0;		// 是否是地理信息服务（1:地理信息服务,0:非地理信息服务）

	@Column
	private String perms;				// 使用权限(公开, 授权) --暂不提供该功能

	@Column
	private Integer terminal;			// 适用终端类型（1:通用,2:Web端,3:桌面端）

	@Column
	private String routingName;			// 服务路由指派名,供前端分发路由使用(如:Navi)


	public Long getSvcId() {
		return svcId;
	}

	public void setSvcId(Long svcId) {
		this.svcId = svcId;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getSvcName() {
		return svcName;
	}

	public void setSvcName(String svcName) {
		this.svcName = svcName;
	}

	public String getSvcAlias() {
		return svcAlias;
	}

	public void setSvcAlias(String svcAlias) {
		this.svcAlias = svcAlias;
	}

	public String getSvcVersion() {
		return svcVersion;
	}

	public void setSvcVersion(String svcVersion) {
		this.svcVersion = svcVersion;
	}

	public String getSvcSubType() {
		return svcSubType;
	}

	public void setSvcSubType(String svcSubType) {
		this.svcSubType = svcSubType;
	}

	public String getSvcDesc() {
		return svcDesc;
	}

	public void setSvcDesc(String svcDesc) {
		this.svcDesc = svcDesc;
	}

	public String getSvcKeyword() {
		return svcKeyword;
	}

	public void setSvcKeyword(String svcKeyword) {
		this.svcKeyword = svcKeyword;
	}

	public String getHomePageUrl() {
		return homePageUrl;
	}

	public void setHomePageUrl(String homePageUrl) {
		this.homePageUrl = homePageUrl;
	}

	public String getInterfaceSiteUrl() {
		return interfaceSiteUrl;
	}

	public void setInterfaceSiteUrl(String interfaceSiteUrl) {
		this.interfaceSiteUrl = interfaceSiteUrl;
	}

	public String getPreviewUrl() {
		return previewUrl;
	}

	public void setPreviewUrl(String previewUrl) {
		this.previewUrl = previewUrl;
	}

	public SysDeptDO getSysDeptDO() {
		return sysDeptDO;
	}

	public void setSysDeptDO(SysDeptDO sysDeptDO) {
		this.sysDeptDO = sysDeptDO;
	}

	public BusSvcTypeDO getSvcTypeDO() {
		return svcTypeDO;
	}

	public void setSvcTypeDO(BusSvcTypeDO svcTypeDO) {
		this.svcTypeDO = svcTypeDO;
	}

	public String getPerms() {
		return perms;
	}

	public void setPerms(String perms) {
		this.perms = perms;
	}

	public String getThumbnails() {
		return thumbnails;
	}

	public void setThumbnails(String thumbnail) {
		this.thumbnails = thumbnail;
	}

	public Integer getIsGisSvc() {
		return isGisSvc;
	}

	public void setIsGisSvc(Integer isGisSvc) {
		this.isGisSvc = isGisSvc;
	}

	public Integer getTerminal() {
		return terminal;
	}

	public void setTerminal(Integer terminal) {
		this.terminal = terminal;
	}

	public String getRoutingName() {
		return routingName;
	}

	public void setRoutingName(String routingName) {
		this.routingName = routingName;
	}

	public BusSvcStyleDO getSvcStyleDO() {
		return svcStyleDO;
	}

	public void setSvcStyleDO(BusSvcStyleDO svcStyleDO) {
		this.svcStyleDO = svcStyleDO;
	}
}
