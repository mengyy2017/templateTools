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
<mapper namespace="${namespace}field.${camelTableName}FieldMapper" >
	<!-- Result Map 数据库映射到实体类  -->
	<resultMap id="${camelTableName}ResultMap" type="${entityDotAllPath}${upperCamelTableName}Entity" >
	<#list columnList as columnEntity>
		<result column="${columnEntity.columnName}" property="${columnEntity.camelColName}" jdbcType="${columnEntity.dataType?upper_case?upper_case}" javaType="${columnEntity.javaFiledType}"/>	${"<!--"} ${columnEntity.columnComment!} ${"-->"}
	</#list>
	</resultMap>
  	
  	<!-- tableColumns  所有列 -->
	<sql id="${camelTableName}Columns" >
		<trim suffix="" suffixOverrides=",">
		<#list columnList as columnEntity>
			${tableName}.${columnEntity.columnName}	<#--as	${columnEntity.camelColName}, as后面应该是column的名字 而不是property的名字 -->	${"<!--"} ${columnEntity.columnComment!} ${"-->"}
		</#list>
		</trim>
	</sql>
	
	<!-- insertColumns 入库列 -->
	<sql id="insert${upperCamelTableName}Columns">
		<trim suffix="" suffixOverrides=",">
		<#list columnList as columnEntity>
			<if test="${columnEntity.camelColName} != null " >
				${columnEntity.columnName},	${"<!--"} ${columnEntity.columnComment!} ${"-->"}
			</if>
		</#list>
	    </trim>
	</sql>
	
	<!-- insertParams  入库值 -->
	<sql id="insert${upperCamelTableName}Params">
		<trim suffix="" suffixOverrides=",">
		<#list columnList as columnEntity>
			<if test="${columnEntity.camelColName} != null " >
				${"#"}{${columnEntity.camelColName}, jdbcType=${columnEntity.dataType?upper_case}},	${"<!--"} ${columnEntity.columnComment!} ${"-->"}
			</if>
		</#list>
	    </trim>
	</sql>
	
	<!-- insertBatchParams  批量入库列 -->
	<sql id="insert${upperCamelTableName}BatchColumns">
		<trim suffix="" suffixOverrides=",">
		<#list columnList as columnEntity>
			${columnEntity.columnName},	${"<!--"} ${columnEntity.columnComment!} ${"-->"}
		</#list>
	    </trim>
	</sql>
	
	<!-- insertBatchParams  批量入库值 -->
	<sql id="insert${upperCamelTableName}BatchParams">
		<trim suffix="" suffixOverrides=",">
		<#list columnList as columnEntity>
			${"#"}{item.${columnEntity.camelColName}, jdbcType=${columnEntity.dataType?upper_case}},	${"<!--"} ${columnEntity.columnComment!} ${"-->"}
		</#list>
	    </trim>
	</sql>
	
	<!-- updateParams  更新列 -->
	<sql id="update${upperCamelTableName}Params">
		<trim suffix="" suffixOverrides=",">
		<#list columnList as columnEntity>
			<#if columnEntity.columnKey! != "PRI">
			<if test="${columnEntity.camelColName} != null " >
				${columnEntity.columnName} = ${"#"}{${columnEntity.camelColName}, jdbcType=${columnEntity.dataType?upper_case}},	${"<!--"} ${columnEntity.columnComment!} ${"-->"}
			</if>
			</#if>
		</#list>
	    </trim>
	</sql>
	
  	<!-- 查询条件  包含所有 -->
	<sql id="andAll${upperCamelTableName}">
		<trim  suffixOverrides="," >
		<#list columnList as columnEntity>
			<if test="${columnEntity.camelColName} != null " >
				and ${tableName}.${columnEntity.columnName} = ${"#"}{${columnEntity.camelColName}, jdbcType=${columnEntity.dataType?upper_case}}	${"<!--"} ${columnEntity.columnComment!} ${"-->"}
			</if>
		</#list>
		</trim>
	</sql>
	
	<!-- 模糊查询判断 -->
	<sql id="andLike${upperCamelTableName}">
		<trim  suffixOverrides="," >
		<#list columnList as columnEntity>
			<if test="${columnEntity.camelColName} != null" >
				<#if columnEntity.javaFiledType=="Integer" || columnEntity.javaFiledType=="short" || columnEntity.javaFiledType=="Long" || columnEntity.javaFiledType=="float" || columnEntity.javaFiledType=="Double" || columnEntity.javaFiledType=="Date" || columnEntity.javaFiledType=="java.math.BigDecimal">
				and ${tableName}.${columnEntity.columnName} = ${"#"}{${columnEntity.camelColName}, jdbcType=${columnEntity.dataType?upper_case}}	${"<!--"} ${columnEntity.columnComment!} ${"-->"}
				<#else>
				and ${tableName}.${columnEntity.columnName} like CONCAT(CONCAT('%',${"#"}{${columnEntity.camelColName}, jdbcType=${columnEntity.dataType?upper_case}}),'%' )	${"<!--"} ${columnEntity.columnComment!} ${"-->"}
				</#if>
		    </if>
		</#list>
		</trim>
	</sql>
	
	<!-- 删除条件  包含所有 -->
	<sql id="deleteAll${upperCamelTableName}">
		<trim  suffixOverrides="," >
		<#list columnList as columnEntity>
			<if test="${columnEntity.camelColName} != null " >
				and ${columnEntity.columnName} = ${"#"}{${columnEntity.camelColName}, jdbcType=${columnEntity.dataType?upper_case}}	${"<!--"} ${columnEntity.columnComment!} ${"-->"}
			</if>
		</#list>
		</trim>
	</sql>
	
	<!-- primary key  没有别名的主键 列名称 ,视图获取不到主键 需要设置（用户删除、更新） -->
	<sql id="noPrefixKey${upperCamelTableName}">
		<#list columnList as columnEntity>
		<#if columnEntity.columnKey! == "PRI">
		and ${columnEntity.columnName} = ${"#"}{${columnEntity.camelColName}, jdbcType=${columnEntity.dataType?upper_case}}	${"<!--"} ${columnEntity.columnComment!} ${"-->"}
	    </#if>
		</#list>
	</sql>
	
	<!-- primaryKey  主键 列名称 ,视图获取不到主键 需要设置 -->
	<sql id="primaryKey${upperCamelTableName}">
		<#list columnList as columnEntity>
		<#if columnEntity.columnKey! == "PRI">
		and ${tableName}.${columnEntity.columnName} = ${"#"}{${columnEntity.camelColName}, jdbcType=${columnEntity.dataType?upper_case}}	${"<!--"} ${columnEntity.columnComment!} ${"-->"}
	    </#if>
		</#list>
	</sql>
</mapper>