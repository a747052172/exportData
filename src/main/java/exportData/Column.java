package exportData;

/**
 * 数据库表中列字段的实体类
 * @author 马晓晨
 * @date 2017年9月25日
 */
public class Column {
	//列名
	private String columnName;
	//列类型
	private String columnType;
	//类型长度
	private Integer columnLength;
	//备注
	private String remarks;
	//是否是主键
	private boolean primaryKey = false;
	//是否是外键
	private boolean exportedKey = false;
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getColumnType() {
		return columnType;
	}
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	public Integer getColumnLength() {
		return columnLength;
	}
	public void setColumnLength(Integer columnLength) {
		this.columnLength = columnLength;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public boolean isPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}
	public boolean isExportedKey() {
		return exportedKey;
	}
	public void setExportedKey(boolean exportedKey) {
		this.exportedKey = exportedKey;
	}

	
}
