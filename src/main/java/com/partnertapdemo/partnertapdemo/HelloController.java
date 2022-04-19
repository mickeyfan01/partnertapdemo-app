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
		return "Hey Partners, you can do some shopping here and also win prices 3";
	}
<<<<<<< HEAD
	@RequestMapping("/shopping2")
	public String shopping2() {
		return "Hey Partners, shopping 2";
=======
	@RequestMapping("/demo1")
	public String demo1() {
		return "Welcome to demo1";
>>>>>>> 43245c0e9fc3c1a75b1df3114e39f3724f751362
	}
}