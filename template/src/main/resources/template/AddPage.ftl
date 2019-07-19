<${"#"}import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html lang="en">

<head>
    <title><${"@"}spring.message code="label.opc.${camelTableName}.pageTitle.new"/></title>

    <${"#"}include "/center/head.html" />
</head>

<body>
    <div class="sthHtml">
        <form class="form-horizontal" id="new${upperCamelTableName}Form">
            <input type="hidden" id="id" name="id" value="${"$"}{cur${upperCamelTableName}.id!}">

            <#list columnList as columnEntity>
                <#if (columnEntity.columnName)?contains("_id") >
                    <div class="form-group col-lg-5 col-sm-6 col-xs-6">
                        <label for="${columnEntity.camelColName}" class="col-lg-6 col-sm-5 col-xs-5 control-label"><${"@"}spring.message code="label.opc.${camelTableName}.${columnEntity.camelColName}"/>：</label>
                        <div class="col-lg-6 col-sm-6 col-xs-7">
                        <select class="form-control selects" name="${columnEntity.camelColName}" id="${columnEntity.camelColName}">
                            <${"#"}if ${columnEntity.camelColName}RefList?exists  && (${columnEntity.camelColName}RefList?size > 0) >
                                <option value="">请选择</option>
                                <${"#"}list ${columnEntity.camelColName}RefList as itemInfo>
                                    <${"#"}if (itemInfo.isValid)! == "1" && (itemInfo.isShow)! == "1"><option value="${"$"}{itemInfo.id!}"
                                    <${"#"}if (cur${upperCamelTableName}.${columnEntity.camelColName})! == itemInfo.id!>selected</${"#"}if>>${"$"}{itemInfo.name!}</option></${"#"}if>
                                </${"#"}list>
                            </${"#"}if>
                        </select>
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
                    || (columnEntity.camelColName)?contains("dataContent")
                    || (columnEntity.camelColName)?contains("goodsName")
                    || (columnEntity.camelColName)?contains("registrationNumber")
                    || (columnEntity.camelColName)?contains("authentification")
                    || (columnEntity.camelColName)?contains("totalProduced")
                    || (columnEntity.camelColName)?contains("limitations")
                    || (columnEntity.camelColName)?contains("contracts")
                >
                    <div class="form-group col-lg-12 col-sm-12 col-xs-12">
                        <label for="${columnEntity.camelColName}" class="col-lg-2 col-sm-2 col-xs-2 control-label describeSpan"><${"@"}spring.message code="label.opc.${camelTableName}.${columnEntity.camelColName}"/>:</label>
                        <div class="col-lg-9 col-sm-9 col-xs-9 describeTextArea">
                            <textarea type="text" maxlength="${columnEntity.characterMaximumLength!}" class="form-control" id="${columnEntity.camelColName}" name="${columnEntity.camelColName}" value="${"$"}{cur${upperCamelTableName}.${columnEntity.camelColName}!}" placeholder="<${"@"}spring.message code="msg.input.tips"/><${"@"}spring.message code="label.opc.${camelTableName}.${columnEntity.camelColName}"/>"></textarea>
                        </div>
                    </div>
                <#elseif (columnEntity.dataType)?contains("DATE")
                    || (columnEntity.dataType)?contains("DATETIME")
                >
                    <div class="form-group col-lg-5 col-sm-6 col-xs-6">
                        <label for="${columnEntity.camelColName}" class="col-lg-6 col-sm-5 col-xs-5 control-label"><${"@"}spring.message code="label.opc.${camelTableName}.${columnEntity.camelColName}"/>:</label>
                        <div class="col-lg-6 col-sm-6 col-xs-7">
                            <input type="text" maxlength="${columnEntity.characterMaximumLength!}" class="form-control" id="${columnEntity.camelColName}" name="${columnEntity.camelColName}" value="${"$"}{(cur${upperCamelTableName}.${columnEntity.camelColName}?string('yyyy-MM-dd HH:mm:ss'))!}" placeholder="<${"@"}spring.message code="msg.input.tips"/><${"@"}spring.message code="label.opc.${camelTableName}.${columnEntity.camelColName}"/>">
                        </div>
                    </div>
                <#else >
                    <div class="form-group col-lg-5 col-sm-6 col-xs-6">
                        <label for="${columnEntity.camelColName}" class="col-lg-6 col-sm-5 col-xs-5 control-label"><${"@"}spring.message code="label.opc.${camelTableName}.${columnEntity.camelColName}"/>:</label>
                        <div class="col-lg-6 col-sm-6 col-xs-7">
                            <input type="text" maxlength="${columnEntity.characterMaximumLength!}" class="form-control" id="${columnEntity.camelColName}" name="${columnEntity.camelColName}" value="${"$"}{cur${upperCamelTableName}.${columnEntity.camelColName}!}" placeholder="<${"@"}spring.message code="msg.input.tips"/><${"@"}spring.message code="label.opc.${camelTableName}.${columnEntity.camelColName}"/>">
                        </div>
                    </div>
                </#if>
            </#list>
        </form>
    </div>

    <${"#"}include "/center/foot.html" />

</body>

</html>
<script>
    function save(listWin) {
        var header = $("meta[name='_csrf_header']").attr("content");
        var token = $("meta[name='_csrf']").attr("content");
        $.ajax({
            type: "post",
            url: "${"$"}{basePath}/opc/save${upperCamelTableName}",
            data: $("#new${upperCamelTableName}Form").serialize(),
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (response) {
                var response = response || "{}";
                response = JSON.parse(response);
                if(response.code == "success"){
                    layer.msg('<${"@"}spring.message code="msg.save.success"/>', {shade: 0.3, time: TIMES}, function () {
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);
                        listWin.locationReload();
                    });

                }else{
                    layer.msg('<${"@"}spring.message code="msg.save.failure"/>', {
                        shade:0.3,
                        time: TIMES
                    });
                }
            }
        })
    }
</script>
