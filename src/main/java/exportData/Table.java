package exportData;

import java.util.List;

/**
 * 数据库表实体类
 * @author 马晓晨
 * @date 2017年9月25日
 */
public class Table {
	//表名
	private String tableName;
	//所有列
	private List<Column> listColumn;
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public List<Column> getListColumn() {
		return listColumn;
	}
	public void setListColumn(List<Column> listColumn) {
		this.listColumn = listColumn;
	}
}
