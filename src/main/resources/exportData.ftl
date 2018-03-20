<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title></title>
	</head>
	<body>
		<#list listTable as table>
		<center><h3>${table.tableName}</h3></center>
		<table align="center" border="1">
			<thead>
				<tr>
					<td>列名</td>
					<td>中文含义</td>
					<td>数据类型</td>
					<td>主键 </td>
					<td>外键</td>
					<td>不为空</td>
					<td>备注 </td>
				</tr> 
			</thead>
			<tbody>
			
				<#list table.listColumn as column>
				<tr>
					<td>${column.columnName}</td>
					<#if column.remarks??>
						<td>${column.remarks}</td>
					<#else>
						<td></td>
					</#if>
					
					<td>${column.columnType}</td>
					<#if column.primaryKey == true>
						<td>T</td>
					<#else>
						<td>F</td>
					</#if>
					<td></td>
					<#if column.primaryKey == true>
						<td>T</td>
					<#else>
						<td>F</td>
					</#if>
					<td></td>
				</tr>
				</#list>
				
			</tbody>
		</table>
		</#list>
	</body>
</html>