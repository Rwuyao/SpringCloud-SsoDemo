package org.user.manage.utils;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.user.manage.entity.SN;
import com.alibaba.excel.EasyExcel;
public class ExcelUtils {
	
	/**
	 *  传入输入流及标题开始行，读取第一个sheet的第一列，返回List<String>
	 * @param in
	 * @param headRowNumber
	 * @return
	 */
	public static List<String> SimpleRead(InputStream in,int headRowNumber){
		List<String> snlist=new ArrayList<>();
		EasyExcel
			.read(in,SN.class,new SNListener(snlist))
			.sheet()
			.headRowNumber(headRowNumber)
			.doRead();
		return snlist;	
	}
	
	public static void SimpleWirte(OutputStream out,List<HashMap<String,Object>> list) {
		
        EasyExcel
        	.write(out)
        	.head(ConvertHead(list))
        	.sheet("sheet1")
        	.doWrite(ConvertDate(list));
	}
	
	public static List<List<String>> ConvertHead(List<HashMap<String,Object>> list){
		
		List<List<String>> result = new ArrayList<>();
		Set<String> head=list.get(0).keySet();
		head.stream().forEach(h->{
			result.add(Arrays.asList(h));		
		});
		return result;
	}
	
	public static List<List<Object>> ConvertDate(List<HashMap<String,Object>> list){
		
		 List<List<Object>> result = new ArrayList<>();
	     for (HashMap<String,Object> m :list) {
	        	List<Object> ii=new ArrayList<>(m.values());
	        	result.add(ii);
	     }
	     return result;
	}

}
