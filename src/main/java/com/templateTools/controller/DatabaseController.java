package com.templateTools.controller;

import com.templateTools.entity.ColumnEntity;
import com.templateTools.entity.TableEntity;
import com.templateTools.entity.model.DatabaseModel;
import com.templateTools.service.ColumnService;
import com.templateTools.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/database")
public class DatabaseController {

    @Value("${table.schema}")
    private String TABLE_SCHEMA;

    @Autowired
    TableService tableService;

    @Autowired
    ColumnService columnService;

    @RequestMapping(value = "/getAllTables")
    @ResponseBody
    public List<TableEntity> getAllTables(){

        Example example = new Example(TableEntity.class);
        example.createCriteria().andEqualTo("tableSchema", TABLE_SCHEMA);

        List<TableEntity> list = tableService.selectByExample(example);
        return list;
    }

    @RequestMapping(value = "/getAllColumns")
    @ResponseBody
    public List<ColumnEntity> getTableColumn(ColumnEntity columnEntity){

        columnEntity.setTableSchema(TABLE_SCHEMA);

        List<ColumnEntity> list = columnService.select(columnEntity);
        return list;
    }

    @RequestMapping(value = "/createCode")
    @ResponseBody
    public Map createCode (@RequestBody List<DatabaseModel> databaseModelList){
        Map map = new HashMap();
        map.put("111", "222");
        map.put("code", 200);
        return map;
    }


}
