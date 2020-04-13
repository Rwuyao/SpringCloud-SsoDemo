package org.user.manage;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.user.manage.entity.ComplexHeadData;
import org.user.manage.entity.ConverterData;
import org.user.manage.entity.SN;

import com.alibaba.excel.EasyExcel;
@RunWith(SpringRunner.class)
//启动Spring
@SpringBootTest
public class EasyExcelTest {
		@Test
	    public void ReadExcel() throws Exception {
			
			List<Object> list = EasyExcel.read(Files.newInputStream( Paths.get("D:","11.xls"))).sheet(0).doReadSync();
			int t=list.size();
			System.out.println(t);
	    }
		@Test
		public void ReadExcelByListener()  throws Exception{
			//EasyExcel.read(Files.newInputStream( Paths.get("D:","11.xls")),new SNListener()).sheet().doRead();
			List<String> snlist=new ArrayList<>();
			EasyExcel.read(Files.newInputStream( Paths.get("D:","11.xls")),SN.class,new SNListener(snlist)).sheet().headRowNumber(0).doRead();
			System.out.println(snlist.size());
		}
		
		
		@Test
	    public void complexHeadWrite() {
	        String fileName =   "D://complexHeadWrite" + System.currentTimeMillis() + ".xlsx";
	        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
	        EasyExcel.write(fileName, ComplexHeadData.class).sheet("模板").doWrite(data());
	    }

		
		 @Test
		    public void converterWrite() {
		        String fileName ="D://converterWrite" + System.currentTimeMillis() + ".xlsx";
		        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
		        EasyExcel.write(fileName, ConverterData.class).sheet("模板").doWrite(data());
		    }
		
		 @Test
		    public void dynamicHeadWrite() {
		        String fileName = "D://dynamicHeadWrite" + System.currentTimeMillis() + ".xlsx";
		        EasyExcel.write(fileName)
		            // 这里放入动态头
		            .head(head()).sheet("模板")
		            // 当然这里数据也可以用 List<List<String>> 去传入
		            .doWrite(data());
		    }
		 
		 /**
		     * 不创建对象的写
		     */
		    @Test
		    public void noModleWrite() {
		        // 写法1
		        String fileName = "D://noModleWrite" + System.currentTimeMillis() + ".xlsx";
		        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
		        EasyExcel.write(fileName).head(head()).sheet("模板").doWrite(dataList());
		    }

//		    private List<List<Object>> dataList() {
//		        List<List<Object>> list = new ArrayList<List<Object>>();
//		        for (int i = 0; i < 10; i++) {
//		            List<Object> data = new ArrayList<Object>();
//		            data.add("字符串" + i);
//		            data.add(new Date());
//		            data.add(0.56);
//		            list.add(data);
//		        }
//		        return list;
//		    }
		    
		    private List<List<Object>> dataList() {
		        List<List<Object>> list = new ArrayList<>();
		        for (int i = 0; i < 10; i++) {
		        	LinkedHashMap<String,Object> map = new LinkedHashMap<>();
		        	map.put("字符串1", "12232");
		        	map.put("字符串2", "12232");
		        	map.put("字符串3", "12232");
		        	List<Object> ii=new ArrayList<>(map.values());
		            list.add(ii);
		        }
		        return list;
		    }
		    
//		    private List<Collection<Object>> dataList() {
//		        List<Collection<Object>> list = new ArrayList<>();
//		        for (int i = 0; i < 10; i++) {
//		        	LinkedHashMap<String,Object> map = new LinkedHashMap<>();
//		        	map.put("字符串1", "12232");
//		        	map.put("字符串2", "12232");
//		        	map.put("字符串3", "12232");		        
//		        	Collection<Object> ii=map.values();
//		            list.add(ii);
//		        }
//		        return list;
//		    }
		    
		 private List<List<String>> head() {
		        List<List<String>> list = new ArrayList<List<String>>();
		        List<String> head0 = new ArrayList<String>();
		        head0.add("字符串" + System.currentTimeMillis());
		        head0.add("字符串" + System.currentTimeMillis());
		        List<String> head1 = new ArrayList<String>();
		        head1.add("数字" + System.currentTimeMillis());
		        List<String> head2 = new ArrayList<String>();
		        head2.add("日期" + System.currentTimeMillis());
		        list.add(head0);
		        list.add(head1);
		        list.add(head2);
		        return list;
		    } 
		private List<ComplexHeadData> data() {
	        List<ComplexHeadData> list = new ArrayList<ComplexHeadData>();
	        for (int i = 0; i < 10; i++) {
	        	ComplexHeadData data = new ComplexHeadData();
	            data.setString("字符串" + i);
	            data.setDate(new Date());
	            data.setDoubleData(0.56);
	            list.add(data);
	        }
	        return list;
	    }
		
}
