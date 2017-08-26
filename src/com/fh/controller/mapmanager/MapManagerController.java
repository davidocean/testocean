package com.fh.controller.mapmanager;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fh.bean.cluster.Cluster;
import com.fh.bean.folder.Service;
import com.fh.bean.result.ResultObj;
import com.fh.bean.service.type.Type;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.ms.dict.MsDictCode;
import com.fh.entity.ms.res.MsResServicesClass;
import com.fh.entity.ms.services.MsServicesReg;
import com.fh.entity.system.User;
import com.fh.service.bean.MsArcgisService;
import com.fh.service.ms.dict.MsDictCodeService;
import com.fh.service.ms.res.MsResServicesClassService;
import com.fh.service.ms.services.MsServicesRegService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.FileUpload;
import com.fh.util.FileUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.PathUtil;
import com.mapone.MsConfigProperties;

import net.sf.json.JSONArray;

/**
 * 运维管理
 * 
 * @author mwhdds
 * @date 2016-10-14
 */
@Controller
@RequestMapping(value = "/mapmanager")
public class MapManagerController extends BaseController {

	@Resource(name = "msDictCodeService")
	private MsDictCodeService msDictCodeService;
	@Resource(name = "msServicesRegService")
	private MsServicesRegService msServicesRegService;
	@Resource(name = "msResServicesClassService")
	private MsResServicesClassService msResServicesClassService;
	MsArcgisService msArcgisService = new MsArcgisService();

