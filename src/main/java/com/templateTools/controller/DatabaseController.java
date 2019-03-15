package com.templateTools.controller;

import com.templateTools.base.controller.BaseController;
import com.templateTools.entity.ColumnEntity;
import com.templateTools.entity.UserEntity;
import com.templateTools.entity.model.CreateInfo;
import com.templateTools.entity.TableEntity;
import com.templateTools.entity.model.TableColsInfo;
import com.templateTools.service.ColumnService;
import com.templateTools.service.TableService;
import com.templateTools.utils.HandelDataUtil;
import com.templateTools.utils.ThreadLocalUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
@RequestMapping(value = "/database")
public class DatabaseController extends BaseController {

    @Autowired
    TableService tableService;

    @Autowired
    ColumnService columnService;

    @ApiOperation(value = "初始化连接信息并查询所有表", notes = "根据信息初始化连接并查询连接内的所有表")
    @PostMapping(value = "/setCreateInfo")
    @ResponseBody
    public List<TableEntity> setCreateInfo(@RequestBody CreateInfo createInfo, HttpServletResponse response){

        ThreadLocalUtil.getCreateInfoThreadLocal().set(createInfo);
        response.addCookie(new Cookie("authToken", createInfo.toString()));

        Example example = new Example(TableEntity.class);
        example.createCriteria().andEqualTo("tableSchema", createInfo.getDatabaseSchema());

        List<TableEntity> list = tableService.selectByExample(example);

        return list;
    }

    @GetMapping(value = "/getAllTables")
    @ResponseBody
    public List<TableEntity> getAllTables(){

        Example example = new Example(TableEntity.class);

        example.createCriteria().andEqualTo("tableSchema", "wxj");

        return tableService.selectByExample(example);
    }

    @PostMapping(value = "/getAllColumns")
    @ResponseBody
    public List<ColumnEntity> getTableColumn(ColumnEntity columnEntity) throws Exception {
        try{
            return columnService.select(columnEntity);
        } catch (Exception e) {
            // throw new Exception("连接超时");
        }
        return null;
    }

    @PostMapping(value = "/createCode")
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
