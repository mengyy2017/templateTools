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

	private ${column.javaFiledType} ${column.camelColName}; // ${column.columnComment!}
</#list>

<#list columnList as column>
	/** ${column.columnComment!} */
	public ${column.javaFiledType} get${column.upperCamelColName}() {
		return ${column.camelColName};
	}
	/** ${column.columnComment!} */
	public void set${column.upperCamelColName}(${column.javaFiledType} ${column.camelColName}) {
		this.${column.camelColName} = ${column.camelColName};
	}
</#list>

}







