package com.lzhphantom.userimpl.controller;

import com.lzhphantom.core.common.util.LzhphantomResult;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Tag(name = "测试模块")
public class TestController {
    @GetMapping("")
    public LzhphantomResult test(){
        return LzhphantomResult.ok("Hi lzhphantom","测试成功");
    }
}
