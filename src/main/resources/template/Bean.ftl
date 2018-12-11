package ${packageName!};

import java.io.Serializable;

/**
 * @Title: ${upperCamelTableName}
 * @Description: ${tableRemark!}

 * @author ${author!}
 * @date ${date!}
 */
public class ${upperCamelTableName} implements Serializable {

<#list columnList as column>

	private ${column.javaType} ${column.camelColName}; // ${column.columnComment!}
</#list>

<#list columnList as column>
	/** ${column.columnComment!} */
	public ${column.javaType} get${column.upperCamelColName}() {
		return ${column.camelColName};
	}
	/** ${column.columnComment!} */
	public void set${column.upperCamelColName}(${column.javaType} ${column.camelColName}) {
		this.${column.camelColName} = ${column.camelColName};
	}
</#list>

}







