package com.smp.admin.model.po.bus.svcinfo;

import com.smp.admin.constant.enums.StatusEnum;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 服务类型模型
 * 	服务类型属于某一主题
 *
 * @author Hongyu Jiang
 * @since  May. 16 2020
 */
@Entity
@Table(name = "bus_svc_type")
@EntityListeners(AuditingEntityListener.class)
public class BusSvcTypeDO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id                            	// 标识该属性为主键
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long typeId;			// 编号

	@Column
	private String name;			// 类型名称

	@Column
	private Byte delFlag = StatusEnum.OK.getCode();		// 是否删除(3：已删除, 1：正常)

	@ManyToOne
	@JoinColumn(name = "subjectId")
	private BusSvcSubjectDO busSvcSubjectDO;

	@Column
//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;		// 创建时间

	@Column
//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateTime;	// 更新时间

	@Column
	private String createBy;		// 创建人

	@Column
	private String  lastUpdateBy;	// 更新人

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Byte getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Byte delFlag) {
		this.delFlag = delFlag;
	}

	public BusSvcSubjectDO getBusSvcSubjectDO() {
		return busSvcSubjectDO;
	}

	public void setBusSvcSubjectDO(BusSvcSubjectDO busSvcSubjectDO) {
		this.busSvcSubjectDO = busSvcSubjectDO;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
}
