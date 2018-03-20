package exportData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.Template;
/**
 * 导出数据到html
 * @author 马晓晨
 * @date 2018年3月20日
 */
public class ExportDatabaseFromFreemarker {
	
	public static final String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";
	public static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
	
	/**
	 * 将数据库数据字典创建到页面中
	 * @param freeMarkerTemplateLoading  freemarker的模板位置(到父级文件夹)
	 * @param encoding  编码方式，默认为utf8
	 * @param templateName  freemarker模板的名称
	 * @param outputLocation 输出页面的位置
	 * @param driverName	驱动名称，可从属性中获取，暂时只测试支持mysql和oracle
	 * @param username 	用户名	
	 * @param password	密码	
	 * @param jdbcConnection jdbc的连接
	 * @param catalog	匹配数据库中的catalog，为null则不缩小匹配范围
	 * @param schemaPattern  Schema匹配，为空则不缩小匹配范围
	 * @param tableNamePattern 表名称匹配，为空则不缩小匹配范围
	 */
	public static void createHtmlFromData(String freeMarkerTemplateLoading, String encoding, 
			String templateName, String outputLocation, String driverName, String username, String password, 
			String jdbcConnection, String catalog, String schemaPattern, String tableNamePattern) {
		Writer out = null;
		try {
		//创建一个Configuration对象
		Configuration configuration = new Configuration(Configuration.getVersion());
		//设置模板文件保存的目录 "D:/Workspaces/e3mall/exportData/src/main/resources/"
		configuration.setDirectoryForTemplateLoading(new File(freeMarkerTemplateLoading));
		//模板文件的编码格式
		if (encoding == null) {
			configuration.setDefaultEncoding("utf-8");
		} else {
			configuration.setDefaultEncoding(encoding);
		}
		//加载一个模板文件创建一个模板对象。"exportData.ftl"
		Template template = configuration.getTemplate(templateName);
		//创建一个数据集
		Map<String, List<Table>> data = new HashMap<String, List<Table>>();
		List<Table> listTable = ExportDatabase.getAllTable("com.mysql.jdbc.Driver", username, password, jdbcConnection, catalog, schemaPattern, tableNamePattern);
		data.put("listTable", listTable);
		//7、创建一个Writer对象，指定输出文件的路径及文件名 "D:/freemarker/oracleData.html"
		out = new FileWriter(new File(outputLocation));
		//8、生成静态页面
		template.process(data, out);
		} catch (Exception e) {
			System.out.println("导出失败");
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	@Test
	public void test() {
		createHtmlFromData("D:/test/", "utf-8", "exportData.ftl", "D:/test/data.html", MYSQL_DRIVER, "root", "root", "jdbc:mysql://localhost:3306/snowberghobby", null, null, null);
	}
}

