package org.user.manage;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.user.manage.entity.SN;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;

public class SNListener extends AnalysisEventListener<SN>  {

	 private static final Logger LOGGER = LoggerFactory.getLogger(SNListener.class);
	    /**
	     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
	     */    
	    List<String> list;
	    public SNListener() {
	    }

	    /**
	     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
	     *
	     * @param demoDAO
	     */
	    public SNListener(List<String> snlist) {
	        this.list = snlist;
	    }
	
	    public void onException(Exception exception, AnalysisContext context) {
	        LOGGER.error("解析失败，但是继续解析下一行:{}", exception.getMessage());
	        // 如果是某一个单元格的转换异常 能获取到具体行号
	        // 如果要获取头的信息 配合invokeHeadMap使用
	        if (exception instanceof ExcelDataConvertException) {
	            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException)exception;
	            LOGGER.error("第{}行，第{}列解析异常，数据为:{}", excelDataConvertException.getRowIndex(),
	                excelDataConvertException.getColumnIndex(), excelDataConvertException.getCellData());
	        }
	    }    
	    
	    
	@Override
	public void invoke(SN data, AnalysisContext context) {
		// TODO Auto-generated method stub
		 LOGGER.info("解析到一条数据:{}", data);
	        list.add(data.getSnid());
	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext context) {
		// TODO Auto-generated method stub
		LOGGER.info("所有数据解析完成！");

	}

}
