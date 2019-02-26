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
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Controller
@RequestMapping(value = "/database")
public class DatabaseController extends BaseController {

    @Autowired
    TableService tableService;

    @Autowired
    ColumnService columnService;

    @RequestMapping(value = "/setCreateInfo")
    @ResponseBody
    public List<TableEntity> setCreateInfo(@RequestBody CreateInfo createInfo, HttpServletRequest request){

        ThreadLocalUtil.getCreateInfoThreadLocal().set(createInfo);
        // ThreadLocalUtil.getRequestThreadLocal().set(request);
        Example example = new Example(TableEntity.class);
        example.createCriteria().andEqualTo("tableSchema", createInfo.getDatabaseSchema());

        List<TableEntity> tableEntityList = tableService.selectByExample(example);

        return tableEntityList;
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

        setVals(columnEntity, getVAndF("wxj", ColumnEntity::setTableSchema));

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