	/**
	 * 去发送邮箱页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/index")
	public ModelAndView index() throws Exception {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("mapmanager/indexmanager");
		return mv;
	}

	/**
	 * 显示服务发布页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/publishindex")
	public ModelAndView serverpublish() throws Exception {
		ModelAndView mv = this.getModelAndView();
		// 服务引擎
		List<PageData> varList = new ArrayList<PageData>();
		PageData pd = new PageData();
		pd.put("a", "ArcGIS Server103");
		pd.put("b", MsConfigProperties.ARCGIS_SERVER_IP);
		pd.put("c", "启用");
		pd.put("d", "验证服务器连接成功");
		pd.put("e", "e");
		pd.put("f", "f");
		List<Cluster> clusters = msArcgisService.getServiceClusters().getClusters();
		mv.addObject("clusters", clusters);
		List<String> folders = msArcgisService.getFolders();
		mv.addObject("folders", folders);
		List<Type> types = msArcgisService.getServiceTypes();
		mv.addObject("types", types);
		varList.add(pd);
		mv.setViewName("mapmanager/publish/publishindex");
		mv.addObject("varList", varList);
		// 资源文件
		File dir = new File(MsConfigProperties.RESOURCE_DIR);
		String folder = dir.getName();
		List<String> filesList = FileUtil.listAllFiles(null, dir, 0, folder, "msd");
		mv.addObject("filesList", filesList);
		mv.addObject("resource_dir", dir.getAbsolutePath());
		List<MsDictCode> dictCodeList = msDictCodeService.listAll(new MsDictCode());
		mv.addObject("dictCodeList", dictCodeList);
		return mv;
	}

	/**
	 * 创建服务
	 * 
	 * @author ZhaiZhengqiang
	 * @date 2016-10-09
	 * @throws Exception
	 */
	@RequestMapping(value = "/createService")
	@ResponseBody
	public ModelAndView createService(String filePath, String serviceName, String serviceType, String clusterName,
			String description, String folderName) throws Exception {
		ModelAndView mv = this.getModelAndView();
		ResultObj resultObj = msArcgisService.createService(serviceName, serviceType, description, clusterName,
				filePath, folderName);
		mv.addObject("resultObj", resultObj);
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 验证服务是否已存在
	 * 
	 * @author ZhaiZhengqiang
	 * @date 2016-11-14
	 * @throws Exception
	 */
	@RequestMapping(value = "/existsService")
	@ResponseBody
	public ModelAndView existsService(String serviceName, String serviceType, String folderName) throws Exception {
		ModelAndView mv = this.getModelAndView();
		Boolean exists = msArcgisService.existsService(serviceName, serviceType, folderName);
		mv.addObject("exists", exists);
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 显示服务注册页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/register")
	public ModelAndView serverRegister(String serviceName, String serviceType) throws Exception {
		ModelAndView mv = this.getModelAndView();
		List<Cluster> clusters = msArcgisService.getServiceClusters().getClusters();
		mv.addObject("clusters", clusters);
		// List<Service> services = msArcgisService.getServices("/",null);
		List<Service> services = msServicesRegService.getAvailableServices();
		mv.addObject("services", services);
		List<MsResServicesClass> classList = msResServicesClassService.listAll(new MsResServicesClass());
		mv.addObject("classList", classList);
		mv.addObject("native_url", MsConfigProperties.ARCGIS_URL);
		mv.addObject("serviceName", serviceName);
		mv.addObject("serviceType", serviceType);
		mv.setViewName("mapmanager/publish/register");
		return mv;
	}
	
	/**
	 * 显示服务修改页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/serverupdate")
	public ModelAndView serverupdate(Integer id) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd.put("id", id);
		MsServicesReg msServicesReg=msServicesRegService.findById(pd);
		mv.addObject("ms",msServicesReg);
		List<MsResServicesClass> classList = msResServicesClassService.listAll(new MsResServicesClass());
		mv.addObject("classList", classList);
		mv.addObject("native_url", MsConfigProperties.ARCGIS_URL);
		mv.setViewName("mapmanager/publish/updateregister");
		return mv;
	}
	
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/getServiceCache")
	@ResponseBody
	public ModelAndView getServiceCache(String folderName,String serviceName,String serviceType) throws Exception{
		ModelAndView mv = this.getModelAndView();
		String serviceCache = msArcgisService.getServiceCache(folderName, serviceName, serviceType);
		mv.addObject("serviceCache", serviceCache);
		mv.setViewName("save_result");
		return mv;
	}
	
	@RequestMapping(value = "/updateRegister")
	@ResponseBody
	public ModelAndView updateRegister(String id, String clusterName, String servicefullName, String serviceData, int classid,
			String folder, String nativeurl, String servicecache, Integer fullpermission, String thumbnail,String description)
			throws Exception {
		PageData pd = this.getPageData();
		String[] serviceDataArray = serviceData.split("##");
		String serviceName = serviceDataArray[0];
		String serviceType = serviceDataArray[1];// 服务分类
		String folderName = serviceDataArray[2].equals("/") ? "/" : serviceDataArray[2];// 发布目录
		// 转发地址，也就是原始地址
		String transferurl ="/" + serviceName + "/" + serviceType;
		//by David.Ocean 添加上folderName     暂时不上用。
		//String transferurl =folderName+ "/" + serviceName + "/" + serviceType;
		System.out.println(serviceType);
		pd.put("id", id);
		pd.put("thumbnail", thumbnail);
		pd.put("resinfoid", "");
		pd.put("servicename", serviceName);
		pd.put("servicefullname", servicefullName);
		pd.put("servicetype", serviceType);
		pd.put("clustercode", clusterName);
		pd.put("fullpermission", fullpermission);
		pd.put("nativeurl", nativeurl);
		pd.put("transferurl", transferurl);
		pd.put("status", 1);
		pd.put("ifpublish", 1);
		pd.put("description", description);
		pd.put("folder", folderName);
		pd.put("servicecache", servicecache);
		pd.put("classid", classid);
		ModelAndView mv = this.getModelAndView();
		User user = this.getUser();
		if (user == null) {
			mv.addObject("status", "error");
		} else {
			pd.put("registerdman", user.getUSER_ID());
			pd.put("registerddate", new Date());
			msServicesRegService.updateReg(pd);
			mv.addObject("status", "success");
		}
		mv.setViewName("save_result");
		return mv;
	}

	@RequestMapping(value = "/createRegister")
	@ResponseBody
	public ModelAndView createRegister(String clusterName, String servicefullName, String serviceData, int classid,
			String folder, String nativeurl, String servicecache, Integer fullpermission, String thumbnail,String description)
			throws Exception {
		PageData pd = this.getPageData();
		String[] serviceDataArray = serviceData.split("##");
		String serviceName = serviceDataArray[0];
		String serviceType = serviceDataArray[1];// 服务分类
		String folderName = serviceDataArray[2].equals("/") ? "/" : serviceDataArray[2];// 发布目录
		// 转发地址，也就是原始地址
		//by David.Ocean 添加上folderName     暂时不上用。
		String transferurl ="/" + serviceName + "/" + serviceType;
		System.out.println(serviceType);
		pd.put("thumbnail", thumbnail);
		pd.put("resinfoid", "");
		pd.put("servicename", serviceName);
		pd.put("servicefullname", servicefullName);
		pd.put("servicetype", serviceType);
		pd.put("clustercode", clusterName);
		pd.put("fullpermission", fullpermission);
		pd.put("nativeurl", nativeurl);
		pd.put("transferurl", transferurl);
		pd.put("status", 1);
		pd.put("ifpublish", 1);
		pd.put("description", description);
		pd.put("folder", folderName);
		pd.put("servicecache", servicecache);
		pd.put("count", 0);
		ModelAndView mv = this.getModelAndView();
		User user = this.getUser();
		if (user == null) {
			mv.addObject("status", "error");
		} else {
			pd.put("registerdman", user.getUSER_ID());
			pd.put("registerddate", new Date());
			msServicesRegService.save(pd);
			mv.addObject("status", "success");
		}
		mv.setViewName("save_result");
		return mv;
	}
	

	/**
	 * 显示服务注册页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/manager")
	public ModelAndView serviceManager(Model model, String classid) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			JSONArray arr = JSONArray.fromObject(msResServicesClassService.listAllClass("0"));
			String json = arr.toString();
			json = json.replaceAll("id", "id").replaceAll("parentid", "pId").replaceAll("classname", "name")
					.replaceAll("subMsResServicesClass", "nodes").replaceAll("hasMsResServicesClass", "checked")
					.replaceAll("treeurl", "url");
			model.addAttribute("zTreeNodes", json);
			mv.addObject("classid", classid);
			mv.addObject("pd", pd);
			mv.setViewName("mapmanager/service/manager");
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	@RequestMapping(value = "/listServicesReg")
	public ModelAndView listServicesReg(Page page, String classid) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd.put("classid", StringUtils.isBlank(classid) ? 1 : classid);
		page.setPd(pd);
		
		List<PageData> varList = msServicesRegService.listPageByClassId(page);
		mv.addObject("pd", pd);// 上级ID
		mv.addObject("native_url",
				MsConfigProperties.ARCGIS_URL.lastIndexOf("/") + 1 == MsConfigProperties.ARCGIS_URL.length()
						? MsConfigProperties.ARCGIS_URL.subSequence(0, MsConfigProperties.ARCGIS_URL.length() - 1)
						: MsConfigProperties.ARCGIS_URL);
		mv.addObject("transfer_url", MsConfigProperties.TRANSFER_URL + "/rest/services");
		mv.setViewName("mapmanager/service/list_services_reg");
		mv.addObject("varList", varList);
		return mv;
	}

	/**
	 * 开启服务
	 * 
	 * @param regids
	 * @return
	 */
	@RequestMapping(value = "/startServices")
	@ResponseBody
	public ModelAndView startServices(String regids) {
		ModelAndView mv = this.getModelAndView();
		try {
			ResultObj obj = msServicesRegService.startServices(regids.split(","));
			mv.addObject("obj", obj);
			mv.setViewName("save_result");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	/**
	 * 停止服务
	 * 
	 * @param regids
	 * @return
	 */
	@RequestMapping(value = "/stopServices")
	@ResponseBody
	public ModelAndView stopServices(String regids) {
		ModelAndView mv = this.getModelAndView();
		try {
			ResultObj obj = msServicesRegService.stopServices(regids.split(","));
			mv.addObject("obj", obj);
			mv.setViewName("save_result");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	/**
	 * 删除服务
	 * @param regids
	 * @return
	 */
	@RequestMapping(value = "/deleteServices")
	@ResponseBody
	public ModelAndView deleteServices(String regids, Boolean deleteArcFlag) {
		ModelAndView mv = this.getModelAndView();
		try {
			Boolean flag = msServicesRegService.deleteServices(regids.split(","), deleteArcFlag);
			mv.addObject("flag", flag);
			mv.setViewName("save_result");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	/**
	 * 上传图片
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/uploadimg")
	@ResponseBody
	public ModelAndView uploadimg(Model model, @RequestParam(required = false) MultipartFile file) {
		String suffixs = "bmp,jpg,gif,png,pcx,dcx,emf,jif,jpe,jfif,eps,tif,jdeg,rle,dib,pcd,dxf,ico,wmf,tiff,tga";
		String ffile = DateUtil.getDays();
		String fileName = "";
		String filePath = "";
		ModelAndView mv = this.getModelAndView();
		String name = file.getOriginalFilename();
		System.out.println(name);
		String suffix = name.substring(name.lastIndexOf(".") + 1, name.length());
		System.out.println(suffix.toLowerCase());
		if (suffixs.contains(suffix.toLowerCase())) {
			if (null != file && !file.isEmpty()) {
				filePath = PathUtil.getClasspath() +"/"+ Const.FILEPATHIMG + "mapresource/" + ffile;// 文件上传路径
				System.out.println(filePath);
				fileName = FileUpload.fileUp(file, filePath, this.get32UUID());
				System.out.println("上传成功");// 执行上传
				mv.addObject("filePath", "/"+Const.FILEPATHIMG + "mapresource/" + ffile + "/" + fileName);
				System.out.println(fileName);
				mv.addObject("msg", "true");
			} else {
				System.out.println("上传失败");
				mv.addObject("msg", "flase");
			}
		} else {
			mv.addObject("msg", "flaseURL");
			System.out.println("上传的文件不是图片");
		}
		try {

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mv;
	}
	/**查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/update")
	public ModelAndView update(Integer id) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd.put("id", id);
		MsServicesReg msServicesReg=msServicesRegService.findById(pd);
		mv.addObject("pd",msServicesReg);	
		mv.setViewName("mapmanager/service/serviceupdate");
		return mv;
	}	
	
	/**归档
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/newupdate")
	@ResponseBody
	public ModelAndView newupdate(Integer id,String parentname,String servicefullname,String servicename,String desversion,String version,String classid) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pdclass = new PageData();
		PageData pdclassid = new PageData();
		System.out.println("2222222222");
		pdclassid.put("classname","历史库");
		pdclassid.put("classid",classid);
		System.out.println("+++++++++"+classid);
		List<MsResServicesClass> ms1=msResServicesClassService.find(pdclassid);
		if(ms1.size()==0){
		pdclass.put("id", null);
		pdclass.put("parentid",classid);
		pdclass.put("classname", "历史库");
		pdclass.put("sort", 30);
		pdclass.put("classtype", "无");
		pdclass.put("memo", "无");
		msResServicesClassService.save(pdclass);
		}
		List<MsResServicesClass> ms=msResServicesClassService.find(pdclassid);
		MsResServicesClass b = ms.get(0);
		PageData pd = this.getPageData();
		pd.put("id", id);
		pd.put("parentname", parentname);
		pd.put("servicefullname", servicefullname);
		pd.put("servicename", servicename);
		pd.put("desversion",desversion);
		pd.put("version", version);
		pd.put("classid", b.getId());
		User user = this.getUser();
		if (user == null) {
			mv.addObject("status", "error");
		} else {
			msServicesRegService.update(pd);
			mv.addObject("status", "success");
		}
		return mv;
	}	
	
	
	/**资源详情
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/detail")
	public ModelAndView detail(String servicename) throws Exception{
		System.out.println(11111111);
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd.put("servicename", servicename);
		pd.put("parentname", "历史库");
		List<MsServicesReg> msServicesReg=msServicesRegService.find(pd);
		mv.addObject("pd",msServicesReg);	
		mv.setViewName("mapmanager/service/servicelist");
		return mv;
	}	
	
	/**资源详情
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/updateClass")
	public ModelAndView updateClass(String id,String classid) throws Exception{
		System.out.println(id);
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		PageData pdclassid = this.getPageData();
		pd.put("id", id);
		pdclassid.put("classid", classid==""?0:classid);
		if(classid!=null&&classid!=""){
			mv.addObject("classid",classid);
		}
		if(id==null&&classid==""){
			System.out.println(00000);
			mv.addObject("classid",0);
		}
		pdclassid=msResServicesClassService.findByClassid(pdclassid);
		pd=msResServicesClassService.findById(pd);
		mv.addObject("pdclassid",pdclassid);	
		mv.addObject("pd",pd);	
		mv.setViewName("mapmanager/service/list_services_reg_class_edit");
		return mv;
	}	
	
	
	@RequestMapping(value = "/listServicesRegClass")
	public ModelAndView listServicesRegClass(Page page, String classid) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		System.out.println(classid);
		pd.put("parentId",classid);
		page.setPd(pd);
		List<PageData> varList = msResServicesClassService.parentid(page);
		mv.addObject("pd", pd);// 上级ID
		mv.addObject("varList", varList);
		PageData pdclassid = this.getPageData();
		pdclassid.put("classid", classid==""?0:classid);
		pdclassid=msResServicesClassService.findByClassid(pdclassid);
		mv.addObject("classid", classid==""?0:classid);
		mv.addObject("parentId", pdclassid);
		mv.setViewName("mapmanager/service/list_services_reg_class_list");
		return mv;
	}

	/**
	 * 显示服务注册页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/servicesreg")
	public ModelAndView servicesreg(Model model, String classid) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			JSONArray arr = JSONArray.fromObject(msResServicesClassService.allListClass("0"));
			String json = arr.toString();
			json = json.replaceAll("id", "id").replaceAll("parentid", "pId").replaceAll("classname", "name")
					.replaceAll("subMsResServicesClass", "nodes").replaceAll("hasMsResServicesClass", "checked")
					.replaceAll("treeurl", "url");
			model.addAttribute("zTreeNodes", json);
			mv.addObject("classid", classid);
			mv.addObject("pd", pd);
			mv.setViewName("mapmanager/service/services_reg");
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	
	/**
	 * 保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatesaveClass")
	@ResponseBody
	public ModelAndView updatesaveClass(Model model,String id,String parentid,String classname,String sort) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd.put("id", id==""?null:id);
		pd.put("parentid", parentid);
		pd.put("classname", classname);
		pd.put("classtype", "无");
		pd.put("sort", sort==""?0:sort);
		pd.put("memo", "无");
		List<MsResServicesClass> ms= msResServicesClassService.findByClassname(classname);
		System.out.println("231232"+ms);
		if(id==null||id==""){
			if(ms.size()==0){
		    msResServicesClassService.save(pd);
			mv.addObject("status", "success");
			}else{
				System.out.println("11111");
				mv.addObject("status", "error");
			}
	    }else{
	    	msResServicesClassService.update(pd);
	    	mv.addObject("status", "success");
		}
		mv.setViewName("mapmanager/service/services_reg");
		return mv;
	}
	
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除Class");
		if(!Jurisdiction.buttonJurisdiction("mapmanager/service/list_services_reg_class_list", "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			msResServicesClassService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
/*	*//**删除
	 * @param out
	 * @throws Exception
	 *//*
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除");
		if(!Jurisdiction.buttonJurisdiction("mapmanager/service/list_services_reg_class_list", "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		msResServicesClassService.delete(pd);
		out.write("success");
		out.close();
	}*/
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public ModelAndView delete(String id) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd.put("id", id);
		PageData pid = new PageData();
		pid.put("parentId", id);
		pid= msResServicesClassService.findByIn(pid);
		System.out.println("shsh"+pid);
		PageData pdcl = this.getPageData();
		pdcl.put("classid",id);
		Page page=new Page();
		page.setPd(pdcl);
		List<PageData> varList = msServicesRegService.listPageByClassId(page);
		System.out.println("4646546465464"+varList);
		if(pid==null&&varList.size()==0){
		msResServicesClassService.delete(pd);
		mv.addObject("status", "success");
		}else{
		mv.addObject("status", "error");
		}
		mv.setViewName("mapmanager/service/services_reg");
		return mv;
	}
	
}