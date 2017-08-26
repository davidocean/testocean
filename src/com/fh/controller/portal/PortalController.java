package com.fh.controller.portal;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.support.json.JSONUtils;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.ms.res.MsResServicesClass;
import com.fh.entity.ms.res.TOCItem;
import com.fh.entity.ms.services.MsQueryConfig;
import com.fh.entity.ms.services.MsQueryConfigCombination;
import com.fh.entity.ms.services.MsServicesReg;
import com.fh.entity.ms.services.MsStatClassification;
import com.fh.entity.ms.services.MsStatConfig;
import com.fh.entity.ms.services.MsStatConfigDetail;
import com.fh.entity.ms.services.MsStatConfigRange;
import com.fh.entity.system.User;
import com.fh.service.bean.MsArcgisService;
import com.fh.service.ms.res.MsResServicesClassService;
import com.fh.service.ms.services.MsQueryConfigCombinationService;
import com.fh.service.ms.services.MsQueryConfigService;
import com.fh.service.ms.services.MsServicesRegService;
import com.fh.service.ms.services.MsStatClassificationService;
import com.fh.service.ms.services.MsStatConfigDetailService;
import com.fh.service.ms.services.MsStatConfigRangeService;
import com.fh.service.ms.services.MsStatConfigService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.FileUpload;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.PathUtil;
import com.mapone.MsConfigProperties;
//import net.sf.json.JSONArray;

/**
 * 门户
 * @author mwhdds
 * @date 2016-10-14
 */
@Controller
@RequestMapping(value="/portal")
public class PortalController extends BaseController {

	//fujl 2016-11-13 注入服务注册服务
	@Resource(name="msServicesRegService")
	private MsServicesRegService msServicesRegService;
	//arcgis 服务对象 fujl 2016-11-19 ++
	MsArcgisService msArcgisSrv=new MsArcgisService();
	//注册服务
	@Resource(name = "msResServicesClassService")
	private MsResServicesClassService msResServicesClassService;

	//fujl 2016-12-31  注入地图查询配置组合服务
	@Resource(name="msQueryConfigCombinationService")
	private MsQueryConfigCombinationService configComSrv;
	//fujl 2016-11-19  注入查询配置明细服务
	@Resource(name="msQueryConfigService")
	private MsQueryConfigService msQueryConfigService;
	
	//fujl 2016-11-22  注入统计配置服务
	@Resource(name="msStatConfigService")
	private MsStatConfigService msStatConfigService;
	//统计分类
	@Resource(name="msStatClassificationService")
	private MsStatClassificationService msStatClassiSrv;
	//报表统计配置明细
	@Resource(name="msStatConfigDetailService")
	private MsStatConfigDetailService msStatConfigDetailSrv;
	//报表统计区间明细
	@Resource(name="msStatConfigRangeService")
	private MsStatConfigRangeService msStatConfigRangeSrv;
	
