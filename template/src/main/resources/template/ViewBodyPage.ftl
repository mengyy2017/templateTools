<form class="form-horizontal">

    <input type="hidden" id="id" name="id" value="${"$"}{cur${upperCamelTableName}.id}">

    <#list columnList as columnEntity>
        <#if (columnEntity.columnName)?contains("_id") >
            <div class="form-group col-lg-5 col-sm-6 col-xs-6">
                <label for="${columnEntity.camelColName}" class="col-lg-6 col-sm-5 col-xs-5 control-label"><${"@"}spring.message code="label.opc.${camelTableName}.${columnEntity.camelColName}"/>：</label>
                <div class="col-lg-6 col-sm-6 col-xs-7">
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
        <#elseif (columnEntity.camelColName)?contains("changeContent")
        || (columnEntity.camelColName)?contains("document")
        || (columnEntity.camelColName)?contains("iocComments")
        || (columnEntity.camelColName)?contains("learningPathway")
        || (columnEntity.camelColName)?contains("ocogComments")
        || (columnEntity.camelColName)?contains("description")
        || (columnEntity.camelColName)?contains("msmilestoneCode")
        || (columnEntity.camelColName)?contains("item")
        || (columnEntity.camelColName)?contains("details")
        >
            <div class="form-group col-lg-12 col-sm-12 col-xs-12">
                <label for="${columnEntity.camelColName}" class="col-lg-2 col-sm-2 col-xs-2 control-label describeSpan"><${"@"}spring.message code="label.opc.${camelTableName}.${columnEntity.camelColName}"/>:</label>
                <div class="col-lg-9 col-sm-9 col-xs-9 describeTextArea">
                    <p>${"$"}{cur${upperCamelTableName}.${columnEntity.camelColName}!}</p>
                </div>
            </div>
        <#elseif (columnEntity.dataType)?contains("DATE")
        || (columnEntity.dataType)?contains("DATETIME")
        >
            <div class="form-group col-lg-5 col-sm-6 col-xs-6">
                <label for="${columnEntity.camelColName}" class="col-lg-6 col-sm-5 col-xs-5 control-label"><${"@"}spring.message code="label.opc.${camelTableName}.${columnEntity.camelColName}"/>:</label>
                <div class="col-lg-6 col-sm-6 col-xs-7">
                    <p>${"$"}{cur${upperCamelTableName}.${columnEntity.camelColName}?string('yyyy-MM-dd HH:mm:ss')}</p>
                </div>
            </div>
        <#else >
            <div class="form-group col-lg-5 col-sm-6 col-xs-6">
                <label for="${columnEntity.camelColName}" class="col-lg-6 col-sm-5 col-xs-5 control-label"><${"@"}spring.message code="label.opc.${camelTableName}.${columnEntity.camelColName}"/>:</label>
                <div class="col-lg-6 col-sm-6 col-xs-7">
                    <p>${"$"}{cur${upperCamelTableName}.${columnEntity.camelColName}!}</p>
                </div>
            </div>
        </#if>
    </#list>
</form>