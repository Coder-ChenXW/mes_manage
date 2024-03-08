package org.chenxw.mes.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.chenxw.mes.domain.EmployeeReportInfo;
import org.chenxw.mes.domain.OrderReportInfo;
import org.chenxw.mes.entity.Employee;
import org.chenxw.mes.service.EmployeeService;
import org.chenxw.mes.service.ReportService;
import org.chenxw.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: ChenXW
 * @Date:2024/2/29 16:26
 * @Description: 报表管理相关
 **/

@Api(value = "报表管理相关")
@RestController
@RequestMapping("/base/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private EmployeeService employeeService;

    /**
     * @description: 对数表查询
     * @author: ChenXW
     * @date: 2024/2/29 16:35
     */
    @ApiOperation("对数表查询")
    @GetMapping("/order/{orderId}")
    public Result<OrderReportInfo> getOrderReportInfo(@PathVariable("orderId") Long orderId) {
        return Result.generateSuccess(reportService.getOrderReportInfo(orderId));
    }


    /**
     * @description: 对数表excel
     * @author: ChenXW
     * @date: 2024/2/29 17:03
     */
    @ApiOperation("对数表excel")
    @GetMapping("/order/{orderId}/excel")
    public ResponseEntity<byte[]> exportOrderReport(@PathVariable("orderId") Long orderId) throws Exception {
        if (orderId == null || orderId == 0) {
            throw new RuntimeException("参数错误");
        }
        OrderReportInfo orderReports = reportService.getOrderReportInfo(orderId);

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        XSSFRow header0 = sheet.createRow(0);
        XSSFCell header0cell0 = header0.createCell(0);
        XSSFCell header0cell2 = header0.createCell(2);
        header0cell0.setCellValue("款号: " + orderReports.getProductNo());
        header0cell2.setCellValue("款式: " + orderReports.getProductName());


        XSSFRow header1 = sheet.createRow(1);
        Map<Long, Integer> employeeIdColMap = new HashMap<>();
        AtomicInteger colPointer = new AtomicInteger(0);
        header1.createCell(colPointer.getAndIncrement()).setCellValue("序号");
        header1.createCell(colPointer.getAndIncrement()).setCellValue("工序\\姓名");
        header1.createCell(colPointer.getAndIncrement()).setCellValue("预期数量");
        int expectQtyColIndex = colPointer.get() - 1;
        header1.createCell(colPointer.getAndIncrement()).setCellValue("实际数量");
        int actualQtyColIndex = colPointer.get() - 1;

        for (int i = 0; i < orderReports.getItems().size(); i++) {
            Map<String, Object> data = orderReports.getItems().get(i);
            XSSFRow dataRow = sheet.createRow(i + 2);
            dataRow.createCell(0).setCellValue(i + 1);
            dataRow.createCell(1).setCellValue(data.get("craftName").toString());
            employeeIdColMap.forEach((k, v) -> {
                Integer employeeQty = (Integer) data.get("emp_" + k);
                dataRow.createCell(v).setCellValue(employeeQty);
            });
            dataRow.createCell(expectQtyColIndex).setCellValue((Integer) data.get("qty"));
            dataRow.createCell(actualQtyColIndex).setCellValue((Integer) data.get("totalQty"));
        }

        XSSFCell header0cell3 = header0.createCell(colPointer.get() - 2);
        header0cell3.setCellValue("合计");

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));


        sheet.addMergedRegion(new CellRangeAddress(0, 0, colPointer.get() - 2, colPointer.get() - 1));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);

        String filename = "对数表-" + UUID.randomUUID().toString() + ".xlsx";

        HttpHeaders headers = getExcelDownloadHttpHeaders(filename);

        byte[] bytes = bos.toByteArray();


        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    private HttpHeaders getExcelDownloadHttpHeaders(String filename) throws UnsupportedEncodingException {
        String fileName = URLEncoder.encode(filename, String.valueOf(StandardCharsets.UTF_8));
        HttpHeaders headers = new HttpHeaders();
        ContentDisposition contentDisposition = ContentDisposition.builder("attachment").filename(fileName).build();
        headers.setContentDisposition(contentDisposition);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return headers;
    }

    /**
     * @description: 工艺表
     * @author: ChenXW
     * @date: 2024/2/29 19:34
     */
    @GetMapping("/craftAll")
    @ApiOperation("工艺表")
    public Result<List<Map<String, Object>>> getCraftReportInfo() {
        List<Map<String, Object>> craftReportInfo = reportService.getCraftReportInfo();
        return Result.generateSuccess(craftReportInfo);
    }


    /**
     * @description: 导出工艺表excel
     * @author: ChenXW
     * @date: 2024/2/29 22:01
     */
    @GetMapping("/craft/excel")
    @ApiOperation("导出工艺表excel")
    public ResponseEntity<byte[]> exportCraftReport() throws UnsupportedEncodingException {
        List<Map<String, Object>> craftReportInfo = reportService.getCraftReportInfo();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("工艺表");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("订单编号");
        headerRow.createCell(1).setCellValue("加工工艺");
        headerRow.createCell(2).setCellValue("单价");
        headerRow.createCell(3).setCellValue("预期数量");
        headerRow.createCell(4).setCellValue("总价");

        int rowNum = 1;
        for (Map<String, Object> craft : craftReportInfo) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue((String) craft.get("orderNo"));
            row.createCell(1).setCellValue((String) craft.get("craftName"));
            row.createCell(2).setCellValue((Double) craft.get("unitPrice"));
            row.createCell(3).setCellValue((Integer) craft.get("qty"));
            row.createCell(4).setCellValue((Double) craft.get("totalPrice"));
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            workbook.write(bos);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String filename = "工艺表-" + UUID.randomUUID().toString() + ".xlsx";

        HttpHeaders headers = getExcelDownloadHttpHeaders(filename);

        byte[] bytes = bos.toByteArray();

        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }


}
