<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>编辑${tableRemark!}</title>
    <link rel="stylesheet" href="/lib/bootstrap.min.css">
    <!--[if !IE]>-->
    <link rel="stylesheet" href="/lib/swiper.min.css">
    <!--<![endif]-->
    <!--[if IE]>
    <link rel="stylesheet" href="/lib/idangerous.swiper2.7.6.css">
    <![endif]-->
    <link rel="stylesheet" href="/lib/layer/theme/default/layer.css">
    <link rel="stylesheet" href="/lib/layui/css/layui.css">
    <link rel="stylesheet" href="/lib/iconfonts/iconfont.css">
    <link rel="stylesheet" href="/css/base.css">
    <link rel="stylesheet" href="/css/pm.css">

    ${'<#include "/center/head.html" />'}
</head>

<body>
<div class="sthHtml">
    <form class="form-horizontal" id="edit${upperCamelTableName}Form">

    <#list columnList as column>
        <#if column.columnKey! == "PRI">
        <input type="hidden" id="id" name="id" value="${r'${cur'}${upperCamelTableName}.id}">
        <#elseif column.characterMaximumLength! == "1">
        <div class="form-group col-lg-10 col-sm-12 col-xs-12">
            <label for="isValid" class="col-lg-4 col-sm-4 col-xs-4 control-label">${column.columnComment!}:</label>
            <div class="col-lg-7 col-sm-7 col-xs-7">
                <select class="form-control selects" id="${column.camelColName}" name="${column.camelColName}" data-index="0">
                    ${'<option value="1" <#if cur'}${upperCamelTableName}${'.'}${column.camelColName}${' == "1">selected</#if>>是</option>'}
                    ${'<option value="0" <#if cur'}${upperCamelTableName}${'.'}${column.camelColName}${' == "0">selected</#if>>否</option>'}
                </select>
            </div>
        </div>
        <#else>
        <div class="form-group col-lg-10 col-sm-12 col-xs-12">
            <label for="code" class="col-lg-4 col-sm-4 col-xs-4 control-label">${column.columnComment!}:</label>
            <div class="col-lg-7 col-sm-7 col-xs-7">
                <input type="text" class="form-control" id="${column.camelColName}" name="${column.camelColName}" value="${r'${cur'}${upperCamelTableName}.${column.camelColName}}" placeholder="请填写${column.columnComment!}">
            </div>
        </div>
        </#if>
    </#list>

    </form>
</div>
<!--[if !IE]>-->
<script src="/lib/jquery-3.3.1.min.js"></script>
<script src="/lib/swiper.min.js"></script>
<!--<![endif]-->
<!--[if IE]>
<script src="/js/baseBussiness.js"></script>
<script src="/lib/jquery-1.9.0.min.js"></script>
<script src="/lib/idangerous.swiper2.7.6.js"></script>
<script src="/js/wh.js"></script>
<![endif]-->
<script src="/lib/layer/layer.js"></script>
<script src="/lib/laydate/laydate.js"></script>
<script src="/lib/layui/layui.js"></script>
<script src="/js/data.js"></script>
<script src="/js/css.js"></script>

</body>

</html>
<script>
    function update() {
        var header = $("meta[name='_csrf_header']").attr("content");
        var token = $("meta[name='_csrf']").attr("content");
        $.ajax({
            type: "post",
            url: "/${moduleName}/update${upperCamelTableName}",    //向后端请求数据的url
            data: $("#edit${upperCamelTableName}Form").serialize(),
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (response) {
                var index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
            }
        })
    }
</script>
