package com.article.recommend.controller;

import com.alibaba.fastjson.JSON;
import com.article.recommend.entity.QuartzInfo;
import com.article.recommend.service.quartzservice.QuartzService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class QuartzController {
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
        QuartzInfo dbQuartzInfo=quartzService.getQuartzInfoById(quartzInfo.getId());
        if(!quartzInfo.getCron().equals(dbQuartzInfo.getCron())){//相等
            try {
                quartzService.updateQuartzInfo(quartzInfo);
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }
        List<QuartzInfo> quartzInfos= quartzService.getQuartzInfos();
        model.addAttribute("quartzInfos",quartzInfos);
        model.addAttribute("userName","admin");
        return  "main";
    }

    /**
     * 暂停
     *
     */
    @ResponseBody
    @RequestMapping("paused")
    public String paused( QuartzInfo quartzInfo){
        quartzInfo=quartzService.getQuartzInfoById(quartzInfo.getId());
        try {
            quartzService.paused(quartzInfo);
            return JSON.toJSONString("操作成功");
        } catch (SchedulerException e) {

            e.printStackTrace();
            return JSON.toJSONString("操作失败");

        }
    }
    @ResponseBody
    @RequestMapping("resume")
    public String resume( QuartzInfo quartzInfo){
        quartzInfo=quartzService.getQuartzInfoById(quartzInfo.getId());
        try {
            quartzService.resume(quartzInfo);
            return JSON.toJSONString("操作成功");
        } catch (SchedulerException e) {

            e.printStackTrace();
            return JSON.toJSONString("操作失败");

        }
    }
    @RequestMapping("addQuartz")
   public String addQuartz(Model model,QuartzInfo quartzInfo){
        model.addAttribute("userName","admin");
        return  "add";
   }

    @RequestMapping("saveQuartz")
    public String saveQuartz(Model model,QuartzInfo quartzInfo,RedirectAttributes redirectAttributes){
        quartzService.addQuartzInfo(quartzInfo);
        redirectAttributes.addAttribute("password","admin");
        redirectAttributes.addAttribute("userName","admin");
        return "redirect:/login";
    }
    @RequestMapping("deleteQuartz")
    public String deleteQuartz(Model model,Long id,RedirectAttributes redirectAttributes){
        try {
            quartzService.deleteQuartzInfo(id);
            List<QuartzInfo> quartzInfos= quartzService.getQuartzInfos();
            model.addAttribute("quartzInfos",quartzInfos);
            model.addAttribute("userName","admin");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        redirectAttributes.addAttribute("userName","admin");
        redirectAttributes.addAttribute("password","admin");
        return "redirect:/login";
    }

}
