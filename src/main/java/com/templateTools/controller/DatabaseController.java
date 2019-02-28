package com.templateTools.controller;

import com.templateTools.base.controller.BaseController;
import com.templateTools.entity.ColumnEntity;
import com.templateTools.entity.CreateInfo;
import com.templateTools.entity.TableEntity;
import com.templateTools.entity.TableColsInfo;
import com.templateTools.service.ColumnService;
import com.templateTools.service.TableService;
import com.templateTools.utils.HandelDataUtil;
import com.templateTools.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping(value = "/database")
public class DatabaseController extends BaseController {

    @Autowired
    TableService tableService;

    @Autowired
    ColumnService columnService;

    @RequestMapping(value = "/setCreateInfo")
    @ResponseBody
    public List<TableEntity> setCreateInfo(@RequestBody CreateInfo createInfo, HttpServletResponse response){

        ThreadLocalUtil.getCreateInfoThreadLocal().set(createInfo);
        response.addCookie(new Cookie("authToken", createInfo.toString()));

        Example example = new Example(TableEntity.class);
        example.createCriteria().andEqualTo("tableSchema", createInfo.getDatabaseSchema());

        List<TableEntity> list = tableService.selectByExample(example);

        return list;
    }

    @RequestMapping(value = "/getAllTables")
    @ResponseBody
    public List<TableEntity> getAllTables(){

        Example example = new Example(TableEntity.class);

        example.createCriteria().andEqualTo("tableSchema", "wxj");

        return tableService.selectByExample(example);
    }

    @RequestMapping(value = "/getAllColumns")
    @ResponseBody
    public List<ColumnEntity> getTableColumn(ColumnEntity columnEntity){
        return columnService.select(columnEntity);
    }

    @RequestMapping(value = "/createCode")
    @ResponseBody
    public Map createCode (@RequestBody List<TableColsInfo> tableColsInfoList){

        tableColsInfoList.stream().forEach(dbModel -> {

            if(dbModel.getColList().size() == 0){
                List<ColumnEntity> columnList = columnService.select(
                        newAndSet(ColumnEntity::new, getVAndF(dbModel.getTableName(), ColumnEntity::setTableName)));
                setVals(dbModel, getVAndF(columnList, TableColsInfo::setColList));
            }

            HandelDataUtil.convertData(dbModel);

//            FreeMarkerUtil.outputFile(HandelDataUtil.convertData(dbModel));

        });
        return null;
    }


}
