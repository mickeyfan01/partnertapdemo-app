package com.partnertapdemo.partnertapdemo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@RequestMapping("/")
	public String index() {
		return "Welcome to the TAP Demo by Tanzu Partner SE Team";
	}
	@RequestMapping("/tap")
	public String tap() {
		return "Great to be here huh , go tanzu";
	}
	@RequestMapping("/shopping")
	public String shopping() {
		return "Hey Partners, you can do some shopping here and also win prices";
	}
	@RequestMapping("/shopping1")
	public String shopping1() {
		return "Hey Partners shopping1, you can do some shopping here and also win prices";
	}
	@RequestMapping("/shopping2")
	public String shopping2() {
		return "Hey Partners shopping2, you can do some shopping here and also win prices";
	}
	@RequestMapping("/shopping3")
	public String shopping3() {
		return "Hey Partners shopping3, you can do some shopping here and also win prices";
	}
	@RequestMapping("/shopping5")
	public String shopping5() {
		return "Hey Partners shopping5, you can do some shopping here and also win prices and many more";
	}
	@RequestMapping("/shopping4")
	public String shopping4() {
		return "Hey Partners shopping4, ome";
	}
}