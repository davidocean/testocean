package com.fh.controller.statis;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
/**
 * 门户
 * @author ZhaiZhengqiang
 * @date 2016-11-11
 */
@Controller
@RequestMapping(value="/statis")
public class MapStatisController extends BaseController {

	@RequestMapping(value="/access")
	public ModelAndView index() throws Exception{
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("mapmanager/statis/access");
		return mv;
	}
}
