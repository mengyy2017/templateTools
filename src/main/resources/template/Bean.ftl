package ${packageName};

import java.io.Serializable;

/**
 * @Title: ${tableName}
 * @Description: ${tableRemark!}

 * @author ${author}
 * @date ${date}
 */
public class ${tableName} implements Serializable {

<#list columnList as column>
	/** ${column.remark!} */
	private ${column.fieldType} ${column.fieldName};
</#list>

<#list columnList as column>
	/** ${column.remark!} */
	public ${column.fieldType} get${column.methods}() {
		return ${column.fieldName};
	}
	/** ${column.remark!} */
	public void set${column.methods}(${column.fieldType} ${column.fieldName}) {
		this.${column.fieldName} = ${column.fieldName};
	}
</#list>

}







