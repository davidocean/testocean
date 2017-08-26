package com.fh.controller.ms;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.system.User;
import com.fh.service.ms.services.MsGisServerConfService;
import com.fh.service.system.user.UserManager;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.mapone.MsConfigProperties;

/**
 * 服务引擎配置
 * @author fujl 
 * @date 2016-11-13 ++
 */
@Controller
@RequestMapping(value="/gisserviceconf")
public class GisServiceConfController extends BaseController {

	//注入GIS服务器配置服务 fujl 2016-11-16 ++
	@Resource(name="msGisServerConfService")
	private MsGisServerConfService gisServerConfSrv;
	
	
	/**
	 * 加载服务引擎配置编辑页面
	 * @author fujl 
	 * @time 2016-11-16 ++
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd=this.getPageData();
		List<PageData> list=gisServerConfSrv.list(this.getPage());
		if(null!=list&&list.size()>0){
			pd=list.get(0);
		}
		mv.addObject("msg","");
		mv.addObject("pd",pd);	
		mv.setViewName("ms/gisserviceconf/serverengine_conf");
		return mv;
	}
	
	/**
	 * 保存服务器配置
	 * @author fujl 
	 * @time 2016-11-16 22:44
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/saveConf")
	public ModelAndView saveConf() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"保存GIS服务器配置");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		User loginUser=Jurisdiction.getLoginUser();
		String id=pd.getString("id");
		pd.put("enabled",1);
		pd.put("sortcode",1);
		pd.put("deletemark",0);
		pd.put("modifydate",new Date());	
		pd.put("modifyuserid",null==loginUser?"":loginUser.getUSER_ID());				
		pd.put("modifyusername", null==loginUser?"":loginUser.getUSER_ID());
		if(null==id||StringUtils.isEmpty(id)||StringUtils.isBlank(id)){
			pd.remove("id");
			pd.put("createdate",new Date());	
			pd.put("createuserid",null==loginUser?"":loginUser.getUSER_ID());				
			pd.put("createusername", null==loginUser?"":loginUser.getUSER_ID());
			gisServerConfSrv.save(pd);//执行保存
		}else{
			pd.put("createdate",new Date());	
			pd.put("createuserid",null==loginUser?"":loginUser.getUSER_ID());				
			pd.put("createusername", null==loginUser?"":loginUser.getUSER_ID());
			gisServerConfSrv.update(pd);//执行保存
		}
		mv.addObject("msg","success");
		mv.addObject("pd",pd);	
		mv.setViewName("ms/gisserviceconf/serverengine_conf");
		return mv;
	}
}
