package com.template.bussiness.controller;

import com.common.bussiness.controller.BaseController;
import com.common.pub.pubBo.Resp;
import com.template.bussiness.entity.model.CreateInfo;
import com.template.bussiness.service.TableService;
import com.template.bussiness.entity.ColumnEntity;
import com.template.bussiness.entity.TableEntity;
import com.template.bussiness.entity.model.TableColsInfo;
import com.template.pub.consts.Consts;
import com.template.bussiness.service.ColumnService;
import com.template.util.FreeMarkerUtil;
import com.template.util.HandelDataUtil;
import com.template.util.ThreadLocalUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
    public Resp<List<TableEntity>> setCreateInfo(@RequestBody CreateInfo createInfo, HttpServletResponse response){

        ThreadLocalUtil.getCreateInfoThreadLocal().set(createInfo);
        response.addCookie(new Cookie(Consts.AUTHTOKEN, createInfo.toString()));

        Example example = new Example(TableEntity.class);
        example.createCriteria().andEqualTo("tableSchema", createInfo.getTableSchema());

        try {
            return mkSuccResp(tableService.selectByExample(example));
        } catch (Exception e) {
            mkFailResp(e);
        }

        return respResult.get();
    }

    @RequestMapping(value = "/getAllTables")
    @ResponseBody
    public Resp<List<TableEntity>> getAllTables(){

        Example example = new Example(TableEntity.class);

        example.createCriteria().andEqualTo("tableSchema", "wxj");

        try {
            return mkSuccResp(tableService.selectByExample(example));
        } catch (Exception e) {
            mkFailResp(e);
        }

        return respResult.get();
    }

    @PostMapping(value = "/getAllColumns")
    @ResponseBody
    public Resp<List<ColumnEntity>> getTableColumn(@RequestBody ColumnEntity columnEntity) throws Exception {
        try{
            return mkSuccResp(columnService.select(columnEntity));
        } catch (Exception e) {
            mkFailResp(e);
        }
        return respResult.get();
    }

    @PostMapping(value = "/createCode")
    @ResponseBody
    public Resp createCode (@RequestBody List<TableColsInfo> tableColsInfoList){

        tableColsInfoList.stream().forEach(dbModel -> {

            if(dbModel.getColList().size() == 0){
                List<ColumnEntity> columnList = columnService.select(
                        newAndSet(ColumnEntity::new, getVAndF(dbModel.getTableName(), ColumnEntity::setTableName)
                                    , getVAndF(CreateInfo.creInfoFromToken().getTableSchema(), ColumnEntity::setTableSchema)));
                setVals(dbModel, getVAndF(columnList, TableColsInfo::setColList));
            }

            HandelDataUtil.convertData(dbModel);

            try {
                FreeMarkerUtil.outputFile(HandelDataUtil.convertData(dbModel));
            } catch (Exception e) {
                mkFailResp(e);
            }
        });
        return respResult.get();
    }

}
