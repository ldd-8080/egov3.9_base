package egovframework.com.sub.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.com.cmmn.SecurityUtil;
import egovframework.com.sub.service.SubService;
import egovframework.com.sub.vo.SubVo;

@Controller
@RequestMapping(value = "/sub")
public class SubController {

	@Resource(name = "subService")
	private SubService subService;
	
	@RequestMapping(value = "/sub.do")
	public String test(ModelMap model) {
		System.out.println("---------- SubController test() sub/sub.do ----------");
		model.addAttribute("subVo", new SubVo());
		return "sub/sub";
	}
	
	
	@RequestMapping(value = "/subs.do", method = RequestMethod.POST)
	//@ModelAttribute는 생략해도  무관하나 명시적으로 붙여는것이 좋
	public String test2(@ModelAttribute @Valid SubVo vo, BindingResult result) {
		System.out.println("---------- SubController test2() sub/subs.do ----------");
		System.out.println("vo userId : " + vo.getEmail());
		
		if (result.hasErrors()) {
			System.out.println("result Error : " + result.getFieldError().getDefaultMessage());
			return "sub/sub";
		}
		
		return "main/main";
	}
	
	
	@RequestMapping(value = "/login.do")
	public String login(@ModelAttribute @Valid SubVo vo, BindingResult result, ModelMap model) throws Exception {
		System.out.println("---------- SubController login() sub/login.do ----------");
		
		if (result.hasErrors()) {
			System.out.println("result Error : " + result.getFieldError().getDefaultMessage());
			return "sub/sub";
		}
		
		SubVo subVo = subService.login(vo);
		
		
		SecurityUtil securityUtil = new SecurityUtil();
		String EncryptPw = securityUtil.encryptSHA256(vo.getPwKey());
		System.out.println("EncryptPw = " + EncryptPw +", vo.getPwKey() = " + vo.getPwKey());
		
		
		if(subVo == null) {
			return "sub/sub";
		}else {
			System.out.println("입력값 = " + vo.getPw() + ", 디비값 = " + subVo.getPw());
			if(EncryptPw.equals(subVo.getPw())) {
				return "main/main";
			}else {
				return "sub/sub";
			}
				
		}
	}
}
