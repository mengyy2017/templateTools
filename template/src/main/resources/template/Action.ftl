<#list columnList as columnEntity>
    label.opc.${camelTableName}.${columnEntity.camelColName} = ${columnEntity.camelColName?upper_case}
</#list>


------------------------------------------------------------------------


<#list columnList as columnEntity>
label.opc.${camelTableName}.${columnEntity.camelColName} = ${columnEntity.columnComment!}
</#list>


-------------------------------------------------------------------------------

---------------------------------------------------------------------------------


<#list columnList as columnEntity>
<div class="formInput">
    <label for="${columnEntity.camelColName}"><${"@"}spring.message code="label.opc.${camelTableName}.${columnEntity.camelColName}"/>：</label>
    <select class="form-control selects" name="${columnEntity.camelColName}" id="${columnEntity.camelColName}">
    <${"#"}if ${columnEntity.camelColName}RefList?exists  && (${columnEntity.camelColName}RefList?size > 0) >
    <option value="">请选择</option>
    <${"#"}list ${columnEntity.camelColName}RefList as itemInfo>
    <${"#"}if (itemInfo.isValid)! == "1" && (itemInfo.isShow)! == "1"><option value="${"$"}{itemInfo.id!}"
        <${"#"}if (q.${columnEntity.camelColName})! == itemInfo.id!>selected</${"#"}if>${"$"}>{itemInfo.name!}</option></${"#"}if>
    </${"#"}list>
    </${"#"}if>
    </select>
</div>

</#list>


---------------------------------------------------------------------------------


<#list columnList as columnEntity>

    <${"#"}if ${columnEntity.camelColName}RefList?exists  && (${columnEntity.camelColName}RefList?size > 0) >
        <${"#"}list ${columnEntity.camelColName}RefList as itemInfo>
            <${"#"}if (itemInfo.isValid)! == "1" && (itemInfo.isShow)! == "1" && (q.${columnEntity.camelColName})! == itemInfo.id!>
            ${"$"}{itemInfo.name!}</${"#"}if>
        </${"#"}list>
    </${"#"}if>

</#list>


---------------------------------------------------------------------------------

label.opc.${camelTableName}.pageTitle.approve = Approve ${camelTableName}
<#list columnList as columnEntity>
    <div class="formInput">
        <label for="${columnEntity.camelColName}"><${"@"}spring.message code="label.opc.${camelTableName}.${columnEntity.camelColName}"/>：</label>
        <input type="text" style="" id="${columnEntity.camelColName}" name="${columnEntity.camelColName}" value="${"$"}{q.${columnEntity.camelColName}!}" placeholder="">
    </div>

</#list>


---------------------------------------------------------------------------------


