package com.cj.utils;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExcelUtil {

   /* public static void main(String[] args) throws IOException {
        readExcel("d:\\\\github\\\\workspace_private\\\\word\\\\知识库最新版2.20.xlsx");
    }*/


    /*public static void main(String[] args)   {

        String test = "力盟商业巷（纺织品大楼）";
        String test1 = "15";
        String test2 = "新宁广场南";
        String test3 = "16";

        System.out.println(flushLeft(' ', 26 , test) + flushLeft(' ', 26 , test1));
        System.out.println(flushLeft(' ', 26 , test2) + flushLeft(' ', 26 , test3));


    }*/


    /**
     * 根据Unicode编码判断中文汉字和符号
     * @param c
     * @return
     */
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {

            return true;
        }
        return false;

    }

    /* c 要填充的字符
  *  length 填充后字符串的总长度
  *  content 要格式化的字符串
  *  格式化字符串，左对齐
  * */
    public static String flushLeft(char c, long length, String content){
        String str = "";
        long cl = 0;
        String cs = "";

        int chineseNum = 0;
        for (int i=0; i<content.length(); i++) {
            if (isChinese(content.charAt(i))) {
                chineseNum += 2;
            } else {
                chineseNum += 1;
            }
        }

        if (chineseNum > length){
            str = content;
        }else{
            for (int i = 0; i < length - chineseNum; i++){
                cs = cs + c;
            }
        }
        str = content + cs;
        return str;
    }

    private static SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

    private static Map<String, Integer> SPECIAL_KEYWORD_MAP = new HashMap<>();

    static {
        SPECIAL_KEYWORD_MAP.put("公交车", 1);
    }

    /**
     * 获取最后关键字信息
     *
     * @param keywordMap
     * @return
     */
    private static Map<String, List<String>> getKeywordResult(Map<String, String[][]> keywordMap) {
        Map<String, List<String>> keyListMap = new HashMap<>();

        if (keywordMap != null && !keywordMap.isEmpty()) {
            for (String key : keywordMap.keySet()) {
                Map<String, Integer> keywordResultMap = new HashMap<>();

                String[][] keywords = keywordMap.get(key);

                //特殊表格，“公交车”特殊处理
                if (SPECIAL_KEYWORD_MAP.get(key) != null) {
                    //将表格存储块状
                    List<String> wordList = getkeywordBlockData(key, keywords);
                    keyListMap.put(key, wordList);
                } else {
                    //获取整行关键字
                    for (int i = 0; i < keywords.length; i++) {
                        String value = getkeywordLineData(key, keywords[i]);
                        if (StringUtils.isNotBlank(value)) {
                            keywordResultMap.put(getkeywordLineData(key, keywords[i]), 1);
                        }
                    }

                    keyListMap.put(key, new ArrayList<String>(keywordResultMap.keySet()));
                }

            }
        }
        return keyListMap;
    }

    /**
     * 获取块状信息
     *
     * @param keywords
     * @return
     */
    private static List<String> getkeywordBlockData(String key, String[][] keywords) {
        int lastLineIndex = 3;
        List<String> segmentList = new ArrayList<String>();

        List<String> blockList = new ArrayList<String>();


        for (int i = 0; i < lastLineIndex; i++) {
            String[] words = keywords[i];

            int begin = 0;
            int end = 0;
            boolean hasSegent = false;
            for (int j = 0; j < words.length; j++) {
                if (StringUtils.isBlank(words[j])) {

                    if (!hasSegent) {
                        end = j - 1;
                    }

                    hasSegent = true;
                } else {
                    if (hasSegent) {
                        segmentList.add(begin + "," + end);

                        begin = j;
                        hasSegent = false;
                    }
                }
            }

            if (segmentList.size() > 0) {
                break;
            }
        }

        if (!segmentList.isEmpty()) {
            String firstSegment = segmentList.get(0);
            int begin_x = Integer.valueOf(firstSegment.split(",")[0]);


            for (int i = 0; i < segmentList.size(); i++) {

                String segement = segmentList.get(i);
                String[] segmentArray = segement.split(",");

                int index_y = Integer.valueOf(segmentArray[0]);

                int block_begin_x = begin_x;
                int block_begin_y = Integer.valueOf(segmentArray[0]);
                int block_end_x = -1;
                int block_end_y = Integer.valueOf(segmentArray[1]);

                int begin = begin_x;
                int end = 0;
                boolean hasSegent = false;
                for (int j = begin_x; j < keywords.length; j++) {
                    if (StringUtils.isBlank(keywords[j][index_y])) {

                        if (!hasSegent) {
                            end = j - 1;
                        }

                        hasSegent = true;
                    } else {
                        if (hasSegent) {

                            block_end_x = end;

                            blockList.add(block_begin_x + "," + block_begin_y + "," + block_end_x + "," + block_end_y);

                            begin = j;
                            hasSegent = false;

                            block_begin_x = begin;

                        }
                    }
                }
            }
        }

        List<String> wordList = new ArrayList<String>();

        if (!blockList.isEmpty()) {

            for (int index = 0; index < blockList.size(); index++) {
                String value = "";
                StringBuffer data = new StringBuffer();

                if (key.indexOf("sheet") == -1 && key.indexOf("Sheet") == -1) {
                    data.append(key);
                }

                String[] curBlockArray = blockList.get(index).split(",");
                int block_begin_x = Integer.valueOf(curBlockArray[0]);
                int block_begin_y = Integer.valueOf(curBlockArray[1]);
                int block_end_x = Integer.valueOf(curBlockArray[2]);
                int block_end_y = Integer.valueOf(curBlockArray[3]);

                for (int i = block_begin_x; i <= block_end_x; i++) {
                    for (int j = block_begin_y; j <= block_end_y; j++) {

                        if (StringUtils.isNotBlank(keywords[i][j])) {

                            data.append(flushLeft(' ', 26 , keywords[i][j]));

                        } else {

                            data.append(flushLeft(' ', 26 , ""));
                        }

                        /*if (keywords[i][j] != null && !value.equals(keywords[i][j])) {
                            value = keywords[i][j];
                            data.append("\t\t\t");

                            String.format("% 4d", number1)

                            data.append(keywords[i][j]);
                        } else {
                            data.append("\t\t\t");
                        }*/
                    }

                    data.append("\n");
                }

                wordList.add(data.toString());
            }

        }

        return wordList;
    }

    /**
     * 获取整行关键字
     *
     * @param words
     * @return
     */
    private static String getkeywordLineData(String key, String[] words) {
        StringBuffer data = new StringBuffer();

        if (key.indexOf("sheet") == -1 && key.indexOf("Sheet") == -1) {
            data.append(key);
        }

        String value = "";
        for (int j = 0; j < words.length; j++) {
            if (words[j] != null && !value.equals(words[j])) {
                value = words[j];
                if (value.length() > 20) {
                    data.append("\n");
                } else {
                    data.append("\t");
                }
                data.append(words[j]);
            } else {
                data.append("\t");
            }
        }
        return data.toString();
    }

    /**
     * 读取Excel测试，兼容 Excel 2003/2007/2010
     */
    public static Map<String, List<String>> readExcel(String path) {
        Map<String, List<String>> keyListMap = null;
        int cellCount = 0;
        try {
            File excelFile = new File(path); //创建文件对象
            FileInputStream is = new FileInputStream(excelFile); //文件流
            Workbook workbook = WorkbookFactory.create(is); //这种方式 Excel 2003/2007/2010 都是可以处理的
            int sheetCount = workbook.getNumberOfSheets();  //Sheet的数量

            Map<String, String[][]> keywordMap = new HashMap<>();

            //遍历每个Sheet
            for (int s = 0; s < sheetCount; s++) {
                Sheet sheet = workbook.getSheetAt(s);
                String sheetName = sheet.getSheetName();
                int rowCount = sheet.getPhysicalNumberOfRows(); //获取总行数
                Row firstRow = sheet.getRow(0);

                if (firstRow == null) {
                    continue;
                }

                cellCount = firstRow.getPhysicalNumberOfCells(); //获取总列数，以第一行的列为主

                String[][] resultData = new String[rowCount][cellCount];

                //遍历每一行
                for (int r = 0; r < rowCount; r++) {
                    Row row = sheet.getRow(r);

                    if (row == null) {
                        continue;
                    }

                    //遍历每一列
                    for (int c = 0; c < cellCount; c++) {
                        Cell cell = row.getCell(c);
                        String cellValue = "";

                        /*if (isMergedRegion(sheet, r, c)) {
                            cellValue = getMergedRegionValue(sheet, r, c , cell);
                        } else {
                            cellValue = getCellValue(cell);
                        }*/

                        cellValue = getMergedRegionValue(sheet, r, c, cell);

                        resultData[r][c] = cellValue;
                    }

                    /*if (keywordMap.get(sheetName) != null) {
                        keywordMap.put(sheetName + "_" + System.currentTimeMillis() / 1000, resultData);
                    } else {
                        keywordMap.put(sheetName, resultData);
                    }*/

                    keywordMap.put(sheetName, resultData);

                }
            }

            keyListMap = getKeywordResult(keywordMap);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return keyListMap;
    }

    /**
     * 判断指定的单元格是否是合并单元格
     *
     * @param sheet
     * @param row
     * @param column
     * @return
     */
    private static boolean isMergedRegion(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();

            if (row >= firstRow && row <= lastRow) {
                if (column >= firstColumn && column <= lastColumn) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取合并单元格的值
     *
     * @param sheet
     * @param row
     * @param column
     * @return
     */
    private static String getMergedRegionValue(Sheet sheet, int row, int column, Cell cell) {
        int sheetMergeCount = sheet.getNumMergedRegions();

        boolean isMergedRegion = false;

        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();

            if (row >= firstRow && row <= lastRow) {

                if (column >= firstColumn && column <= lastColumn) {
                    isMergedRegion = true;

                    Row fRow = sheet.getRow(firstRow);
                    Cell fCell = fRow.getCell(firstColumn);

                    return getCellValue(fCell);
                }
            }
        }

        if (!isMergedRegion) {
            return getCellValue(cell);
        }

        return "";
    }

    /**
     * 获取单元格的值
     *
     * @param cell
     * @return
     */
    private static String getCellValue(Cell cell) {
        String cellValue = "";
        if (cell != null) {
            int cellType = cell.getCellType();
            switch (cellType) {
                case Cell.CELL_TYPE_STRING: //文本
                    cellValue = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC: //数字、日期
                    if (DateUtil.isCellDateFormatted(cell)) {
                        cellValue = fmt.format(cell.getDateCellValue()); //日期型
                    } else {
                        DecimalFormat format = new DecimalFormat("#.00");
                        String value = format.format(Double.valueOf(cell.getNumericCellValue()));
                        if (value.substring(value.lastIndexOf(".") + 1, value.length()).equals("00")) {
                            cellValue = value.substring(0, value.lastIndexOf(".")); //数字
                        } else {
                            cellValue = value; //数字
                        }
                        if (cellValue.equals("")) {
                            cellValue = "0";
                        }
                    }
                    break;
                case Cell.CELL_TYPE_BOOLEAN: //布尔型
                    cellValue = String.valueOf(cell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_BLANK: //空白
                    cellValue = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_ERROR: //错误
                    cellValue = "错误";
                    break;
                case Cell.CELL_TYPE_FORMULA: //公式
                    cellValue = "错误";
                    break;
                default:
                    cellValue = "错误";
            }
        }
        return cellValue;
    }

}
