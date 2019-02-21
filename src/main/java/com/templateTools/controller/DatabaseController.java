package com.templateTools.controller;

import com.templateTools.base.controller.BaseController;
import com.templateTools.entity.ColumnEntity;
import com.templateTools.entity.TableEntity;
import com.templateTools.entity.model.DatabaseModel;
import com.templateTools.service.ColumnService;
import com.templateTools.service.TableService;
import com.templateTools.utils.FreeMarkerUtil;
import com.templateTools.utils.HandelDataUtil;
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
import java.util.stream.Stream;

@Controller
@RequestMapping(value = "/database")
public class DatabaseController extends BaseController {

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

//        Example a = oneConstr(Example::new, TableEntity.class);
//        putsVals(a, Example.Criteria::,"tableSchema", TABLE_SCHEMA);

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

        databaseModelList.stream().forEach(dbModel -> {

            if(dbModel.getColList().size() == 0){
                List<ColumnEntity> columnList = columnService.select(newAndSet(ColumnEntity::new, getVAndF(dbModel.getTableName(), ColumnEntity::setTableName)));
                setVals(dbModel, getVAndF(columnList, DatabaseModel::setColList));
            }

            Map databaseMap = HandelDataUtil.convertData(dbModel);

            FreeMarkerUtil.outputFile(databaseMap);

        });
        return null;
    }


}
