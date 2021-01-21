package com.javaex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javaex.dao.GuestbookDao;
import com.javaex.vo.GuestVo;

@Controller
@RequestMapping(value = "/guest")
public class GuestController {
	
	//오토와이어드 필드
	@Autowired
	private GuestbookDao guestbookDao;
	// 리스트
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(Model model) {
		System.out.println("list");

		List<GuestVo> guestList = guestbookDao.getList();

		// 테스트
		System.out.println(guestList);

		model.addAttribute("guestList", guestList);

		return "list";

	}

	// 생성
	@RequestMapping(value = "/insert", method = { RequestMethod.GET, RequestMethod.POST })
	public String insert(@ModelAttribute GuestVo guestVo) {
		System.out.println("insert");

	
		System.out.println(guestVo);
		guestbookDao.Insert(guestVo);

		return "redirect:/guest/list";
	}

	@RequestMapping(value = "/deleteForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String deleteForm() {
		System.out.println("deleteForm");
		//@PathVariable("no") int no 받는경우 포워드로 값이 넘어가질 않는다
		// 어튜리뷰트로 할경우 가능할 것같다 -->2delteForm2에서 실험
		// 받는 파라미터 no의 값이 포워드로 넘어가야함
		
		return "deleteForm";
	}

	@RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.POST })
	public String delete(@ModelAttribute GuestVo guestVo, Model model) {
		System.out.println("delete");
		// ---***디스패처에서는 디폴트생성자로 Vo를 불러 setter로 값을 넣기 떄문에
		// ---***실험을 위해 기존의 no와 password가 들어있는 생성자를 지워봄
		System.out.println(guestVo);
		// ---***잘들어간다

		// 삭제
		int count = guestbookDao.delete(guestVo);
	
		if (count == 0) {
			// 삭제 실패일경우
			String result = "fail";
			model.addAttribute("result", result);
			// 파라미터 no의 값도 같이 포워드해야하는데
			// 저절로 들어가니 따로 설정할 필요가없다
			return "deleteForm";
		} else {
			// 삭제 성공일 경우
			return "redirect:/guest/list";
		}
	

	}
	@RequestMapping(value = "/list2", method = { RequestMethod.GET, RequestMethod.POST })
	public String list2(Model model) {
		System.out.println("list2");
		
		List<GuestVo> guestList = guestbookDao.getList();
		
		// 테스트
		System.out.println(guestList);
		
		model.addAttribute("guestList", guestList);
		
		return "list2";
		
	}
	@RequestMapping(value = "/deleteForm2/{no}", method = { RequestMethod.GET, RequestMethod.POST })
	public String deleteForm2(@PathVariable("no") int no,Model model) {
		System.out.println("deleteForm2");
		//@PathVariable("no") int no 받는경우 포워드로 값이 넘어가질 않는다
		// 어튜리뷰트로 할경우 가능할 것같다
		// 받는 파라미터 no의 값이 포워드로 넘어가야함
		model.addAttribute("no",no);
		return "deleteForm2";
	}
	
	@RequestMapping(value = "/delete2", method = { RequestMethod.GET, RequestMethod.POST })
	public String delete2(@ModelAttribute GuestVo guestVo, Model model) {
		System.out.println("delete2");
		// ---***디스패처에서는 디폴트생성자로 Vo를 불러 setter로 값을 넣기 떄문에
		// ---***실험을 위해 기존의 no와 password가 들어있는 생성자를 지워봄
		System.out.println(guestVo);
		// ---***잘들어간다

		// 삭제
		int count = guestbookDao.delete(guestVo);
	
		if (count == 0) {
			// 삭제 실패일경우
			String result = "fail";
			model.addAttribute("result", result);
			model.addAttribute("no",guestVo.getNo());
			// 파라미터 no의 값도 같이 포워드해야하는데
			// 저절로 들어가니 따로 설정할 필요가없다
			return "deleteForm2";
		} else {
			// 삭제 성공일 경우
			return "redirect:/guest/list2";
		}
	
	}
}
