package com.example.bcsd;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class HelloController {

    @ResponseBody
    @GetMapping("/introduce")
    public String introduce(@RequestParam(name = "name", required = false, defaultValue = "홍길동") String name) {
        return "안녕하세요 제 이름은 " + name + "입니다!";
    }
    @ResponseBody
    @GetMapping("/json")
    public Map<String, Object> json() {
        Map<String, Object> map = new HashMap<>();
        map.put("age", 26);
        map.put("name", "허준기");
        return map;
    }
}