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
<mapper namespace="${namespace}${camelTableName}Mapper" >
  	<!-- 添加,插入记录   -->
	<insert id="insert${upperCamelTableName}" parameterType="${entityDotAllPath}${upperCamelTableName}Entity" >
	    insert into ${tableName} (<include refid="${namespace}field.${camelTableName}FieldMapper.insert${upperCamelTableName}Columns"/>) values(<include refid="${namespace}field.${camelTableName}FieldMapper.insert${upperCamelTableName}Params"/>)
	</insert>
	
	<!-- 删除,主键删除   -->
	<delete id="delete${upperCamelTableName}ByPrimaryKey" parameterType="${entityDotAllPath}${upperCamelTableName}Entity">
	    delete from ${tableName} where 1=1 <include refid="${namespace}field.${camelTableName}FieldMapper.noPrefixKey${upperCamelTableName}"/>
	</delete>
	
	<!-- 删除,实体删除   -->
	<delete id="delete${upperCamelTableName}ByEntity" parameterType="${entityDotAllPath}${upperCamelTableName}Entity">
	    delete from ${tableName} where 1=1 <include refid="${namespace}field.${camelTableName}FieldMapper.deleteAll${upperCamelTableName}"/>
	</delete>
	
	<!-- 修改,主键更新  -->
	<update id="update${upperCamelTableName}ByPrimaryKey" parameterType="${entityDotAllPath}${upperCamelTableName}Entity" >
		update ${tableName}
			<set><include refid="${namespace}field.${camelTableName}FieldMapper.update${upperCamelTableName}Params"/></set>
			where 1=1 <include refid="${namespace}field.${camelTableName}FieldMapper.noPrefixKey${upperCamelTableName}"/>
	</update>
	
	<!-- 查询,实体查询   -->
	<select id="select${upperCamelTableName}ByEntry" resultMap="${namespace}field.${camelTableName}FieldMapper.${camelTableName}ResultMap" parameterType="${entityDotAllPath}${upperCamelTableName}Entity">
		select <include refid="${namespace}field.${camelTableName}FieldMapper.${camelTableName}Columns"/>
		from ${tableName} ${tableName} 
		where 1=1 and ${tableName}.isdel=0 <include refid="${namespace}field.${camelTableName}FieldMapper.andAll${upperCamelTableName}"/>
	</select>
	
	<!-- 查询,ID查询   -->
	<select id="select${upperCamelTableName}ById" resultMap="${namespace}field.${camelTableName}FieldMapper.${camelTableName}ResultMap" parameterType="java.lang.String">
		select <include refid="${namespace}field.${camelTableName}FieldMapper.${camelTableName}Columns"/>
		from ${tableName} ${tableName} 
		where 1=1 and ${tableName}.isdel=0 <include refid="${namespace}field.${camelTableName}FieldMapper.primaryKey${upperCamelTableName}"/>
	</select>
	
  	<!-- 批量插入 mysql -->
  	<insert id="insert${upperCamelTableName}Batch_mysql" parameterType="java.util.List">
  		insert into ${tableName} (<include refid="${namespace}field.${camelTableName}FieldMapper.insert${upperCamelTableName}BatchColumns"/>)
  		values
  		<foreach collection="list" item="item" index="index" separator=",">
  			(<include refid="${namespace}field.${camelTableName}FieldMapper.insert${upperCamelTableName}BatchParams"/>)
  		</foreach>
  	</insert>
  	<!-- 批量插入 oracle -->
  	<insert id="insert${upperCamelTableName}Batch_oracle" parameterType="java.util.List" useGeneratedKeys="false">
  		insert all
  		<foreach collection="list" item="item" index="index">
  		into ${tableName} (<include refid="${namespace}field.${camelTableName}FieldMapper.insert${upperCamelTableName}BatchColumns"/>)
  		values 
  			(<include refid="${namespace}field.${camelTableName}FieldMapper.insert${upperCamelTableName}BatchParams"/>)
  		</foreach>
  		select 1 from dual
  	</insert>
  	<!-- 批量删除 -->
  	<delete id="delete${upperCamelTableName}Batch" parameterType="java.util.List">
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
	<update id="logic${upperCamelTableName}Delete" parameterType="${entityDotAllPath}${upperCamelTableName}Entity" >
		update ${tableName}
			set isdelete = 1 	<!-- 逻辑删除 -->
		where 1=1 <include refid="${namespace}field.${camelTableName}FieldMapper.noPrefixKey${upperCamelTableName}"/>
	</update>
	
  	<!-- 批量逻辑删除 -->
  	<update id="logic${upperCamelTableName}DeleteBatch" parameterType="java.util.List" >
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