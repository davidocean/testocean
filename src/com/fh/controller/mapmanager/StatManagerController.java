package com.fh.controller.mapmanager;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.ms.res.MsResServicesClass;
import com.fh.entity.ms.services.MsServicesReg;
import com.fh.entity.system.Department;
import com.fh.service.fhoa.department.DepartmentManager;
import com.fh.service.ms.res.MsResServicesClassService;
import com.fh.service.ms.services.MsServicesRegService;
import com.fh.service.ms.stat.MsStatServices;
import com.fh.util.PageData;
import com.fh.util.SortUtil;
import com.mapone.MsConfigProperties;
//import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

/**
 * 运维管理
 * 
 * @author mwhdds
 * @date 2016-11-11
 */
@Controller
@RequestMapping(value = "/statis")
public class StatManagerController extends BaseController {
	
	// fujl 2016-11-19 注入查询配置服务
	@Resource(name = "msStatServices")
	private MsStatServices msStatServices;
	
	@Resource(name = "msResServicesClassService")
	private MsResServicesClassService msResServicesClassService;
	
	@Resource(name = "msServicesRegService")
	private MsServicesRegService msServicesRegService;
	
	@Resource(name = "departmentService")
	private DepartmentManager departmentService;
	
	/**
	 * 服务统计
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/servicestat")
	public ModelAndView servicestat(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		
		// toc id 获得参数
		String toc_selected_id = pd.getString("toc_selected_id");// 服务目录
		List<Integer> toc_selected_id_list = new ArrayList<>();
		if (StringUtils.isEmpty(toc_selected_id) || "0".equals(toc_selected_id)) {
			toc_selected_id = "0";// 全部 无此classID
		}
		toc_selected_id_list.add(Integer.parseInt(toc_selected_id));
		
		// 查询所有的子节点
		if (!"0".equals(toc_selected_id)) {
			List<MsResServicesClass> list = msResServicesClassService.listAll(new MsResServicesClass());
			retrieveChild(Integer.parseInt(toc_selected_id), list, toc_selected_id_list);
		}
		pd.put("toc_selected_id", toc_selected_id);
		pd.put("toc_selected_id_list", toc_selected_id_list);
		
		// service _status 获得参数
		String service_status = pd.getString("service_status");// 安全 、 自由服务
		if (StringUtils.isEmpty(service_status)) {
			service_status = "3";// 全部
		}
		pd.put("service_status", service_status);
		
		// time
		String timestart = pd.getString("timestart"); // 开始时间
		String timeend = pd.getString("timeend"); // 结束时间
		if (timestart != null && !"".equals(timestart)) {
			pd.put("timestart", timestart + " 00:00:00");
		}
		if (timeend != null && !"".equals(timeend)) {
			pd.put("timeend", timeend + " 23:59:59");
		}
		
		List<PageData> varList = msStatServices.listStatServices(pd);
		List<MsServicesReg> sList = msServicesRegService.listAll(new MsServicesReg());
		
		// 根据比率 计算 访问量
		for (PageData _pd : varList) {
			String sname = _pd.getString("servicename");
			long accesscount = Long.parseLong(_pd.get("accesscount") + "");
			
			for (MsServicesReg mr : sList) {
				if (sname.equals(mr.getServicename()) && "tiled".equals(mr.getServicecache())) {
					_pd.put("accesscount", accesscount / Long.parseLong(MsConfigProperties.TILED_SERVICE_RATE));
					break;
				}
			}
			
		}
		
		String strServiceName = "";
		String strNumber = "";
		for (PageData pd2 : varList) {
			strServiceName += "'" + pd2.get("servicename") + "',";
			strNumber += pd2.get("accesscount") + ",";
		}
		
		if (strServiceName != "") {
			strServiceName = strServiceName.substring(0, strServiceName.length() - 1);
		}
		
		if (strNumber != "") {
			strNumber = strNumber.substring(0, strNumber.length() - 1);
		}
		
		mv.addObject("varList", varList);
		mv.addObject("axisx", strServiceName);
		mv.addObject("axisy", strNumber);
		
		List<MsResServicesClass> classList = msResServicesClassService.listRoot(new MsResServicesClass());
		classList.add(0, new MsResServicesClass("0", "全部"));
		mv.addObject("toc", classList);
		mv.addObject("toc_selected_id", classList);
		mv.addObject("pd", pd);
		page.setPd(pd);
		
		mv.setViewName("mapmanager/statis/servicestat");
		return mv;
	}
	
	/**
	 * 用户统计
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/userstat")
	public ModelAndView userstat() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		
		// 参数 部门
		String dept_selected_id = pd.getString("dept_selected_id");// 组织机构目录
		List<String> dept_selected_id_list = new ArrayList<String>();
		if (StringUtils.isEmpty(dept_selected_id) || "0".equals(dept_selected_id)) {
			dept_selected_id = "0";// 全部 无此classID
		}
		dept_selected_id_list.add(dept_selected_id);
		
		if (!"0".equals(dept_selected_id)) {
			List<Department> list = departmentService.listAllDept();
			retrieveChildDept(dept_selected_id, list, dept_selected_id_list);
		}
		pd.put("dept_selected_id", dept_selected_id);
		pd.put("dept_selected_id_list", dept_selected_id_list);
		
		// 参数 时间 time
		String timestart = pd.getString("timestart"); // 开始时间
		String timeend = pd.getString("timeend"); // 结束时间
		if (timestart != null && !"".equals(timestart)) {
			pd.put("timestart", timestart + " 00:00:00");
		}
		if (timeend != null && !"".equals(timeend)) {
			pd.put("timeend", timeend + " 23:59:59");
		}
		
		// 获取所有查询配置服务 fujl 2016-11-20 ++
		List<PageData> varList = msStatServices.listStatUsers(pd);
		List<MsServicesReg> sList = msServicesRegService.listAll(new MsServicesReg());
		
		// 用户统计 访问总量
		Map<String, Long> sumMap = new HashMap<String, Long>();
		
		for (PageData _pd : varList) {
			// 根据比率 计算 访问量
			String sname = _pd.getString("servicename");
			long accesscount = Long.parseLong(_pd.get("accesscount") + "");
			for (MsServicesReg mr : sList) {
				if (sname.equals(mr.getServicename()) && "tiled".equals(mr.getServicecache())) {
					_pd.put("accesscount", accesscount / Long.parseLong(MsConfigProperties.TILED_SERVICE_RATE));
					break;
				}
			}
			
			// 计算求和
			String name = _pd.getString("department_id") + "_" + _pd.getString("department_name");
			if (sumMap.containsKey(name)) {
				sumMap.put(name, sumMap.get(name) + Long.parseLong(_pd.get("accesscount") + ""));
			} else {
				sumMap.put(name, Long.parseLong(_pd.get("accesscount") + ""));
			}
		}
		
		varList = new ArrayList<PageData>();
		for (Map.Entry<String, Long> entry : sumMap.entrySet()) {
			String sname = entry.getKey();
			Long accesscount = entry.getValue();
			PageData _pd = new PageData();
			_pd.put("department_id", sname.split("_")[0]);
			_pd.put("department_name", sname.split("_")[1]);
			_pd.put("accesscount", accesscount);
			varList.add(_pd);
		}
		
		varList = SortUtil.sort(varList, "accesscount", "asc");
		
		String strName = "";
		String strNumber = "";
		for (PageData pd2 : varList) {
			strName += "'" + pd2.get("department_name") + "',";
			strNumber += pd2.get("accesscount") + ",";
		}
		
		if (strName != "") {
			strName = strName.substring(0, strName.length() - 1);
		}
		
		if (strNumber != "") {
			strNumber = strNumber.substring(0, strNumber.length() - 1);
		}
		
		mv.addObject("varList", varList); // 查询配置列表
		mv.addObject("axisx", strName); //
		mv.addObject("axisy", strNumber); //
		
		// 组织机构
		List<Department> departments = departmentService.listAllUser("0");
		departments = departmentService.listAllUser(departments.get(0).getDEPARTMENT_ID());
		departments.add(0, new Department("全部", "0"));
		departments.add(new Department("匿名用户", "anonymous"));
		mv.addObject("dept", departments);
		mv.addObject("pd", pd);
		mv.setViewName("mapmanager/statis/userstat");
		return mv;
	}
	
	/**
	 * 用户统计
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/runstat")
	public ModelAndView runstat() throws Exception {
		ModelAndView mv = this.getModelAndView();
		
		PageData pd = this.getPageData();//fujl 2017-2-11 myf 原因的为new PageData();
		// 参数 时间 time
		String timestart = pd.getString("timestart"); // 开始时间
		String timeend = pd.getString("timeend"); // 结束时间
		if (timestart != null && !"".equals(timestart)) {
			pd.put("timestart", timestart + " 00:00:00");
		}
		if (timeend != null && !"".equals(timeend)) {
			pd.put("timeend", timeend + " 23:59:59");
		}
		// 获取所有查询配置服务 fujl 2016-11-20 ++
		List<PageData> varList = msStatServices.listStatServiceSuccess(pd);
		
		List<String> varListX = new ArrayList<String>();
		List<String> varListY = new ArrayList<String>();
		for (PageData pd2 : varList) {
			varListX.add("'" + pd2.get("ACCESSDATE").toString() + "'");
			// System.out.println(pd2.get("ACCESSDATE").toString().substring(0,10));
			varListY.add(pd2.get("ACCESSRATE").toString());

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date d = format.parse(pd2.get("ACCESSDATE").toString());
			pd2.put("ACCESSDATE", format.format(d));
		}
		
		mv.addObject("varList", varList);// 服务成功率
		mv.addObject("axisx", varListX);//
		mv.addObject("axisy", varListY);//
		mv.addObject("pd", pd);
		mv.setViewName("mapmanager/statis/runstat");
		return mv;
	}
	
	/**
	 * 递归 获得 根节点下所有的 子几点
	 * 
	 * @param rootId
	 * @param list
	 * @param toc_selected_id_list
	 */
	private void retrieveChild(int rootId, List<MsResServicesClass> list, List<Integer> toc_selected_id_list) {
		boolean flag = false;
		for (MsResServicesClass clas : list) {
			if (rootId == Integer.parseInt(clas.getParentid())) {
				int lefid = Integer.parseInt(clas.getId());
				toc_selected_id_list.add(lefid);
				retrieveChild(lefid, list, toc_selected_id_list);
				flag = true;
			}
		}
		if (!flag) {
			return;
		}
	}
	
	/**
	 * 递归 获得根节点下所有的子节点
	 * 
	 * @param rootId
	 * @param list
	 * @param dept_selected_id_list
	 */
	private void retrieveChildDept(String rootId, List<Department> list, List<String> dept_selected_id_list) {
		boolean flag = false;
		for (Department clas : list) {
			if (rootId.equals(clas.getPARENT_ID())) {
				String lefid = clas.getDEPARTMENT_ID();
				dept_selected_id_list.add(lefid);
				retrieveChildDept(lefid, list, dept_selected_id_list);
				flag = true;
			}
		}
		if (!flag) {
			return;
		}
	}
}
