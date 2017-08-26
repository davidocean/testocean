package com.fh.controller.mapresource;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.ms.res.MsResServicesClass;
import com.fh.entity.ms.services.MsServicesReg;
import com.fh.entity.system.User;
import com.fh.service.ms.res.MsResServicesClassService;
import com.fh.service.ms.services.MsServicesRegService;
import com.fh.service.ms.stat.MsStatServices;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.mapone.MsConfigProperties;

/**
 * 资源管理
 * @author mwhdds
 * @date 2016-10-14
 * @modify 2016-11-12
 */

@Controller
@RequestMapping(value="/mapresource")
public class MapResourceController extends BaseController {
	@Resource(name="msServicesRegService")
	private MsServicesRegService msServicesRegService;
	@Resource(name="msResServicesClassService")
	private MsResServicesClassService msResServicesClassService;
	@Resource(name = "msStatServices")
	private MsStatServices msStatServices;
	/**资源管理首页
	 * @return
	 * @throws Exception
	 */
/*	@RequestMapping(value="/index")
	public ModelAndView index() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd.put("SYSNAME", MsConfigProperties.App_Name); //读取系统名称
		pd.put("COPYRIGHT", MsConfigProperties.CopyRight); //读取系统名称
		mv.addObject("pd",pd);
		List<MsResServicesClass> list = msResServicesClassService.listAll(null);
		mv.addObject("list",list);
		mv.setViewName("mapresource/indexresource");
		return mv;
	}*/
	
	/**资源详情
	 * @return
	 * @throws Exception
	 */
/*	@RequestMapping(value="/detail")
	public ModelAndView detail(Integer id) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd.put("id", id);
		MsServicesReg msServicesReg=msServicesRegService.findById(pd);
		pd.put("count", msServicesReg.getCount()==null?0:msServicesReg.getCount());
		msServicesRegService.updatecount(pd);
		mv.addObject("transfer_url",MsConfigProperties.TRANSFER_URL+"/rest/services");
		mv.addObject("pd",msServicesReg);		
		mv.setViewName("mapresource/servicedetail");
		return mv;
	}	*/
	
	@RequestMapping(value="/index")
	public ModelAndView index(Page page,String classid,String word) throws Exception {
		//fujl 2017-2-11 注释掉后面的代码 
		//List<MsResServicesClass> list=msResServicesClassService.listAll(null);
		List<MsResServicesClass> list =msResServicesClassService.listSubClassByParentId("0");//fujl 2017-2-11 ++ 获取服务资源顶级目录
		List<MsResServicesClass> sublist =msResServicesClassService.listSubClassGTParentId(1);//fujl 2017-3-16 ++ //获取所有服务的子目录
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd.put("classid", StringUtils.isBlank(classid) ? null : classid);
		Integer a=0;
		pd.put("orderfield", word);
		/*--fujl 2017-3-15 增加版权 start--*/
		pd.put("COPYRIGHT", Const.SYS_COPYRIGHT);
		/*--fujl 2017-3-15 增加版权 end --*/
		page.setPd(pd);
		List<PageData> varList = msServicesRegService.listPageByClassId(page);
		
		List<PageData> accessCountList = msStatServices.listStatServices(pd);
		for(PageData vpd : varList){
			vpd.put("count", 0);
			for(PageData apd : accessCountList){
				if(vpd.get("servicename").toString().equalsIgnoreCase(apd.get("servicename").toString())){
					vpd.put("count", apd.get("accesscount").toString());
					break;
				}
			}
		}
		
//		for(int i=0;i<varList.size();i++){
//			PageData reg = varList.get(i);
//			System.out.println(reg.getString("servicename")+"########"+reg.getString("servicetype"));
//			msServicesRegService.updatecount(reg.getString("servicename"),reg.getString("servicetype"));
//		}
		mv.addObject("pd", pd);//上级ID
		mv.addObject("native_url",MsConfigProperties.ARCGIS_URL.lastIndexOf("/")+1 == MsConfigProperties.ARCGIS_URL.length() ? MsConfigProperties.ARCGIS_URL.subSequence(0, MsConfigProperties.ARCGIS_URL.length()-1) : MsConfigProperties.ARCGIS_URL);
		mv.addObject("transfer_url",MsConfigProperties.TRANSFER_URL+"/rest/services");
		mv.addObject("varList", varList);
		mv.addObject("list",list);
		mv.addObject("sublist",sublist);//fujl 2017-3-16 ++
		mv.addObject("word",word);
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
		
		mv.setViewName("mapresource/indexresource");

		return mv;
	}
	
	
}
