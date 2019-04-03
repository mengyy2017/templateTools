package com.template.service;

import com.template.base.service.CommonService;
import com.template.entity.TableEntity;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class TableService extends CommonService<TableEntity> {
    @Override
    public List<TableEntity> selectByExample(Example example) {
        return super.selectByExample(example);
    }
}
