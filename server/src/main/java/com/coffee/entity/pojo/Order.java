package com.coffee.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * JavaBean:PO，字段对应order表
 *
 * @author K
 */
@TableName("`order`")
public class Order {
	@TableId(type = IdType.AUTO)
	private int orderId;
	private Integer userId;
	private Double orderTotal;
	private String status;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createdTime;
	private String address;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getUserId() {
		return userId;
	}

	public void setOrderTotal(double orderTotal) {
		this.orderTotal = orderTotal;
	}

	public double getOrderTotal() {
		return orderTotal;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ",userId=" + userId + ",orderTotal=" + orderTotal + ",createdTime="
				+ createdTime + "]";
	}

}
