package com.smp.common.api;

import java.io.Serializable;
import java.util.List;

/**
 * 分页数据封装类
 * 	通用，适合未集成MyBatis的场情况。
 *
 * Created by JHy on 2020/4/3.
 */
public class PageVO<T> implements Serializable {

    private List<T> list;				// list result of this page(分页数据)
    private Integer pageNum;			// page number(当前页码)
    private Integer pageSize;			// result amount of this page(每页数量)
    private Integer totalPage;			// total page(页码总数)
    private Long totalRow;				// total row(记录总数)


	public PageVO(List<T> list, Integer pageNum, Integer pageSize, Integer totalPage, Long totalRow) {
		this.list = list;
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.totalPage = totalPage;
		this.totalRow = totalRow;
	}

	public PageVO() {
    }

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Long getTotalRow() {
		return totalRow;
	}

	public void setTotalRow(Long totalRow) {
		this.totalRow = totalRow;
	}
}
