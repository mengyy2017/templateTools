<${"#"}import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html lang="en">

<head>
    <title><${"@"}spring.message code="label.opc.${camelTableName}.pageTitle.list"/></title>

    <${"#"}include "/center/head.html" />
</head>

<body>
    <div class="listPage">
        <div class="section_name" style="display:none;"><${"@"}spring.message code="label.opc.${camelTableName}.pageTitle.list"/></div>
        <div class="section_content_box">
            <div class="section_content_box_content">
                <p class="sth"><${"@"}spring.message code="label.opc.${camelTableName}.pageTitle.list"/></p>
                <div class="section_content_box_content_content">
                    <div class="searchInput">
                        <form class="form" id="queryForm">
                            <#list columnList as columnEntity>
                            <#if (columnEntity.columnName)?contains("_id") >
                                <div class="formInput">
                                    <label for="${columnEntity.camelColName}"><${"@"}spring.message code="label.opc.${camelTableName}.${columnEntity.camelColName}"/>：</label>
                                    <select name="${columnEntity.camelColName}" id="${columnEntity.camelColName}">
                                        <${"#"}if ${columnEntity.camelColName}RefList?exists  && (${columnEntity.camelColName}RefList?size > 0) >
                                            <option value="">请选择</option>
                                            <${"#"}list ${columnEntity.camelColName}RefList as itemInfo>
                                                <${"#"}if (itemInfo.isValid)! == "1" && (itemInfo.isShow)! == "1"><option value="${"$"}{itemInfo.id!}"
                                                <${"#"}if (q.${columnEntity.camelColName})! == itemInfo.id!>selected</${"#"}if>>${"$"}{itemInfo.name!}</option></${"#"}if>
                                        </${"#"}list>
                                        </${"#"}if>
                                    </select>
                                </div>
                                <div class="formInput">
                                    <label for="${columnEntity.camelColName}"><${"@"}spring.message code="label.opc.${camelTableName}.${columnEntity.camelColName}"/>：</label>
                                    <input type="text" style="" id="${columnEntity.camelColName}" name="${columnEntity.camelColName}" value="${"$"}{q.${columnEntity.camelColName}!}" placeholder="">
                                </div>
                            </#if>
                            </#list>

                            <div class="btn">
                                <button type="submit" class="search"><${"@"}spring.message code="label.action.query"/></button>
                                <button type="button" class="reset" onclick="clearForm()"><${"@"}spring.message code="label.action.reset"/></button>
                            </div>
                        </form>
                        <div class="addDiv">
                            <div class="addImg">
                                <img src="/img/plus.png" alt="">
                            </div>
                            <span><${"@"}spring.message code="label.action.add"/></span>
                        </div>
                    </div>
                    <div class="table-responsive">
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <td><${"@"}spring.message code="label.data.no"/></td>
                                    <#list columnList as columnEntity>
                                        <td><${"@"}spring.message code="label.opc.${camelTableName}.${columnEntity.camelColName}"/></td>
                                    </#list>
                                    <td><${"@"}spring.message code="label.action.operations"/></td>
                                </tr>
                            </thead>
                            <tbody>
                                <${"#"}list page.list as q>
                                <tr id="${"$"}{q.id!}">
                                    <input class="clsId" type="hidden"/>
                                    <td>${"$"}{(page.pageNum-1)*page.pageSize+q_index+1}</td>

                                    <#list columnList as columnEntity>
                                        <#if (columnEntity.columnName)?contains("_id") >
                                            <td>
                                                <${"#"}if ${columnEntity.camelColName}RefList?exists  && (${columnEntity.camelColName}RefList?size > 0) >
                                                    <${"#"}list ${columnEntity.camelColName}RefList as itemInfo>
                                                        <${"#"}if (itemInfo.isValid)! == "1" && (itemInfo.isShow)! == "1" && (q.${columnEntity.camelColName})! == itemInfo.id!>
                                                        ${"$"}{itemInfo.name!}</${"#"}if>
                                                </${"#"}list>
                                                </${"#"}if>
                                            </td>

                                        <#elseif (columnEntity.dataType)?contains("DATE")
                                        || (columnEntity.dataType)?contains("DATETIME")
                                        >
                                            <td>${"$"}{(q.${columnEntity.camelColName!}?string('yyyy-MM-dd HH:mm:ss'))!}</td>
                                        <#else >
                                            <td>${"$"}{q.${columnEntity.camelColName!}!}
                                        </#if>
                                    </#list>

                                    <td>
                                        <i class="iconfont detail" itemId="${"$"}{q.id!}" title='<${"@"}spring.message code="label.action.detail"/>'>&#xe668;</i>
                                        <i class="iconfont bianji" itemId="${"$"}{q.id!}" title='<${"@"}spring.message code="label.action.edit"/>'>&#xe62e;</i>
                                        <i class="iconfont tijiao" style="background: #58ade4; margin-left: 3px;" itemId="${"$"}{q.id!}" title='<${"@"}spring.message code="lable.ht.workflow.submit"/>'>&#xe6ff;</i>
                                        <i class="iconfont shanchu" itemId="${"$"}{q.id!}" title='<${"@"}spring.message code="label.action.delete"/>'>&#xe60a;</i>
                                    </td>
                                </tr>
                                </${"#"}list>
                            </tbody>
                        </table>
                    </div>
                </div>
                <${"#"}include "/center/pagination.html" />
            </div>

        </div>
    </div>
    <${"#"}include "/center/foot.html" />
    <script>
        //清空页面查询项
	    function clearForm() {
	        $(".form input").each(function(){
	            $(this).val('');
	        });
	        $(".form select").each(function(){
	            $(this).val('');
	        });
	    }

        $('.addDiv').on('click', function(e) {
            e.stopPropagation();
            top.layer.open({
                type: 2,
                resize: false,
                title: '<${"@"}spring.message code="label.opc.${camelTableName}.pageTitle.new"/>',
                area: ['60%', '80%'],
                shade: 0.4,
                maxmin: true,
                offset: 'auto',
                content: "${"$"}{basePath}/opc/new${upperCamelTableName}",
                btn: ['<${"@"}spring.message code="label.action.ok"/>', '<${"@"}spring.message code="label.action.cancel"/>'],
                yes: function (index, layero) {
                    var iframeWin = top.window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                    iframeWin.save(window);
                },
                full: function() {
                    $('.layui-layer-content').css("height", "calc(100% - 42px)");
                    $('.layui-layer-iframe iframe').css("height", "100%");
                },
                restore: function() {
                    $('.layui-layer-content').css("height", "calc(100% - 42px)");
                    $('.layui-layer-iframe iframe').css("height", "100%");
                }
            });
        })
        $('.detail').on('click', function(e) {
            e.stopPropagation();
            var id = $(this).parent().parent().attr("id");
            top.layer.open({
                type: 2,
                resize: false,
                title: '<${"@"}spring.message code="label.opc.${camelTableName}.pageTitle.view"/>',
                area: ['60%', '80%'],
                shade: 0.4,
                maxmin: true,
                offset: 'auto',
                content: "${"$"}{basePath}/opc/view${upperCamelTableName}?id=" + id,
                btn: ['<${"@"}spring.message code="label.action.close"/>'],
                full: function() {
                    $('.layui-layer-content').css("height", "calc(100% - 42px)");
                    $('.layui-layer-iframe iframe').css("height", "100%");
                },
                restore: function() {
                    $('.layui-layer-content').css("height", "calc(100% - 42px)");
                    $('.layui-layer-iframe iframe').css("height", "100%");
                }
            });
        })
        $('.tijiao').on('click', function(e) {
            e.stopPropagation();
            var id = $(this).parent().parent().attr("id");
            top.layer.open({
                type: 2,
                resize: false,
                title: '<${"@"}spring.message code="label.opc.${camelTableName}.pageTitle.approve"/>',
                area: ['80%', '80%'],
                shade: 0.4,
                maxmin: true,
                offset: 'auto',
                content: "${"$"}{basePath}/opc/view${upperCamelTableName}Approve?id=" + id,
                btn: ['<${"@"}spring.message code="label.action.ok"/>', '<${"@"}spring.message code="label.action.close"/>'],
                yes: function (index, layero) {
                    var iframeWin = top.window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                    iframeWin.approve(window)
                },
                full: function() {
                    $('.layui-layer-content').css("height", "calc(100% - 42px)");
                    $('.layui-layer-iframe iframe').css("height", "100%");
                },
                restore: function() {
                    $('.layui-layer-content').css("height", "calc(100% - 42px)");
                    $('.layui-layer-iframe iframe').css("height", "100%");
                }
            });
        })
        $('.bianji').on('click', function(e) {
            e.stopPropagation();
            var id = $(this).parent().parent().attr("id");
            top.layer.open({
                type: 2,
                resize: false,
                title: '<${"@"}spring.message code="label.opc.${camelTableName}.pageTitle.edit"/>',
                area: ['60%', '80%'],
                shade: 0.4,
                maxmin: true,
                offset: 'auto',
                content: "${"$"}{basePath}/opc/edit${upperCamelTableName}?id=" + id,
                btn: ['<${"@"}spring.message code="label.action.ok"/>', '<${"@"}spring.message code="label.action.cancel"/>'],
                yes: function (index, layero) {
                    var iframeWin = top.window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                    iframeWin.update(window);
                },
                full: function() {
                    $('.layui-layer-content').css("height", "calc(100% - 42px)");
                    $('.layui-layer-iframe iframe').css("height", "100%");
                },
                restore: function() {
                    $('.layui-layer-content').css("height", "calc(100% - 42px)");
                    $('.layui-layer-iframe iframe').css("height", "100%");
                }
            });
        })

        $('.shanchu').on('click', function(e) {
            e.stopPropagation();
            var id = $(this).parent().parent().attr("id");
            layer.confirm('<${"@"}spring.message code="msg.delete.confirm"/>', {
            	btn: ['<${"@"}spring.message code="label.action.ok"/>', '<${"@"}spring.message code="label.action.cancel"/>'],
            }, function () {
                var header = $("meta[name='_csrf_header']").attr("content");
                var token = $("meta[name='_csrf']").attr("content");
                $.ajax({
                    type: "post",
                    url: "${"$"}{basePath}/opc/delete${upperCamelTableName}",
                    data: {id: id},
                    beforeSend: function (xhr) {
                        xhr.setRequestHeader(header, token);
                    },
                    success: function (data) {
                        var res = JSON.parse(data);
                        if (res.code == "success") {
                            layer.msg('<${"@"}spring.message code="msg.delete.success"/>', {time: TIMES}, function () {
                                location.reload();
                            });
                        } else {
                            layer.msg('<${"@"}spring.message code="msg.delete.failure"/>', {
                                time: TIMES
                            });
                        }
                    }
                })
                return true;
            });
        })
        
        function locationReload() {
            setTimeout(function () {
                location.reload()
            }, 500)
        }
    </script>
</body>

</html>