package com.fh.service.ms.res;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.ms.res.MsResServicesClass;
import com.fh.util.PageData;
/**
 * 服务分类
 * @author ZhaiZhengqiang
 * @date 2016-11-06
 */
@Service("msResServicesClassService")
public class MsResServicesClassServiceImpl implements MsResServicesClassService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("MsResServicesClassMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("MsResServicesClassMapper.delete", pd);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<MsResServicesClass> listAll(MsResServicesClass pd) throws Exception {
		return (List<MsResServicesClass>)dao.findForList("MsResServicesClassMapper.listAll", pd);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>)dao.findForList("MsResServicesClassMapper.datalistPage", page);
	}
	
	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<MsResServicesClass> listAllClass(String parentId) throws Exception {
		List<MsResServicesClass> classList = this.listSubClassByParentId(parentId);
		for(MsResServicesClass sclass : classList){
			sclass.setTreeurl("mapmanager/listServicesReg.do?classid="+sclass.getId());
			sclass.setSubMsResServicesClass(this.listAllClass(sclass.getId()));
			sclass.setTarget("treeFrame");
		}
		return classList;
	}
	

	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<MsResServicesClass> allListClass(String parentId) throws Exception {
		List<MsResServicesClass> classList = this.listSubClassByParentId(parentId);
		for(MsResServicesClass sclass : classList){
			sclass.setTreeurl("mapmanager/listServicesRegClass.do?classid="+sclass.getId());
			sclass.setSubMsResServicesClass(this.allListClass(sclass.getId()));
			sclass.setTarget("treeFrame");
		}
		return classList;
	}
	
	
	/**
	 * 通过ID获取其子级列表
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<MsResServicesClass> listSubClassByParentId(String parentId) throws Exception {
		return (List<MsResServicesClass>) dao.findForList("MsResServicesClassMapper.listSubClassByParentId", parentId);
	}

	/**
	 * 获取大于指定parentI在所有数据
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<MsResServicesClass> listSubClassGTParentId(Integer parentId) throws Exception {
		return (List<MsResServicesClass>) dao.findForList("MsResServicesClassMapper.listSubClassGTParentId", parentId);
	}
	
	
	public PageData findById(PageData pd) throws Exception {
		return (PageData)dao.findForObject("MsResServicesClassMapper.findById", pd);
	}

	@SuppressWarnings("unchecked")
	public List<PageData> parentid(Page page) throws Exception {
		return (List<PageData>)dao.findForList("MsResServicesClassMapper.servicelist", page);
	}

	@Override
	public void update(PageData pd) throws Exception {
		dao.update("MsResServicesClassMapper.update", pd);
		
	}

	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("MsResServicesClassMapper.deleteAll", ArrayDATA_IDS);
	}

	@Override
	public PageData findByClassid(PageData pdclassid) throws Exception {
		return (PageData)dao.findForObject("MsResServicesClassMapper.findByClassid", pdclassid);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MsResServicesClass> findByClassname(String classname) throws Exception {
		return (List<MsResServicesClass>) dao.findForList("MsResServicesClassMapper.findByClassname", classname);
	}

	@Override
	public PageData findByIn(PageData pdclassid) throws Exception {
		return (PageData)dao.findForObject("MsResServicesClassMapper.findByIn", pdclassid);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MsResServicesClass> find(PageData pd) throws Exception {
		return (List<MsResServicesClass>) dao.findForList("MsResServicesClassMapper.find", pd);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<MsResServicesClass> listRoot(MsResServicesClass pd) throws Exception {
		return (List<MsResServicesClass>)dao.findForList("MsResServicesClassMapper.listRoot", pd);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<MsResServicesClass> getIdByParentId(String id) throws Exception {
		return (List<MsResServicesClass>)dao.findForList("MsResServicesClassMapper.getIdByParentId", id);
	}
}
