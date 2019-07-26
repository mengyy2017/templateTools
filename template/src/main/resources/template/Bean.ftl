package ${packageName!};

import java.io.Serializable;

/**
 * @Title: ${upperCamelTableName}
 * @Description: ${tableRemark!}

 * @author ${author!}
 * @date ${date!}
 */
public class ${upperCamelTableName}Entity implements Serializable {

<#list columnList as column>
	<#if column.columnKey! == "PRI">
	private String id; // id
	<#elseif column.columnKey! != "PRI">
	<#if (column.isFronted)??>${"@"}ExcelAnnotation(order = ${column.sortIndex!}, exportName = "${column.camelColName}")</#if>
	private ${column.javaFiledType} ${column.camelColName}; // ${column.columnComment!}
	</#if>
</#list>

<#list columnList as column>
<#if column.columnKey! == "PRI">
	/** 获取id */
	public String getId() {
		return id;
	}
	/** 设置id */
	public void setId(String id) {
		this.id = id;
	}
<#elseif column.columnKey! != "PRI">
	/** ${column.columnComment!} */
	public ${column.javaFiledType} get${column.upperCamelColName}() {
		return ${column.camelColName};
	}
	/** ${column.columnComment!} */
	public void set${column.upperCamelColName}(${column.javaFiledType} ${column.camelColName}) {
		this.${column.camelColName} = ${column.camelColName};
	}
</#if>

</#list>

}







