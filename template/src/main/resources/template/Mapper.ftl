<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<!--   -->
<mapper namespace="com.yishi.core.opc.dao.I${upperCamelTableName}Mapper">
    <resultMap id="BaseResultMap" type="com.yishi.core.opc.entity.${upperCamelTableName}">
    <#list columnList as columnEntity>
        <#if columnEntity.columnKey! == "PRI">
        <#--javaType="${columnEntity.javaFiledType}" javaType可以不设置-->
        <result column="${columnEntity.columnName}" property="id" jdbcType="${columnEntity.dataType}" />	${"<!--"} ${columnEntity.columnComment!} ${"-->"}
        <#elseif columnEntity.columnKey! != "PRI">
        <result column="${columnEntity.columnName}" property="${columnEntity.camelColName}" jdbcType="${columnEntity.dataType}" />	${"<!--"} ${columnEntity.columnComment!} ${"-->"}
        </#if>
    </#list>
    </resultMap>

    <select id="find${upperCamelTableName}ById" parameterType="com.yishi.core.opc.entity.${upperCamelTableName}" resultMap="BaseResultMap">
        SELECT  *  
        FROM ${tableName}
        <#list columnList as columnEntity>
        <#if columnEntity.columnKey! == "PRI">
        WHERE ${columnEntity.columnName!}=${"#"}{id}
        </#if>
        </#list>
    </select>

    <select id="findAll${upperCamelTableName}" resultMap="BaseResultMap" parameterType="com.yishi.core.opc.entity.${upperCamelTableName}">
        SELECT ${camelTableName}.* 
        FROM ${tableName} ${camelTableName}
        WHERE 1=1
        <#list columnList as columnEntity>
        <#if columnEntity.columnKey! != "PRI">
            <if test="${columnEntity.camelColName} != null and  ${columnEntity.camelColName} != ''" >
                AND ${camelTableName}.${columnEntity.columnName} = ${"#"}{${columnEntity.camelColName}, jdbcType=${columnEntity.dataType}} 	${"<!--"} ${columnEntity.columnComment!} ${"-->"}
            </if>
        </#if>
        </#list>
    </select>

    <insert id="add${upperCamelTableName}" parameterType="com.yishi.core.opc.entity.${upperCamelTableName}">
         INSERT INTO  ${tableName}
         <trim prefix="(" suffix=")" suffixOverrides=",">
             <#list columnList as columnEntity>
             <#if columnEntity.columnKey! == "PRI">
                 ${columnEntity.columnName!},
             <#elseif columnEntity.columnKey! != "PRI">
                 <if test="${columnEntity.camelColName} != null and  ${columnEntity.camelColName} != ''" >
                     ${columnEntity.columnName},
                 </if>
             </#if>
             </#list>
         </trim>
         <trim prefix="VALUES (" suffix=")" suffixOverrides=",">
             <#list columnList as columnEntity>
             <#if columnEntity.columnKey! == "PRI">
                 ${"#"}{id},
             <#elseif columnEntity.columnKey! != "PRI">
                 <if test="${columnEntity.camelColName} != null and  ${columnEntity.camelColName} != ''" >
                     ${"#"}{${columnEntity.camelColName}},
                 </if>
             </#if>
             </#list>
         </trim>
    </insert>

    <update id="update${upperCamelTableName}" parameterType="com.yishi.core.opc.entity.${upperCamelTableName}">
        UPDATE ${tableName}
        <trim prefix="set" suffixOverrides=",">

            <#list columnList as columnEntity>
                <#if columnEntity.columnKey! != "PRI">
                <choose>
                    <when test="${columnEntity.camelColName} != null and ${columnEntity.camelColName} != ''">
                        ${columnEntity.columnName} = ${"#"}{${columnEntity.camelColName}},
                    </when>
                    <otherwise>
                        ${columnEntity.columnName} = null,
                    </otherwise>
                </choose>
                </#if>
            </#list>
        </trim>
        <#list columnList as columnEntity>
            <#if columnEntity.columnKey! == "PRI">
                WHERE ${columnEntity.columnName!}=${"#"}{id}
            </#if>
        </#list>
    </update>

    <delete id="delete${upperCamelTableName}" parameterType="com.yishi.core.opc.entity.${upperCamelTableName}">
        DELETE 
        FROM ${tableName}
        <#list columnList as columnEntity>
            <#if columnEntity.columnKey! == "PRI">
                WHERE ${columnEntity.columnName!}=${"#"}{id}
            </#if>
        </#list>
    </delete>

</mapper>
