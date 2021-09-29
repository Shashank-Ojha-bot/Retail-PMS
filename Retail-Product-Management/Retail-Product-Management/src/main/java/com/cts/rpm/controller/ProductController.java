package com.cts.rpm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.cts.rpm.feign.AuthorisingClient;
import com.cts.rpm.feign.ProductClient;
import com.cts.rpm.model.Product;

import feign.FeignException;

@Controller
public class ProductController {
	
	@Autowired
	private ProductClient productClient;
	
	@GetMapping("/viewProducts")
	public ModelAndView viewAllProducts(HttpServletRequest request) {
		
	
		if(request.getSession().getAttribute("Authorization") == null) {
			
			ModelAndView model=new ModelAndView("session-tiemout");
			return model;
		}
		ModelAndView model = new ModelAndView("index");
		Map<String, ?> flashMap=  RequestContextUtils.getInputFlashMap(request);
        if(flashMap!=null) {
        	model.addObject("Message", flashMap.get("Message"));
        }
		List<Product> productList=productClient.getAllProducts((String) request.getSession().getAttribute("Authorization"));
		//productList.stream().forEach((x)-> System.out.println("Image url  "+x.getImageName()));
		
		model.addObject("productList", productList);
		return model;
	}

	@GetMapping("/productsByName")
	public ModelAndView viewAllProductsByName(HttpServletRequest request,@RequestParam String productName,RedirectAttributes redirectAttributes) {
		
	
		if(request.getSession().getAttribute("Authorization") == null) {
			
			ModelAndView model=new ModelAndView("session-tiemout");
			return model;
		}
		
		ModelAndView model = new ModelAndView();
		List<Product> productList=null;
		
		try {
		productList=productClient.getProductByName(productName,(String) request.getSession().getAttribute("Authorization"));
		model.setViewName("index");
		model.addObject("productList", productList);
		}
		catch(FeignException ex) {
			if(ex.getMessage().contains(productName)) {
				String str=ex.getMessage().split("message")[1].split(":")[1];
				String str1=str.substring(1,str.length()-3);
				model.addObject("Message", str1);
				redirectAttributes.addFlashAttribute("Message", str1);
				model.setViewName("redirect:/viewProducts");
			}
		}
		//productList.stream().forEach((x)-> System.out.println("Image url  "+x.getImageName()));
		
		
		return model;
	}

	@GetMapping("/productById")
	public ModelAndView viewAllProductsById(HttpServletRequest request,@RequestParam Integer productId,RedirectAttributes redirectAttributes) {
		
	
		if(request.getSession().getAttribute("Authorization") == null) {
			
			ModelAndView model=new ModelAndView("session-tiemout");
			return model;
		}
		
		ModelAndView model = new ModelAndView();
		Product product=null;
		List<Product> productList=new ArrayList<Product>();
		try {
		product=productClient.getProductById(productId, (String) request.getSession().getAttribute("Authorization"));
		productList.add(product);
		model.setViewName("index");
		model.addObject("productList", productList);
		}
		catch(FeignException ex) {
			
			if(ex.getMessage().contains("not found")) {
				String str=ex.getMessage().split("message")[1].split(":")[1];
				String str1=str.substring(1,str.length()-3);
				model.addObject("Message", str1);
				redirectAttributes.addFlashAttribute("Message", str1);
				model.setViewName("redirect:/viewProducts");
			}
		}
		//productList.stream().forEach((x)-> System.out.println("Image url  "+x.getImageName()));
		
		
		return model;
	}

}
