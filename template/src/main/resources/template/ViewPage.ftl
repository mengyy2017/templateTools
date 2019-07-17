<${"#"}import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html lang="en">

<head>
    <title><${"@"}spring.message code="label.opc.${camelTableName}.pageTitle.view"/></title>

    <${"#"}include "/center/head.html" />

</head>

<body>

<${"#"}include "/opc/view${"upperCamelTableName"}Body.html" />
<${"#"}include "/center/foot.html" />

</body>

</html>