package com.boci.PrintPDF;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.util.StringUtil;

import net.sf.jsqlparser.JSQLParserException;  
import net.sf.jsqlparser.expression.Expression;  
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;  
import net.sf.jsqlparser.parser.CCJSqlParserManager;  
import net.sf.jsqlparser.parser.CCJSqlParserUtil;  
import net.sf.jsqlparser.schema.Column;  
import net.sf.jsqlparser.schema.Table;  
import net.sf.jsqlparser.statement.Statement;  
import net.sf.jsqlparser.statement.insert.Insert;  
import net.sf.jsqlparser.statement.select.Join;  
import net.sf.jsqlparser.statement.select.OrderByElement;  
import net.sf.jsqlparser.statement.select.PlainSelect;  
import net.sf.jsqlparser.statement.select.Select;  
import net.sf.jsqlparser.statement.select.SelectItem;  
import net.sf.jsqlparser.statement.update.Update;  
import net.sf.jsqlparser.util.TablesNamesFinder;

public class testParseSql {
	
	public static List<String> test_select_items(String sql)  
            throws JSQLParserException {  
        CCJSqlParserManager parserManager = new CCJSqlParserManager();  
        Select select = (Select) parserManager.parse(new StringReader(sql));  
        PlainSelect plain = (PlainSelect) select.getSelectBody();  
        List<SelectItem> selectitems = plain.getSelectItems();  
        List<String> str_items = new ArrayList<String>();  
        if (selectitems != null) {  
            for (int i = 0; i < selectitems.size(); i++) {  
                str_items.add(selectitems.get(i).toString());  
                System.out.println(str_items.get(i));
            }  
        }  
        return str_items;  
    }  
	
	public static List<String> getTableNameBySql(String sql) throws JSQLParserException{  
		Statement statement = (Statement) CCJSqlParserUtil.parse(sql);  
        Select selectStatement = (Select) statement;  
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();  
        List<String> tableList = tablesNamesFinder  
                .getTableList(selectStatement);  
        for(int i=0;i<tableList.size();i++){
        	System.out.println(tableList.get(i));
        }
        return tableList;
	}
	
	public static void main(String args[]) throws JSQLParserException{
		testParseSql t1 =new testParseSql();
		
		String sql = "SELECT SCORE.SNO,SCORE.CNO,GRADE.RANK FROM SCORE A,GRADE B WHERE A.DEGREE BETWEEN B.LOW AND B.UPP ORDER BY RANK";
		t1.getTableNameBySql(sql);
		if(true){
			sql = sql.split("FROM")[0];
			System.out.println(sql);
			Util.
		}
		
	}
}