	/**去发送邮箱页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/index")
	public ModelAndView index() throws Exception{
		ModelAndView mv = this.getModelAndView();
		
		PageData pd = getPD();
		User user = null;
		String loginUser = "";
		try
		{
			 Session session =  Jurisdiction.getSession();
			 user = (User)session.getAttribute(Const.SESSION_USER);
			 loginUser = user.getUSERNAME();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		if(loginUser == null || loginUser.length() <= 0)
		{
			pd.put("ISQUERY",0);
			pd.put("ISSTAT",0);
		}
		else
		{
			pd.put("ISQUERY",user.getISQUERY());
			pd.put("ISSTAT",user.getISSTAT());
		}
        pd.put("COPYRIGHT", Const.SYS_COPYRIGHT);
		mv.addObject("pd",pd);
		mv.setViewName("portal/index2");		
		return mv;
	}
	
	
	/**登录
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/login")
	public ModelAndView login(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		//List<PageData> queryConfigList=msQueryConfigService.list(page);
		//mv.addObject("varList",queryConfigList);//查询配置列表
		mv.addObject("pd",getPD());	
		mv.setViewName("portal/login");
		return mv;
	}
	


	
	/********************** 以下为查询配置或统计分析配置功能模块 fujl 2017-1-4 增加注释  *************************/
	/**
	 * 地图查询页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/query")
	public ModelAndView query(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();

		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		
		toc t = new toc();
		
		
		mv.addObject("toc",t.getTOC2(msResServicesClassService,msServicesRegService,user));
		//System.out.println(t.getTOC2(msResServicesClassService,msServicesRegService,user));
		
		List<PageData> configCombList=configComSrv.list(page);
		mv.addObject("configCombList",configCombList);		//查询组合列表 fujl 2016-12-31 ++
		
		List<MsQueryConfig> queryConfigList= msQueryConfigService.listAll(null);	//
		JSONArray jsonArray0 = JSONArray.fromObject(queryConfigList);
		mv.addObject("queryConfigListjson",jsonArray0.toString());							//加载注册服务列表
		/*--fujl 2017-3-15 增加版权 start--*/
		PageData pd=getPD();
		pd.put("COPYRIGHT", Const.SYS_COPYRIGHT);
		mv.addObject("pd",pd);	
		/*--fujl 2017-3-15 增加版权 end --*/
		mv.setViewName("portal/featurequery");
		return mv;
	}
	

	
	
	/**
	 * 统计查询页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/stat")
	public ModelAndView stat() throws Exception{
		ModelAndView mv = this.getModelAndView();
		/*--fujl 2017-3-15 增加版权 start--*/
		PageData pd=getPD();
		pd.put("COPYRIGHT", Const.SYS_COPYRIGHT);
		mv.addObject("pd",pd);	
		/*--fujl 2017-3-15 增加版权 end --*/
		//读取分类
		List<MsStatClassification> varListClass =  msStatClassiSrv.listAll(null);
		mv.addObject("ListClass",varListClass);
		
		//读取配置主表
		List<MsStatConfig> varListStatConfig =  msStatConfigService.listAll(null);
		mv.addObject("ListStatConfig",varListStatConfig);
		
		//读取配置主表JSON
		//List<MsStatConfig> varListStatConfigjson =  msStatConfigService.listAll(null);
		JSONArray jsonArray0 = JSONArray.fromObject(varListStatConfig);
		mv.addObject("ListStatConfigjson",jsonArray0.toString());
		
		//读取配置从表
		List<MsStatConfigDetail> varList = msStatConfigDetailSrv.listAll(null);
		JSONArray jsonArray = JSONArray.fromObject(varList);
		mv.addObject("ListDetail",jsonArray.toString());//查询配置列表	
				
		List<MsStatConfigRange> varRangeList = msStatConfigRangeSrv.listAll(null);
		JSONArray jsonArray2 = JSONArray.fromObject(varRangeList);
		mv.addObject("ListRange",jsonArray2.toString());//查询配置列表	
		
		
		mv.setViewName("portal/featurestat");
		return mv;
	}
	
	/**要素统计导出
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/statExport")
	public ModelAndView  statExport() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		try{
			String[] aa = pd.getString("data_x").split(",");
			String[] bb = pd.getString("data_y").split(",");
			String[] cc = pd.getString("title").split(",");

			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add(cc[0]); 		
			titles.add(cc[1]);  	

			dataMap.put("titles", titles);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i< aa.length ;i++){
				PageData vpd = new PageData();
				vpd.put("var1", aa[i].toString());		//1
				vpd.put("var2", bb[i].toString());		//2
				varList.add(vpd);
			}
			dataMap.put("varList", varList);
			
			ObjectExcelView erv = new ObjectExcelView();					//执行excel操作
			mv = new ModelAndView(erv,dataMap);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
		
	}
	
	/**
	 * 地图查询配置页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/queryconfig")
	public ModelAndView queryconfig(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		//获取所有的服务
		MsServicesReg reg = new MsServicesReg();
		List<MsServicesReg>	regServiceList = msServicesRegService.listAll(reg);//所有注册服务列表
		List<PageData> configCombList=configComSrv.list(page);//fujl 2016-12-31 ++
		mv.addObject("pd",getPD());
		mv.addObject("varList",regServiceList);//加载注册服务列表
		mv.addObject("configCombList",configCombList);//查询组合列表 fujl 2016-12-31 ++
		mv.setViewName("portal/queryconfig");
		return mv;
	}
	
	/**
	 * 删除指定的地图查询配置
	 * @return
	 * @version
	 * (1) fujl 2016-11-20
	 */
	@RequestMapping(value="/removeQueryConfig")
	@ResponseBody
	public Object removeQueryConfig(){
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		User loginUser=Jurisdiction.getLoginUser();
		if(null==loginUser){
			map.put("success", false);
			map.put("message","移除失败，原因：您尚未登录或登录超时！");
			return AppUtil.returnObject(new PageData(), map);
		}
		try{
			pd=this.getPageData();
			String configId=pd.getString("configId[]");
			configComSrv.delete(Integer.parseInt(configId));//删除已配置的查询组合
			msQueryConfigService.deleteByCombId(Integer.parseInt(configId));//删除关联的数据
			map.put("success", true);
			map.put("message","移除成功！");
		}catch(Exception ex){
			map.put("success", false);
			map.put("message","移除失败，原因：系统异常！");
		}
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**
	 * 保存新的地图查询配置
	 * @return
	 * @version
	 * (1) fujl 2016-11-20
	 */
	@RequestMapping(value="/addQueryConfig")
	@ResponseBody
	public Object addQueryConfig(){
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		User loginUser=Jurisdiction.getLoginUser();
		if(null==loginUser){
			map.put("success", false);
			map.put("message","保存失败，原因：您尚未登录或登录超时！");
			return AppUtil.returnObject(new PageData(), map);
		}
		try{
			pd=this.getPageData();
			String queryStr=pd.getString("queryStr");//子查询字符串
			String configname=pd.getString("configname");//配置组合名称
			String configlength=pd.getString("configlength");//排序号
			MsQueryConfigCombination configComb=configComSrv.findByName(configname);
			if(null!=configComb){
				map.put("success", false);
				map.put("message","保存失败，原因：当前查询组名称【"+configname+"】已存在！");
				return AppUtil.returnObject(new PageData(), map);
			}
			//-------------------保存子查询  start --------------------
			JSONArray jsonArray = JSONArray.fromObject(queryStr);
			@SuppressWarnings("unchecked")
			List<Map<String,Object>> mapListJson = (List<Map<String,Object>>)jsonArray;
			for(Map<String,Object> tempMap : mapListJson){
				PageData tempPD = new PageData();
				tempPD.put("servicename", tempMap.get("servicename"));
				tempPD.put("configname",tempMap.get("configName").toString());
				tempPD.put("fieldname",tempMap.get("fieldname"));
				tempPD.put("fieldvalue",tempMap.get("fieldvalue"));
				tempPD.put("fieldtype",tempMap.get("fieldtype"));
				tempPD.put("whereexp",tempMap.get("whereexp"));
				tempPD.put("layername",tempMap.get("layername"));
				tempPD.put("layerid",tempMap.get("layerid"));
				tempPD.put("querycombinationid",-9999);
				tempPD.put("zmemo","###"+configname+"###");//存放临时值
				tempPD.put("enabled",1);
				tempPD.put("sortcode",1);
				tempPD.put("deletemark",0);
				
				tempPD.put("createdate", new Date());
				tempPD.put("createuserid",loginUser.getUSER_ID());
				tempPD.put("createusername",loginUser.getUSERNAME());
				
				tempPD.put("modifydate", new Date());
				tempPD.put("modifyuserid",loginUser.getUSER_ID());
				tempPD.put("modifyusername",loginUser.getUSERNAME());
				
				msQueryConfigService.save(tempPD);
				tempPD.clear();
				tempPD=null;
			}
			//-------------------保存子查询  end --------------------
			//-------保存主表 start-------------
			pd.put("combinationname",configname);
			pd.put("enabled",1);
			pd.put("sortcode",configlength);
			pd.put("deletemark",0);
			
			pd.put("createdate", new Date());
			pd.put("createuserid",loginUser.getUSER_ID());
			pd.put("createusername",loginUser.getUSERNAME());
			
			pd.put("modifydate", new Date());
			pd.put("modifyuserid",loginUser.getUSER_ID());
			pd.put("modifyusername",loginUser.getUSERNAME());
			configComSrv.save(pd);//保存查询配置组合
			//-------保存主表  end-------------
			//保存成功后，查找ID
			configComb=configComSrv.findByName(configname);
			if(null!=configComb){
              //更新关联的子查询组表中的关联字段
				PageData tempPD = new PageData();
				tempPD.put("querycombinationid",configComb.getId());
				tempPD.put("zmemo","###"+configname+"###");
				msQueryConfigService.updateCombIdByZmemo(tempPD);
				tempPD.clear();
				tempPD=null;
			}
			//获取所有查询配置服务 fujl 2016-11-20 ++
			List<PageData> queryConfigList=configComSrv.list(this.getPage());//重新加载列表 fujl 2016-12-31 ++
			map.put("queryConfigList", queryConfigList);
			map.put("success", true);
			map.put("message","保存成功！");
		}catch(Exception ex){
			map.put("success", false);
			map.put("message","保存失败，原因：系统异常！");
		}
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**
	 * 移动地图查询配置(上移或下移)
	 * @return
	 * @version
	 * (1) fujl 2017-1-2
	 */
	@RequestMapping(value="/moveQueryConfig")
	@ResponseBody
	public Object moveQueryConfig(){
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		User loginUser=Jurisdiction.getLoginUser();
		if(null==loginUser){
			map.put("success", false);
			map.put("message","移动失败，原因：您尚未登录或登录超时！");
			return AppUtil.returnObject(new PageData(), map);
		}
		try{
			pd=this.getPageData();
			PageData curPD=new PageData();
			curPD.put("id", pd.getString("currId"));
			curPD.put("sortcode", pd.getString("currSort"));
			configComSrv.updateSortCodeById(curPD);
			
			PageData changePD=new PageData();
			changePD.put("id",pd.getString("changId"));
			changePD.put("sortcode", pd.getString("changeSort"));
			configComSrv.updateSortCodeById(changePD);
			map.put("success", true);
			map.put("message","移动成功！");
		}catch(Exception ex){
			map.put("success", false);
			map.put("message","移动失败，原因：系统异常！");
		}
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**
	 * 统计报表查询配置列表页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/statconfig")
	public ModelAndView statconfig(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
        //String 
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String staticsTypeID = pd.getString("staticsTypeSelect");
        //String 
		if(null != staticsTypeID && !"".equals(staticsTypeID)){
			pd.put("staticsTypeID", staticsTypeID.trim());
		}
		String groupid = pd.getString("groupid");
        //String 
		if(null != groupid && !"".equals(groupid)){
			pd.put("groupid", groupid.trim());
		}
		page.setPd(pd);
		List<PageData> statconfigList=msStatConfigService.configlistPage(page);
		List<PageData> groupList=msStatClassiSrv.findByAll();
		mv.addObject("statconfigList",statconfigList);//加载统计配置列表 fujl 2017-1-4
		mv.addObject("groupList", groupList);//分组列表
		mv.setViewName("portal/statconfigList");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 新增统计报表查询配置列表页
	 * @author fujl 2017-1-4
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/statconfigAdd")
	public ModelAndView statconfigAdd(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		MsServicesReg reg = new MsServicesReg();
		List<MsServicesReg>	regServiceList = msServicesRegService.listAll(reg);//所有注册服务列表
		List<PageData> groupList=msStatClassiSrv.list(page);
		mv.addObject("groupList", groupList);//分组列表
		mv.addObject("regServiceList",regServiceList);
		mv.setViewName("portal/statconfig");
		return mv;
	}
	
	
	
	/**
	 * 保存报表统计配置
	 * @return
	 * @version
	 * (1) fujl 2016-11-22
	 */
	@RequestMapping(value="/saveStatConfig")
	@ResponseBody
	public Object saveStatConfig(){
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		User loginUser=Jurisdiction.getLoginUser();
		if(null==loginUser){
			map.put("success", false);
			map.put("message","保存失败，原因：您尚未登录或登录超时！");
			return AppUtil.returnObject(new PageData(), map);
		}
		try{
			pd=this.getPageData();
			String id=pd.getString("id");//统计主表中的ID，用于编辑时的操作
			String queryStr=pd.getString("queryStr");//子查询字符串
			String configname=pd.getString("configname");//配置组合名称
			String groupVal=pd.getString("groupval");//组名
			String grouptext=pd.getString("grouptext");//组号
			String staticsname=pd.getString("staticsname");//统计分类名称
			String staticstype=pd.getString("staticstype");//统计分类值
			MsStatConfig configComb=null;
			PageData searchPd=new PageData();//查询判断
			searchPd.put("staticsname",configname);
			searchPd.put("staticstypeval",staticstype);
			//如果id不存在的话，说明是进行新增操作,新增的时候需要检查当前名称是否已存在
			if(null==id||StringUtils.isEmpty(id)){
					configComb=msStatConfigService.findByTypeAndName(searchPd);
					if(null!=configComb){
						map.put("success", false);
						map.put("message","保存失败，原因：当前统计配置名称【"+configname+"】已存在！");
						return AppUtil.returnObject(new PageData(), map);
					}
		    }else{//说明进行更新操作
		    	MsStatConfig configTemp=msStatConfigService.findByTypeAndName(searchPd);
		    	if(null!=configTemp&&!id.equals(String.valueOf(configTemp.getID()))){//说明需要更新统计名称已存在
		    		map.put("success", false);
					map.put("message","保存失败，原因：当前统计配置名称【"+configname+"】已存在！");
					return AppUtil.returnObject(new PageData(), map);
		    	}
		    }
			
			Integer sortIndex=1;
			//-------------------保存子查询  start --------------------
			JSONArray jsonArray = JSONArray.fromObject(queryStr);
			@SuppressWarnings("unchecked")
			List<Map<String,Object>> mapListJson = (List<Map<String,Object>>)jsonArray;
			for(Map<String,Object> tempMap : mapListJson){
				if(null!=id&&!StringUtils.isEmpty(id)){//如果是进行更新操作的话，需要判断当前子查询是否已存在
					searchPd.clear();
					searchPd.put("staticobjname",tempMap.get("configname").toString());
					searchPd.put("staticsid",id);
				    List<PageData>	existsList=msStatConfigDetailSrv.findByStaticidName(searchPd);
				    if(null!=existsList&&existsList.size()>0){//说明子统计已存在
						map.put("success", false);
						map.put("message","保存失败，原因：当前子统计名称【"+tempMap.get("configname").toString()+"】已存在！");
						return AppUtil.returnObject(new PageData(), map);
				    }
				}
				PageData tempPD = new PageData();
				tempPD.put("servicename", tempMap.get("servicename"));
				tempPD.put("staticobjname",tempMap.get("configname").toString());
				tempPD.put("fieldname",tempMap.get("fieldname"));
				tempPD.put("fieldalias",tempMap.get("fieldvalue"));
				tempPD.put("fieldtype",tempMap.get("fieldtype"));
				tempPD.put("whereexp",tempMap.get("whereexp"));
				tempPD.put("layername",tempMap.get("layername"));
				tempPD.put("layerid",tempMap.get("layerid"));
				tempPD.put("groupfieldname",tempMap.get("groupfieldname"));//是否设置分组
				tempPD.put("staticsid",-9999);
				if(null==id||StringUtils.isEmpty(id)){
				  tempPD.put("zmemo","###"+configname+"_"+staticstype+"###");//存放临时值
				}else{
					 tempPD.put("zmemo",null);
					 tempPD.put("staticsid",id);
				}
				tempPD.put("enabled",1);
				tempPD.put("sortcode",sortIndex);
				tempPD.put("deletemark",0);
				
				tempPD.put("createdate", new Date());
				tempPD.put("createuserid",loginUser.getUSER_ID());
				tempPD.put("createusername",loginUser.getUSERNAME());
				
				tempPD.put("modifydate", new Date());
				tempPD.put("modifyuserid",loginUser.getUSER_ID());
				tempPD.put("modifyusername",loginUser.getUSERNAME());
				
				msStatConfigDetailSrv.save(tempPD);
				tempPD.clear();
				tempPD=null;
				sortIndex=sortIndex+1;
			}
			//-------------------保存子查询  end --------------------	
			
			//如果id不存在的话，说明是进行新增操作
			if(null==id||StringUtils.isEmpty(id)){
				List<PageData> statconfigList=msStatConfigService.list(this.getPage());
				//下面开始保存主表
				pd.remove("id");
			    pd.put("staticsname",configname);
				pd.put("staticstypeval",staticstype);
				pd.put("staticstype",staticsname);
				pd.put("classificationId",groupVal);
				pd.put("classificationname",grouptext);
				
				pd.put("enabled",1);
				if(null==statconfigList||statconfigList.size()<1){
					pd.put("sortcode",1);
				}else{
					pd.put("sortcode",statconfigList.size()+1);
				}
				
				pd.put("deletemark",0);
	
				pd.put("createdate", new Date());
				pd.put("createuserid",loginUser.getUSER_ID());
				pd.put("createusername",loginUser.getUSERNAME());
				
				pd.put("modifydate", new Date());
				pd.put("modifyuserid",loginUser.getUSER_ID());
				pd.put("modifyusername",loginUser.getUSERNAME());
		
				msStatConfigService.save(pd);
				configComb=msStatConfigService.findByTypeAndName(searchPd);
				if(null!=configComb){//批量更新子表操作
					searchPd.clear();
					searchPd.put("staticsid", configComb.getID());
					searchPd.put("zmemo","###"+configname+"_"+staticstype+"###");
					msStatConfigDetailSrv.updateStaticsIdByZmemo(searchPd);
				}
			}else{//更新操作
			     configComb=msStatConfigService.findById(pd);
			     PageData updatePd = new PageData();
			     updatePd.put("id",configComb.getID());
			     updatePd.put("staticsname",configname);
			     updatePd.put("staticstypeval",staticstype);
			     updatePd.put("staticstype",staticsname);
			     updatePd.put("classificationId",groupVal);
			     updatePd.put("classificationname",grouptext);
			     updatePd.put("enabled",configComb.getEnabled());
			     updatePd.put("sortcode",configComb.getSortcode());
			     updatePd.put("deletemark",configComb.getDeletemark());
			     updatePd.put("createdate", configComb.getCreatedate());
			     updatePd.put("createuserid",configComb.getCreateuserid());
			     updatePd.put("createusername",configComb.getCreateusername());
			     updatePd.put("modifydate", new Date());
			     updatePd.put("modifyuserid",loginUser.getUSER_ID());
			     updatePd.put("modifyusername",loginUser.getUSERNAME());
				 msStatConfigService.update(updatePd);//更新操作
				 updatePd.clear();
				 updatePd=null;
			}
			map.put("data",configComb.getID());//回带ID
			map.put("success", true);
			map.put("message","保存成功！");
		}catch(Exception ex){
			map.put("success", false);
			map.put("message","保存失败，原因：系统异常！");
		}
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**
	 * 删除指定的统计配置
	 * @return
	 * @version
	 * (1) fujl 2016-11-27
	 */
	@RequestMapping(value="/removeStatConfig")
	@ResponseBody
	public Object removeStatConfig(){
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		User loginUser=Jurisdiction.getLoginUser();
		if(null==loginUser){
			map.put("success", false);
			map.put("message","移除失败，原因：您尚未登录或登录超时！");
			return AppUtil.returnObject(new PageData(), map);
		}
		try{
			pd=this.getPageData();
			String configId=pd.getString("configId");
			msStatConfigService.delete(Integer.parseInt(configId));//删除主表
			msStatConfigDetailSrv.deleteByStaticsid(Integer.parseInt(configId));//删除子表
			msStatConfigRangeSrv.deleteByStaticsid(Integer.parseInt(configId));
			map.put("success", true);
			map.put("message","移除成功！");
		}catch(Exception ex){
			map.put("success", false);
			map.put("message","移除失败，原因：系统异常！");
		}
		return AppUtil.returnObject(new PageData(), map);
	}
	
	
	/**
	 * 设置当前配置是否启用
	 * @return
	 * @version
	 * (1) fujl 2016-11-27
	 */
	@RequestMapping(value="/setEnbleStatConfig")
	@ResponseBody
	public Object setEnbleStatConfig(){
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		User loginUser=Jurisdiction.getLoginUser();
		if(null==loginUser){
			map.put("success", false);
			map.put("message","状态设置失败，原因：您尚未登录或登录超时！");
			return AppUtil.returnObject(new PageData(), map);
		}
		try{
			pd=this.getPageData();
			String configId=pd.getString("configId");
			String enable=pd.getString("enable");
			pd.put("id", configId);
			pd.put("enabled", enable);
			msStatConfigService.setEnble(pd);//设置主表
			map.put("success", true);
			map.put("message","状态设置成功！");
		}catch(Exception ex){
			map.put("success", false);
			map.put("message","状态设置失败，原因：系统异常！");
		}
		return AppUtil.returnObject(new PageData(), map);
	}
	
	
	
	/*********************  分组操作 fujl 2017-1-5 ++ start ****************************/
	/**
	 * 分组管理列表页
	 * @author fujl 2017-1-5
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/statClassific")
	public ModelAndView statClassific(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		List<PageData> groupList=msStatClassiSrv.list(page);
		mv.addObject("groupList", groupList);//分组列表
		mv.setViewName("portal/statClassific");
		return mv;
	}
	
	/**
	 * 删除指定分组
	 * @return
	 * @version
	 * (1) fujl 2017-1-5
	 */
	@RequestMapping(value="/removeClassific")
	@ResponseBody
	public Object removeClassific(){
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		User loginUser=Jurisdiction.getLoginUser();
		if(null==loginUser){
			map.put("success", false);
			map.put("message","删除失败，原因：您尚未登录或登录超时！");
			return AppUtil.returnObject(new PageData(), map);
		}
		try{
			//fujl 2017-7-2 判断当前分组下面是否存在数据
			pd=this.getPageData();
			String id=pd.getString("id");
			Page page=new Page();
			pd.put("groupid", id);
			page.setPd(pd);
			List<PageData> configlist=msStatConfigService.configlistPage(page);
			if(null!=configlist&&configlist.size()>0){
				map.put("success", false);
				map.put("message","删除失败，原因：当前分组已在使用！");
			}else{
				msStatClassiSrv.delete(Integer.parseInt(id));
				map.put("success", true);
				map.put("message","删除成功！");
			}
		}catch(Exception ex){
			map.put("success", false);
			map.put("message","删除失败，原因：系统异常！");
		}
		return AppUtil.returnObject(new PageData(), map);
	}
	
	
	/**
	 * 新增分组
	 * @return
	 * @version
	 * (1) fujl 2017-1-5
	 */
	@RequestMapping(value="/saveClassific")
	@ResponseBody
	public Object saveClassific(){
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		User loginUser=Jurisdiction.getLoginUser();
		if(null==loginUser){
			map.put("success", false);
			map.put("message","保存失败，原因：您尚未登录或登录超时！");
			return AppUtil.returnObject(new PageData(), map);
		}
		try{
			pd=this.getPageData();
			String name=pd.getString("name");
			MsStatClassification statClassification=msStatClassiSrv.findByName(name);
			if(null!=statClassification){
				map.put("success", false);
				map.put("message","保存失败，原因：当前分组名称【"+name+"】已存在！");
				return AppUtil.returnObject(new PageData(), map);
			}
			pd.put("enabled",1);
			pd.put("deletemark",0);
			
			pd.put("createdate", new Date());
			pd.put("createuserid",loginUser.getUSER_ID());
			pd.put("createusername",loginUser.getUSERNAME());
			
			pd.put("modifydate", new Date());
			pd.put("modifyuserid",loginUser.getUSER_ID());
			pd.put("modifyusername",loginUser.getUSERNAME());
			msStatClassiSrv.save(pd);
			map.put("success", true);
			map.put("message","保存成功！");
		}catch(Exception ex){
			map.put("success", false);
			map.put("message","保存失败，原因：系统异常！");
		}
		return AppUtil.returnObject(new PageData(), map);
	}
	/*********************  分组操作 fujl 2017-1-5 ++ end  ****************************/
	
	
	/*********************  基本统计、分组统计操作 fujl 2017-1-5 ++ start**************************/
	/**
	 * 基本统计管理列表页
	 * @author fujl 2017-1-5
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/statBaseConfig")
	public ModelAndView statBaseConfig(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd=this.getPageData();
		String id=pd.getString("id");
		MsServicesReg reg = new MsServicesReg();
		MsStatConfig statConfig=msStatConfigService.findById(pd);
		List<MsServicesReg>	regServiceList = msServicesRegService.listAll(reg);//所有注册服务列表
		List<PageData> groupList=msStatClassiSrv.list(page);
		//加载明细列表
		List<PageData> detailList=null;
		if(null!=id&&!StringUtils.isEmpty(id)){
			detailList=msStatConfigDetailSrv.findByStaticsId(Integer.parseInt(id));
		}
		mv.addObject("statConfig", statConfig);//统计配置对象
		mv.addObject("groupList", groupList);//分组列表
		mv.addObject("regServiceList",regServiceList);//注册的服务列表
		mv.addObject("detailList",detailList);
		mv.setViewName("portal/statBaseConfig");
		return mv;
	}
	
	/**
	 * 分组统计管理列表页
	 * @author fujl 2017-1-5
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/statGroupConfig")
	public ModelAndView statGroupConfig(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd=this.getPageData();
		String id=pd.getString("id");
		MsServicesReg reg = new MsServicesReg();
		MsStatConfig statConfig=msStatConfigService.findById(pd);
		List<MsServicesReg>	regServiceList = msServicesRegService.listAll(reg);//所有注册服务列表
		List<PageData> groupList=msStatClassiSrv.list(page);
		//加载明细列表
		List<PageData> detailList=null;
		if(null!=id&&!StringUtils.isEmpty(id)){
			detailList=msStatConfigDetailSrv.findByStaticsId(Integer.parseInt(id));
		}
		mv.addObject("statConfig", statConfig);//统计配置对象
		mv.addObject("groupList", groupList);//分组列表
		mv.addObject("regServiceList",regServiceList);//注册的服务列表
		mv.addObject("detailList",detailList);
		mv.setViewName("portal/statGroupConfig");
		return mv;
	}
	
	/**
	 * 区间统计管理列表页
	 * @author fujl 2017-1-12
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/statRangeConfig")
	public ModelAndView statRangeConfig(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd=this.getPageData();
		String id=pd.getString("id");
		MsServicesReg reg = new MsServicesReg();
		MsStatConfig statConfig=msStatConfigService.findById(pd);
		List<MsServicesReg>	regServiceList = msServicesRegService.listAll(reg);//所有注册服务列表
		List<PageData> groupList=msStatClassiSrv.list(page);
		//加载明细列表
		List<PageData> detailList=null;
		if(null!=id&&!StringUtils.isEmpty(id)){
			detailList=msStatConfigDetailSrv.findByStaticsId(Integer.parseInt(id));
		}
		mv.addObject("statConfig", statConfig);//统计配置对象
		mv.addObject("groupList", groupList);//分组列表
		mv.addObject("regServiceList",regServiceList);//注册的服务列表
		mv.addObject("detailList",detailList);
		mv.setViewName("portal/statRangeConfig");
		return mv;
	}
	
	/**
	 * 区间统计明细管理列表页
	 * @author fujl 2017-1-12
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/statRangeDetailConfig")
	public ModelAndView statRangeDetailConfig(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd=this.getPageData();
		String id=pd.getString("id");//range表中的主键
		String detailid=pd.getString("detailId");//stat_config_detail表中的主键
		String staticsid=pd.getString("staticsid");//stat_config表中的主键
		List<PageData> rangeList=msStatConfigRangeSrv.findByConfigDetailId(Integer.parseInt(detailid));
		mv.addObject("rangeList",rangeList);
		mv.addObject("detailId",detailid);
		mv.addObject("staticsid",staticsid);
		mv.setViewName("portal/statRangeDetailConfig");
		return mv;
	}
	
	/**
	 * 保存区间
	 * @return
	 * @version
	 * (1) fujl 2017-1-12
	 */
	@RequestMapping(value="/saveRange")
	@ResponseBody
	public Object saveRange(){
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		User loginUser=Jurisdiction.getLoginUser();
		if(null==loginUser){
			map.put("success", false);
			map.put("message","获取失败，原因：您尚未登录或登录超时！");
			return AppUtil.returnObject(new PageData(), map);
		}
		try{
			pd=this.getPageData();
			pd.put("enabled",1);
			pd.put("deletemark",0);
			
			pd.put("createdate", new Date());
			pd.put("createuserid",loginUser.getUSER_ID());
			pd.put("createusername",loginUser.getUSERNAME());
			
			pd.put("modifydate", new Date());
			pd.put("modifyuserid",loginUser.getUSER_ID());
			pd.put("modifyusername",loginUser.getUSERNAME());
			msStatConfigRangeSrv.save(pd);
			map.put("success", true);
			map.put("message","保存成功！");
		}catch(Exception ex){
			map.put("success", false);
			map.put("message","获取失败，原因：系统异常！");
		}
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**
	 * 获取区间明细
	 * @return
	 * @version
	 * (1) fujl 2017-1-5
	 */
	@RequestMapping(value="/findStatRange")
	@ResponseBody
	public Object findStatRange(){
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		User loginUser=Jurisdiction.getLoginUser();
		if(null==loginUser){
			map.put("success", false);
			map.put("message","获取失败，原因：您尚未登录或登录超时！");
			return AppUtil.returnObject(new PageData(), map);
		}
		try{
			pd=this.getPageData();
			String detailid=pd.getString("detailid");
			//加载明细列表
			List<PageData> rangeList=null;
			if(null!=detailid&&!StringUtils.isEmpty(detailid)){
				rangeList=msStatConfigRangeSrv.findByConfigDetailId(Integer.parseInt(detailid));
			}
			map.put("rangeList",rangeList);
			map.put("success", true);
			map.put("message","获取成功！");
		}catch(Exception ex){
			map.put("success", false);
			map.put("message","获取失败，原因：系统异常！");
		}
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**
	 * 删除指定区间
	 * @return
	 * @version
	 * (1) fujl 2017-1-5
	 */
	@RequestMapping(value="/removeRange")
	@ResponseBody
	public Object removeRange(){
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		User loginUser=Jurisdiction.getLoginUser();
		if(null==loginUser){
			map.put("success", false);
			map.put("message","删除失败，原因：您尚未登录或登录超时！");
			return AppUtil.returnObject(new PageData(), map);
		}
		try{
			pd=this.getPageData();
			String id=pd.getString("id");
			msStatConfigRangeSrv.delete(Integer.parseInt(id));
			map.put("success", true);
			map.put("message","删除成功！");
		}catch(Exception ex){
			map.put("success", false);
			map.put("message","删除失败，原因：系统异常！");
		}
		return AppUtil.returnObject(new PageData(), map);
	}
	
	
	
	
	
	
	
	
	/**
	 * 获取统计明细
	 * @return
	 * @version
	 * (1) fujl 2017-1-5
	 */
	@RequestMapping(value="/findStatDetail")
	@ResponseBody
	public Object findStatDetail(){
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		User loginUser=Jurisdiction.getLoginUser();
		if(null==loginUser){
			map.put("success", false);
			map.put("message","获取失败，原因：您尚未登录或登录超时！");
			return AppUtil.returnObject(new PageData(), map);
		}
		try{
			pd=this.getPageData();
			String id=pd.getString("id");
			//加载明细列表
			List<PageData> detailList=null;
			if(null!=id&&!StringUtils.isEmpty(id)){
				detailList=msStatConfigDetailSrv.findByStaticsId(Integer.parseInt(id));
			}
			map.put("detailList",detailList);
			map.put("success", true);
			map.put("message","获取成功！");
		}catch(Exception ex){
			map.put("success", false);
			map.put("message","获取失败，原因：系统异常！");
		}
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**
	 * 删除指定的统计配置明细
	 * @return
	 * @version
	 * (1) fujl 2016-11-27
	 */
	@RequestMapping(value="/removeStatConfigDetail")
	@ResponseBody
	public Object removeStatConfigDetail(){
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		User loginUser=Jurisdiction.getLoginUser();
		if(null==loginUser){
			map.put("success", false);
			map.put("message","移除失败，原因：您尚未登录或登录超时！");
			return AppUtil.returnObject(new PageData(), map);
		}
		try{
			pd=this.getPageData();
			String id=pd.getString("id");
			msStatConfigDetailSrv.delete(Integer.parseInt(id));//删除子表
			map.put("success", true);
			map.put("message","移除成功！");
		}catch(Exception ex){
			map.put("success", false);
			map.put("message","移除失败，原因：系统异常！");
		}
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/*********************  基本统计操作 fujl 2017-1-5 ++ end  ****************************/
	
	
	
	
	/*********************  图层操作 fujl 2017-1-5 ++ start ****************************/
	/**
	 * 获取图层列表
	 * @author fujl
	 * @time 2016-11-19
	 * @return
	 */
	@RequestMapping(value="/getLayers")
	@ResponseBody
	public Object getLayers(){
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
		    String layersStr=msArcgisSrv.getLayerListByMapService(pd.getString("serviceName[]"));
		    if(null==layersStr){
		    	map.put("success",false);				//返回结果
			    map.put("data",null);
		    }else{
		    	map.put("success",true);				//返回结果
			    map.put("data",layersStr);
		    }
		} catch(Exception e){
			map.put("success",false);				//返回结果
			map.put("data",null);
			logger.error(e.toString(), e);
		}
		map.put("pd", pd);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**
	 * 获取图层字段
	 * @author fujl
	 * @time 2016-11-19
	 * @return
	 */
	@RequestMapping(value="/getLayerFields")
	@ResponseBody
	public Object getLayerFields(){
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
		    String layersStr=msArcgisSrv.getLayerFieldsByLayerName(pd.getString("serviceName[]"),pd.getString("layerId[]"));
		    if(null==layersStr){
		    	map.put("success",false);				//返回结果
			    map.put("data",null);
		    }else{
		    	map.put("success",true);				//返回结果
			    map.put("data",layersStr);
		    }
		} catch(Exception e){
			map.put("success",false);				//返回结果
			map.put("data",null);
			logger.error(e.toString(), e);
		}
		map.put("pd", pd);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	//
	
	
	/**
	 * 门户维护
	 * @author fujl 2017-3-16
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/maintenance")
	public ModelAndView maintenance() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd=this.getPageData();
		mv.setViewName("portal/indexmaintenance");
		return mv;
	}
	
	/**
	 * 上传图片
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/maintenance_uploadimg")
	@ResponseBody
	public Object maintenance_uploadimg(HttpServletRequest getRequest,@RequestParam(required = false) MultipartFile file) {
		String suffixs = "jpg";
		PageData pd =this.getPageData();
		String fileName = "index_img";
		String picIndex=pd.getString("index");
		if(!StringUtils.isEmpty(picIndex)&&null!=picIndex){
			if("1".equals(picIndex)){
				fileName = "index_img1";
			}
			if("2".equals(picIndex)){
				fileName = "index_img2";
			}
		}
		String filePath = PathUtil.getClasspath() +"/static/portal/images/";
		Map<String,Object> map = new HashMap<String,Object>();
		String name = file.getOriginalFilename();
		String suffix = name.substring(name.lastIndexOf(".") + 1, name.length());
		if (suffixs.contains(suffix.toLowerCase())) {
			if (null != file && !file.isEmpty()) {
				FileUpload.fileUp(file, filePath, fileName);
				map.put("success", true);
				map.put("msg", "图片上传成功!");
			} else {
				map.put("success", false);
				map.put("msg", "上传失败，请重试！");
			}
		} else {
			map.put("success", false);
			map.put("msg", "请上传指定格式的图片!");
		}
		try {

		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "上传失败，系统错误！");
			e.printStackTrace();
		}
		return AppUtil.returnObject(new PageData(), map);
	}
	
	public PageData getPD()
	{
		PageData pd = new PageData();
		pd.put("SYSNAME", MsConfigProperties.App_Name); //读取系统名称
		pd.put("ARCGIS_JS_API", MsConfigProperties.ARCGIS_JS_API);
		pd.put("ARCGIS_URL", MsConfigProperties.ARCGIS_URL);
		return pd;
	}
}
