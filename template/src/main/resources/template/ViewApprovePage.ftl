<${"#"}import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html lang="en">

<head>
    <title><${"@"}spring.message code="label.opc.${camelTableName}.pageTitle.approve"/></title>

    <${"#"}include "/center/head.html" />

</head>

<body>

<div style="height: inherit; overflow-y: scroll;">

    <${"#"}include "/opc/view${"upperCamelTableName"}Body.html" />
    <${"#"}include "/workflow/approveCommon.html" />
    <${"#"}include "/workflow/defApproveFooter.html" />
</div>

<${"#"}include "/center/foot.html" />

</body>

</html>