package com.fh.service.ms.services;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.fh.bean.result.ResultObj;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.ms.services.MsServicesReg;
import com.fh.service.bean.MsArcgisService;
import com.fh.util.PageData;

/**
 * 服务注册
 * @author ZhaiZhengqiang
 * @date 2016-11-04
 */
@Service("msServicesRegService")
public class MsServicesRegServiceImpl implements MsServicesRegService {
	
	MsArcgisService msArcgisService = new MsArcgisService();
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("MsServicesRegMapper.save", pd);
	}
	
	/**删除
	 * @param id
	 * @throws Exception
	 */
	public void delete(Integer id)throws Exception{
		dao.delete("MsServicesRegMapper.delete", id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<MsServicesReg> listAll(MsServicesReg pd) throws Exception {
		return (List<MsServicesReg>)dao.findForList("MsServicesRegMapper.listAll", pd);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<MsServicesReg> listAllAuthority(PageData pd) throws Exception {
		return (List<MsServicesReg>)dao.findForList("MsServicesRegMapper.listAllAuthority", pd);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>)dao.findForList("MsServicesRegMapper.datalistPage", page);
	}
	
	
	
	/**
	 * 根据classid查询列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listPageByClassId(Page page)throws Exception{
		return (List<PageData>)dao.findForList("MsServicesRegMapper.listPageByClassId", page);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MsServicesReg> findByIds(String[] regids)throws Exception{
		return (List<MsServicesReg>)dao.findForList("MsServicesRegMapper.findByIds", regids);
	}
	
	/**
	 * 启动服务
	 * @param regids
	 * @throws Exception
	 */
	@Override
	public ResultObj startServices(String[] regids) throws Exception{
		List<MsServicesReg> services = this.findByIds(regids);
		ResultObj obj = msArcgisService.startServices(services);
		if(obj.getStatus().equals(ResultObj.SUCCESS) ){
			for(String id : regids){
				this.updateStatus(Integer.parseInt(id), 1);
			}
		}
		return obj;
	}
	
	/**
	 * 停止服务
	 * @param regids
	 * @throws Exception
	 */
	@Override
	public ResultObj stopServices(String[] regids) throws Exception{
		List<MsServicesReg> services = this.findByIds(regids);
		ResultObj obj = msArcgisService.stopServices(services);
		if(obj.getStatus().equals(ResultObj.SUCCESS) ){
			for(String id : regids){
				this.updateStatus(Integer.parseInt(id), 0);
			}
		}
		return obj;
	}
	
	/**
	 * 删除服务
	 * @param regids
	 * @throws Exception
	 */
	@Override
	public Boolean deleteServices(String[] regids,Boolean deleteArcFlag) throws Exception{
		List<MsServicesReg> services = this.findByIds(regids);
		ResultObj obj = new ResultObj();
		for(MsServicesReg reg : services){
			if(deleteArcFlag == true){
				obj = msArcgisService.deleteService(reg);
				if(obj.getStatus().equals(ResultObj.SUCCESS)){
					this.delete(reg.getId());
				}else {
					return false;
				}
			}else {
				this.delete(reg.getId());
			}
		}
		return true;
	}
	
	/**
	 * 更新服务状态
	 * @param id
	 * @param status 0 未启动，1启动
	 * @throws Exception
	 */
	public void updateStatus(Integer id,int status)throws Exception{
		PageData pd = new PageData();
		pd.put("id", id);
		pd.put("status", status);
		dao.update("MsServicesRegMapper.updateStatus", pd);
	}
	
	/**批量删除
	 * @param regids
	 * @throws Exception
	 */
	public void deleteAll(String[] regids)throws Exception{
		dao.delete("MsServicesRegMapper.deleteAll", regids);
	}
	
	/**
	 * 获取可用 的服务
	 * @return
	 * @throws Exception
	 */
	public List<com.fh.bean.folder.Service> getAvailableServices() throws Exception{
		List<com.fh.bean.folder.Service> services = msArcgisService.getServices("/",null);
		List<MsServicesReg> regList = this.listAll(null);
		List<com.fh.bean.folder.Service> servicesList = new ArrayList<com.fh.bean.folder.Service>();
		if(services != null){
			for(com.fh.bean.folder.Service s : services){
				boolean flag = false;
				if(regList != null){
					for(MsServicesReg reg : regList){
						System.out.println(reg.getParentname());
						if(s.getServiceName().equals(reg.getServicename()) && s.getType().equals(reg.getServicetype()) 
								&& (reg.getParentname()==null||reg.getClassid()!=2)){
							flag = true;
							break;
						}
					}
				}
				if(!flag)
					servicesList.add(s);
			}
		}
		return servicesList;
	}

	@Override
	public MsServicesReg findById(PageData pd) throws Exception {
		return (MsServicesReg)dao.findForObject("MsServicesRegMapper.findById", pd);
	}

//	@Override
//	public void updatecount(PageData pd) throws Exception {
//		 dao.update("MsServicesRegMapper.updateCount", pd);
//	}
	
	@Override
	public void updatecount(String  servicename,String servicetype)throws Exception{
		PageData pd = new PageData();
		pd.put("servicename", servicename);
		pd.put("servicetype", servicetype);
		dao.update("MsServicesRegMapper.updateCount", pd);
	}

	@Override
	public void update(PageData pd) throws Exception {
		dao.update("MsServicesRegMapper.update", pd);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MsServicesReg> find(PageData pd) throws Exception {
		return (List<MsServicesReg>)dao.findForList("MsServicesRegMapper.find", pd);
		
	}

	@Override
	public void updateReg(PageData pd) throws Exception {
		dao.update("MsServicesRegMapper.updateReg", pd);
	}
	
	
}
