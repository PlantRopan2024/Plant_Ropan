package com.plants.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {
	
	@GetMapping("/userhome")
	public  String userhome() {
		return "userhome";
	}
	
	@GetMapping("/planManage")
	public  String planManage() {
		return "planManage";
	}
	@GetMapping("/addPlan")
	public  String addPlan() {
		return "addPlan";
	}
	@GetMapping("/AdminDashboard")
	public  String AdminDashboard() {
		return "AdminDashboard";
	}
	
	@GetMapping("/agentDetailPage.html")
	public  String agentDetailPage() {
		return "agentDetailPage.html";
	}
	@GetMapping("/agentVerfication")
	public  String agentVerfication(){
		return "agentVerfication";
	}
	
	@GetMapping("/test")
	public  String test(){
		return "test";
	}
}