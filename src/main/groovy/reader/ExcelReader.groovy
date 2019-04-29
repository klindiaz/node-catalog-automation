package reader

import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.DateUtil
import org.apache.poi.xssf.usermodel.XSSFCell
import org.apache.poi.xssf.usermodel.XSSFRow
import org.apache.poi.xssf.usermodel.XSSFWorkbook

class ExcelReader {
    def fileName
    def workbook
    def labels
    def row

    ExcelReader(String fileName) {
        this.fileName = fileName
        XSSFRow.metaClass.getAt = { int idx ->
            XSSFCell cell = delegate.getCell(idx)
            if(! cell) {
                return null
            }
            def value
            switch(cell.getCellTypeEnum()) {
                case CellType.NUMERIC:
                    if(DateUtil.isCellDateFormatted(cell)) {
                        value = cell.dateCellValue
                    } else {
                        value = cell.numericCellValue
                    }
                    break
                case CellType.BOOLEAN:
                    value = cell.booleanCellValue
                    break
                case CellType.FORMULA:
                    switch (cell.getCachedFormulaResultTypeEnum()) {
                        case CellType.NUMERIC:
                            if(DateUtil.isCellDateFormatted(cell)) {
                                value = cell.dateCellValue
                            } else {
                                value = cell.numericCellValue
                            }
                            break
                        case CellType.BOOLEAN:
                            value = cell.booleanCellValue
                            break
                        default:
                            value = cell.stringCellValue
                    }
                    break
                default:
                    value = cell.stringCellValue
                    break
            }
            return value
        }

        new File(fileName).withInputStream{ is->
            workbook = new XSSFWorkbook(is)
        }
    }

    def getSheet(idx) {
        def sheet
        if(! idx) idx = 0
        if(idx instanceof Number) {
            sheet = workbook.getSheetAt(idx)
        } else if(idx ==~ /^\d+$/) {
            sheet = workbook.getSheetAt(Integer.valueOf(idx))
        } else {
            sheet = workbook.getSheet(idx)
        }
        return sheet
    }

    def cell(idx) {
        if(labels && (idx instanceof String)) {
            idx = labels.indexOf(idx.toLowerCase())
        }
        return row[idx]
    }

    def propertyMissing(String name) {
        cell(name)
    }

    def eachLine(Map params = [:] , Closure closure) {
        try {
            def offset = params.offset ?: 0
            def max = params.max ?: 9_999_999
            def sheet = getSheet(params.sheet)
            def rowIterator = sheet.rowIterator()
            def linesRead = 0

            offset.times{ rowIterator.next() }

            if(params.labels) {
                labels = rowIterator.next().collect{it.toString().trim().toLowerCase().replaceAll(" ","_")}
            }

            closure.setDelegate(this)

            while(rowIterator.hasNext() && linesRead++ < max) {
                row = rowIterator.next()
                closure.call(row)
            }
        } catch (Exception e) {
            System.out.println("FILE HAS ISSUE: ${this.fileName}")
            e.printStackTrace()
        }
    }
}
