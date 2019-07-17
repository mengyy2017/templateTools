<${"#"}import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html lang="en">

<head>
    <title><${"@"}spring.message code="label.opc.${camelTableName}.pageTitle.view"/></title>

    <${"#"}include "/center/head.html" />

</head>

<body>
<div class="sthHtml">
    <form class="form-horizontal">

        <input type="hidden" id="id" name="id" value="${"$"}{cur${upperCamelTableName}.id}">

        <#list columnList as columnEntity>

            <#if (columnEntity.columnName)?contains("_id") >
                <div class="form-group col-lg-10 col-sm-12 col-xs-12">
                    <label for="${columnEntity.camelColName}" class="col-lg-4 col-sm-4 col-xs-4 control-label"><${"@"}spring.message code="label.opc.${camelTableName}.${columnEntity.camelColName}"/>：</label>
                    <div class="col-lg-7 col-sm-7 col-xs-7">
                        <p>
                            <${"#"}if ${columnEntity.camelColName}RefList?exists  && (${columnEntity.camelColName}RefList?size > 0) >
                                <${"#"}list ${columnEntity.camelColName}RefList as itemInfo>
                                    <${"#"}if (itemInfo.isValid)! == "1" && (itemInfo.isShow)! == "1" && (cur${upperCamelTableName}.${columnEntity.camelColName})! == itemInfo.id!>
                                    ${"$"}{itemInfo.name!}</${"#"}if>
                            </${"#"}list>
                            </${"#"}if>
                        </p>
                    </div>
                </div>
            <#else >
                <div class="form-group col-lg-10 col-sm-12 col-xs-12">
                    <label for="${columnEntity.camelColName}" class="col-lg-4 col-sm-4 col-xs-4 control-label"><${"@"}spring.message code="label.opc.${camelTableName}.${columnEntity.camelColName}"/>:</label>
                    <div class="col-lg-7 col-sm-7 col-xs-7">
                        <p>${"$"}{cur${upperCamelTableName}.${columnEntity.camelColName}!}</p>
                    </div>
                </div>
            </#if>

        </#list>
    </form>
</div>

<${"#"}include "/center/foot.html" />

</body>

</html>