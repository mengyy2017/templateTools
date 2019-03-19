<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >

<!-- 
         符号转义说明
    &lt;          < 
    &gt;          >  
    &lt;&gt;     <>
    &amp;        & 
    &apos;       '
    &quot;       "
  <![CDATA[ 这里写你的SQL或者符号 ]]> 
 -->
${"<!--"} ${author!}		${date!} ${"-->"}
${"<!--"} ${tableRemark!} ${"-->"}
<mapper namespace="${namespace}Mapper" >
  	<!-- 添加,插入记录   -->
	<insert id="insert" parameterType="${entityDotAllPath}" >
	    insert into ${tableName} (<include refid="${namespace}FieldMapper.insertColumns"/>) values(<include refid="${namespace}FieldMapper.insertParams"/>)
	</insert>
	
	<!-- 删除,主键删除   -->
	<delete id="deleteByPrimaryKey" parameterType="${entityDotAllPath}">
	    delete from ${tableName} where 1=1 <include refid="${namespace}FieldMapper.noPrefixKey"/>
	</delete>
	
	<!-- 删除,实体删除   -->
	<delete id="deleteByEntity" parameterType="${entityDotAllPath}">
	    delete from ${tableName} where 1=1 <include refid="${namespace}FieldMapper.deleteAll"/>
	</delete>
	
	<!-- 修改,主键更新  -->
	<update id="updateByPrimaryKey" parameterType="${entityDotAllPath}" >
		update ${tableName}
			<set><include refid="${namespace}FieldMapper.updateParams"/></set>
			where 1=1 <include refid="${namespace}FieldMapper.noPrefixKey"/>
	</update>
	
	<!-- 查询,实体查询   -->
	<select id="selectByEntry" resultMap="${namespace}FieldMapper.columnResultMap" parameterType="${entityDotAllPath}">
		select <include refid="${namespace}FieldMapper.tableColumns"/> 
		from ${tableName} ${tableName} 
		where 1=1 and ${tableName}.isdel=0 <include refid="${namespace}FieldMapper.andAll"/>
	</select>
	
	<!-- 查询,ID查询   -->
	<select id="selectById" resultMap="${namespace}FieldMapper.columnResultMap" parameterType="java.lang.String">
		select <include refid="${namespace}FieldMapper.tableColumns"/> 
		from ${tableName} ${tableName} 
		where 1=1 and ${tableName}.isdel=0 <include refid="${namespace}FieldMapper.primaryKey"/>
	</select>
	
	<!-- 查询,Map参数查询 -->
	<select id="selectByMap" resultMap="${namespace}FieldMapper.columnResultMap" parameterType="com.tw.base.dto.ParamDTO">
		select <include refid="${namespace}FieldMapper.tableColumns"/>
		from ${tableName} ${tableName} 
		where 1=1 and ${tableName}.isdel=0 <include refid="${namespace}FieldMapper.andAll"/>
	</select>
	
	<!-- 分页查询,ParamDTO参数查询 -->
	<select id="queryPage" resultMap="${namespace}FieldMapper.columnResultMap" parameterType="com.tw.base.dto.ParamDTO">
		select <include refid="${namespace}FieldMapper.tableColumns"/>
		$from$ ${tableName} ${tableName} 
		where 1=1 and ${tableName}.isdel=0 <include refid="${namespace}FieldMapper.andLike"/>
	</select>
  
  	<!-- 批量插入 mysql -->
  	<insert id="insertBatch_mysql" parameterType="java.util.List">
  		insert into ${tableName} (<include refid="${namespace}FieldMapper.insertBatchColumns"/>) 
  		values
  		<foreach collection="list" item="item" index="index" separator=",">
  			(<include refid="${namespace}FieldMapper.insertBatchParams"/>)
  		</foreach>
  	</insert>
  	<!-- 批量插入 oracle -->
  	<insert id="insertBatch_oracle" parameterType="java.util.List" useGeneratedKeys="false">
  		insert all
  		<foreach collection="list" item="item" index="index">
  		into ${tableName} (<include refid="${namespace}FieldMapper.insertBatchColumns"/>) 
  		values 
  			(<include refid="${namespace}FieldMapper.insertBatchParams"/>)
  		</foreach>
  		select 1 from dual
  	</insert>
  	<!-- 批量删除 -->
  	<delete id="deleteBatch" parameterType="java.util.List">
  		delete from ${tableName} where
  		<#list columnList as columnEntity>
		<#if columnEntity.columnKey! == "PRI">
		${columnEntity.columnName} in 	${"<!--"} ${columnEntity.columnComment!} ${"-->"}
	    </#if>
		</#list>
  		<foreach collection="list" item="item" index="index" open="(" separator="," close=")">
  			${"#"}{item}
  		</foreach>
  	</delete>
  	
  	<!-- 逻辑删除  -->
	<update id="logicDelete" parameterType="${entityDotAllPath}" >
		update ${tableName}
			set isdelete = 1 	<!-- 逻辑删除 -->
		where 1=1 <include refid="${namespace}FieldMapper.noPrefixKey"/>
	</update>
	
  	<!-- 批量逻辑删除 -->
  	<update id="logicDeleteBatch" parameterType="java.util.List" >
		update ${tableName}
			set isdelete = 1 	<!-- 逻辑删除 -->
		where 
			<#list columnList as columnEntity>
			<#if columnEntity.columnKey! == "PRI">
			${columnEntity.columnName} in 	${"<!--"} ${columnEntity.columnComment!} ${"-->"}
		    </#if>
			</#list>
	  		<foreach collection="list" item="item" index="index" open="(" separator="," close=")">
	  			${"#"}{item}
	  		</foreach>
	</update>
</mapper>