package com.article.recommend.controller;

import com.article.recommend.dbsourcemanager.PropertiesModel;
import com.article.recommend.entity.QuartzInfo;
import com.article.recommend.service.quartzservice.QuartzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class InitController {
    @Autowired
    private PropertiesModel propertiesModel;
    @Autowired
    private QuartzService quartzService;
    @RequestMapping("/start")
    public String  start(Model model){
        System.out.println("项目启动方法入口");
        model.addAttribute("name",propertiesModel.getDriverClassName());
        return "index";
    }

    /**
     * 用户session验证 暂时没有
     */
    public void validateUser(){

    }
    @RequestMapping("/login")
    public String  login(Model model,HttpServletResponse response,@RequestParam String userName,@RequestParam String password){

            if(userName.equals("admin") && userName.equals(password)){
                model.addAttribute("userName",userName);
                List<QuartzInfo> quartzInfos= quartzService.getQuartzInfos();
                model.addAttribute("quartzInfos",quartzInfos);
                return"main";
            }else{
                return "index";
            }
    }

}
