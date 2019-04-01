package com.templateTools.controller;

import com.templateTools.base.controller.BaseController;
import com.templateTools.base.entity.Resp;
import com.templateTools.entity.MenuEntity;
import com.templateTools.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController extends BaseController {

    @Autowired
    MenuService menuService;

    @PostMapping("/getAllMenu")
    public Resp<List<MenuEntity>> getAllMenu(HttpServletRequest req) {
        try {
            return mkSuccResp(menuService.selectAll());
        } catch (Exception e) {
            mkFailResp(e.getMessage());
        }
        return respResult.get();
    }

    @PostMapping("/getMenuUniq")
    public Resp<MenuEntity> getMenuUniq(HttpServletRequest req, @RequestBody MenuEntity menuEntity) {
        try {
            return mkSuccResp(menuService.selectOne(menuEntity));
        } catch (Exception e) {
            mkFailResp(e.getMessage());
        }
        return respResult.get();
    }

    @PostMapping("/updateOrSave")
    public Resp<MenuEntity> updateOrSave(HttpServletRequest req, @RequestBody MenuEntity menuEntity) {
        try {
            return mkSuccResp(menuService.updateOrSaveSelective(menuEntity));
        } catch (Exception e) {
            mkFailResp(e.getMessage());
        }
        return respResult.get();
    }



}