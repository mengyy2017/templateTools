package com.templateTools.base.mapper;


import com.templateTools.base.entity.BaseEntity;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;


public interface CommonMapper<T extends BaseEntity> extends Mapper<T>, MySqlMapper<T>{
}
