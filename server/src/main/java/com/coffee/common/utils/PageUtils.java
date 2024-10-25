package com.coffee.common.utils;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 包含请求Page（分页结果）的通用代码，负责提供一个相关Page给Servlet再提供给前端
 * 
 * 由于Service层对Page对象进行了基本设置，但是没有设置当前页数，页面支持的个数等参数，所以需要一个工具类更方便地处理
 * 
 * @author K
 */
public class PageUtils {

	/**
	 * 非特征搜索的分页搜索
	 * 
	 * @param request
	 * @param response
	 * @param pageSize
	 * @param service
	 * @return Page<T>
	 * @throws SQLException
	 */
	public static <T> Page<T> getPage(HttpServletRequest request, int pageSize)  {
		int currentPage = getCurrentPage(request);
		int begin = (currentPage - 1) * pageSize; // 计算每页的开头

		return new Page<>(currentPage, pageSize);
	}


	/**
	 * 从request中提取要访问的页数,若没有参数，自动定位到第一页
	 * 
	 * @param request
	 * @return
	 */
	private static int getCurrentPage(HttpServletRequest request) {
		String currentPage = request.getParameter("currentPage");
		if (currentPage == null || isNum(currentPage) == false) {
			return 1;
		} else {
			return Integer.parseInt(currentPage);
		}
	}

	/**
	 * 使用正则验证输入的是否为数字
	 * 
	 * @param string
	 * @return
	 */
	private static boolean isNum(String string) {
		Pattern pattern = Pattern.compile("[1-9]{1}\\d*");
		Matcher matcher = pattern.matcher(string);
		return matcher.matches();
	}

	/**
	 * 这是request请求的URI,即"被请求"的方的地址
	 * 利用这个URL，可以在JSP中直接使用page.url来完成超链接，与使用完整路径来访问GetUsersPageServlet等是相同的功能,
	 * 但是这样做可以使得页面中的代码更有一致性
	 */
	private static String getURL(HttpServletRequest request) {
		String contextPath = request.getContextPath(); // 项目名
		String servletPath = request.getServletPath(); // servlet路径，即/*Servlet
														// 建议使用request.getRequestURI()获得一个包含项目名+Servlet的路径
		String queryString = request.getQueryString(); // ?后面的参数
		if (queryString == null) {
			queryString = "";
		}
		queryString = queryString.replaceAll("[&]{0,1}currentPage=(\\d+)*", "");
		return contextPath + servletPath + "?" + queryString;
	}
}
