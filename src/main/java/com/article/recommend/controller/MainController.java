package com.article.recommend.controller;

import com.alibaba.fastjson.JSON;
import com.article.recommend.entity.QuartzInfo;
import com.article.recommend.service.quartzservice.QuartzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    private QuartzService quartzService;
    @RequestMapping("getQuartzInfo")
    public String getQuartzInfo(Model model, Long id){
        QuartzInfo quartzInfo=quartzService.getQuartzInfoById(id);
        System.out.println(JSON.toJSONString(quartzInfo));
        model.addAttribute("quartzInfo",quartzInfo);
        model.addAttribute("userName","admin");
        return "detail";
    }
    @RequestMapping("updateQuartz")
    public String updateQuartz(Model model,QuartzInfo quartzInfo){
        System.out.println(JSON.toJSONString(quartzInfo));
        quartzService.updateQuartzInfo(quartzInfo);
        List<QuartzInfo> quartzInfos= quartzService.getQuartzInfos();
        model.addAttribute("quartzInfos",quartzInfos);
        model.addAttribute("userName","admin");

        return  "main";
    }
}
