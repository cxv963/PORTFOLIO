package kr.inhatc.spring.chart.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;


@Controller
@RequestMapping("/")
public class CanvasJsChartController {
	 /**
		 * 
		 */
	
	private static final long serialVersionUID = 1L;
	
	public String principalData(Principal principal) {
		return principal.getName();
	}

	
	@RequestMapping(value = "/chart", method = RequestMethod.GET)
	public String getDataPoints(HttpServletRequest request, HttpServletResponse response, Model model, Principal principal) throws IOException{	
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
			
		HttpSession session = request.getSession();
		
		Gson gsonObj = new Gson();
		Map<Object,Object> map = null;
		List<Map<Object,Object>> list = new ArrayList<Map<Object,Object>>();
		 
		map = new HashMap<Object,Object>(); map.put("x", 10); map.put("y", 31); list.add(map);
		map = new HashMap<Object,Object>(); map.put("x", 20); map.put("y", 65); list.add(map);
		map = new HashMap<Object,Object>(); map.put("x", 30); map.put("y", 40); list.add(map);
		map = new HashMap<Object,Object>(); map.put("x", 40); map.put("y", 84); map.put("indexLabel", "Highest"); list.add(map);
		map = new HashMap<Object,Object>(); map.put("x", 50); map.put("y", 68); list.add(map);
		map = new HashMap<Object,Object>(); map.put("x", 60); map.put("y", 64); list.add(map);
		map = new HashMap<Object,Object>(); map.put("x", 70); map.put("y", 38); list.add(map);
		map = new HashMap<Object,Object>(); map.put("x", 80); map.put("y", 71); list.add(map);
		map = new HashMap<Object,Object>(); map.put("x", 90); map.put("y", 54); list.add(map);
		map = new HashMap<Object,Object>(); map.put("x", 100); map.put("y", 60); list.add(map);
		map = new HashMap<Object,Object>(); map.put("x", 110); map.put("y", 21); map.put("indexLabel", "Lowest"); list.add(map);
		map = new HashMap<Object,Object>(); map.put("x", 120); map.put("y", 49); list.add(map);
		map = new HashMap<Object,Object>(); map.put("x", 130); map.put("y", 41); list.add(map);
		
		String dataPoints = gsonObj.toJson(list);
				
		session.setAttribute("dataPoints", dataPoints);
		System.out.println(dataPoints);		
		
		if(principal == null) {
			model.addAttribute("testname","not");
			System.out.println("===========> 유저정보");
		}else {
			System.out.println("============> 유저정보"+ principalData(principal));
			model.addAttribute("testname",principalData(principal));
		}
		
		return "chart/grafico2";
	}
}
