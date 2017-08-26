/**  
 * Copyright © Jes All rights reserved.
 *
 * @Title: MsServicesLogMergedServiceImpl.java
 * @Prject: OceanServer
 * @author: Jes  
 * @date: 2016年11月26日 下午6:40:56
 * @version: V1.0  
 */
package com.fh.service.ms.logs;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.ms.logs.MsServicesLogMerged;
import com.fh.entity.ms.logs.MsServicesLogMergedPK;

/**
 * @author Jes
 * 		
 */
@Service("msServicesLogMergedService")
public class MsServicesLogMergedServiceImpl implements MsServicesLogMergedService {
	
	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@Override
	public void saveObj(MsServicesLogMerged logMerged) throws Exception {
		dao.save("MsServicesLogMergedMapper.saveObj", logMerged);
	}
	
	@Override
	public void getById(MsServicesLogMergedPK pkid) throws Exception {
		dao.findForObject("MsServicesLogMergedMapper.getByID", pkid);
		
	}
	
	@Override
	public void updateById(MsServicesLogMerged logMerged) throws Exception {
		dao.update("MsServicesLogMergedMapper.updateById", logMerged);
		
	}
	
	@Override
	public void saveOrUpdateBatch(List<MsServicesLogMerged> list) throws Exception {
		
		SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
		// 批量执行器
		SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
		try {
			if (null != list && list.size() != 0) {
				for (int i = 0, size = list.size(); i < size; i++) {
					MsServicesLogMerged logMerged = list.get(i);
					if (null != sqlSession.selectOne("MsServicesLogMergedMapper.getByID", logMerged.getPkid())) {
						sqlSession.update("MsServicesLogMergedMapper.updateById", logMerged);
					} else {
						sqlSession.insert("MsServicesLogMergedMapper.saveObj", logMerged);
					}
				}
				sqlSession.flushStatements();
				sqlSession.commit();
				sqlSession.clearCache();
			}
		} finally {
			sqlSession.close();
		}
	}
	
}
