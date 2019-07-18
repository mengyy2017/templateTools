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
                    <div class="form-group col-lg-10 col-sm-12 col-xs-12">
                        <label for="${columnEntity.camelColName}" class="col-lg-4 col-sm-4 col-xs-4 control-label"><${"@"}spring.message code="label.opc.${camelTableName}.${columnEntity.camelColName}"/>：</label>
                        <div class="col-lg-7 col-sm-7 col-xs-7">
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
                <#else >
                    <div class="form-group col-lg-10 col-sm-12 col-xs-12">
                        <label for="${columnEntity.camelColName}" class="col-lg-4 col-sm-4 col-xs-4 control-label"><${"@"}spring.message code="label.opc.${camelTableName}.${columnEntity.camelColName}"/>:</label>
                        <div class="col-lg-7 col-sm-7 col-xs-7">
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
