package exportData;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.Test;

public class ExportDatabase {
	

	//"jdbc:oracle:thin:@192.168.40.129:1521:ORCL"
	public static List<Table> getAllTable(String driverName, String username, String password, 
			String jdbcConnection, String catalog, String schemaPattern, String tableNamePattern) {
		try {
			Class.forName(driverName);
			Properties props =new Properties();
			props.setProperty("user", username);
			props.setProperty("password", password);
			props.put("remarksReporting", "true");
			props.setProperty("remarks", "true");
			Connection conn = DriverManager.getConnection(jdbcConnection, props);
			//获取该oracle的元数据
			DatabaseMetaData metaData = conn.getMetaData();
			String[] types = { "TABLE" };
			//创建装Table实体类的容器
			List<Table> allTable = new ArrayList<Table>();
			//获取数据库下所有表
			ResultSet tables = metaData.getTables(catalog, schemaPattern, tableNamePattern, types);
			while(tables.next()) {
				Table table = new Table();
				//获取表名
				String tableName = tables.getString("TABLE_NAME");
				table.setTableName(tableName);
				//获取表备注
				/*String tableRemark = tables.getString("REMARKS");
				String type = tables.getString("TABLE_SCHEM");
				System.out.println(tableRemark + "---" +type + "---" );*/
				//获取该表下面的所有列
				List<Column> listColumn = getAllColumn(metaData, tableName);
				table.setListColumn(listColumn);
				allTable.add(table);
			}
			return allTable;
		} catch (Exception e) {
			System.out.println("------------获取表或列失败---------------");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取单张表下所有列
	 * @param metaData
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	private static List<Column> getAllColumn(DatabaseMetaData metaData, String tableName) throws SQLException {
		ResultSet columns = metaData.getColumns(null, null, tableName, null);
		//装column的容器
		List<Column> listColumn = new ArrayList<Column>();
		//获取主键信息
		ResultSet primaryKeys = metaData.getPrimaryKeys(null, null, tableName);
		//获取主键名
		String primaryKeyName = "";
		if (primaryKeys.next()) {
			 primaryKeyName = primaryKeys.getString("COLUMN_NAME");
			 System.out.println(tableName + "--PK--"+primaryKeyName);
		}
		//获取外键信息
		ResultSet exportedKeys = metaData.getExportedKeys(null, null, tableName);
		//获取外键名称
		String exportedKeyName = "";
		if (exportedKeys.next()) {
			exportedKeyName = exportedKeys.getString("FKCOLUMN_NAME");
			System.out.println(tableName + "--FK--"+exportedKeyName);
		}
		while(columns.next()) {
			//column容器
			Column columnInfo = new Column();
			//列名称
			String columnName = columns.getString("COLUMN_NAME");
			System.out.println("columnName :" +columnName);
			columnInfo.setColumnName(columnName);
			//如果当前列名称等于主键列名称，则将当前列的主键状态改为true
			if (columnName.equals(primaryKeyName)) {
				columnInfo.setPrimaryKey(true);
			}
			
			//如果当前列名称等于外键列名称，则将当前列的外键状态改为true
			if (columnName.equals(exportedKeyName)) {
				columnInfo.setExportedKey(true);
			}
			
			//列类型
			String columnType = columns.getString("TYPE_NAME");
			columnInfo.setColumnType(columnType);
			//列长度
			Integer columnLength = columns.getInt("COLUMN_SIZE");
			columnInfo.setColumnLength(columnLength);
			//列字段的备注
			String remarks = columns.getString("REMARKS");
			columnInfo.setRemarks(remarks);
			listColumn.add(columnInfo);
		}
		return listColumn;
	}
	
	@Test
	public void test1() throws Exception{
		getAllTable("com.mysql.jdbc.Driver", "root", "root", "jdbc:mysql://localhost:3306/snowberghobby", null, null, null);
	}
}
