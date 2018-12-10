package ${packageName};

import java.io.Serializable;

/**
 * @Title: ${beanName}
 * @Description: ${tableRemark!}

 * @author ${author}
 * @date ${date}
 */
public class ${beanName} implements Serializable {

<#list fields as field>
	/** ${field.remark!} */
	private ${field.fieldType} ${field.fieldName};
</#list>

<#list fields as field>
	/** ${field.remark!} */
	public ${field.fieldType} get${field.methods}() {
		return ${field.fieldName};
	}
	/** ${field.remark!} */
	public void set${field.methods}(${field.fieldType} ${field.fieldName}) {
		this.${field.fieldName} = ${field.fieldName};
	}
</#list>

}







