package com.school.cbis.service;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.school.cbis.commons.Wordbook;
import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.GradeDao;
import com.school.cbis.domain.tables.pojos.*;
import com.school.cbis.domain.tables.records.GradeRecord;
import com.school.cbis.domain.tables.records.TeachTaskInfoRecord;
import com.school.cbis.domain.tables.records.TeachTaskTitleRecord;
import com.school.cbis.exceptionhandle.TeachTaskExceptionHandle;
import com.school.cbis.util.FilesUtils;
import com.school.cbis.vo.eadmin.AssignmentBookAddVo;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by lenovo on 2016-05-30.
 */
@Service("fileService")
public class FileServiceImpl implements FileService {

    private final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);

    private final DSLContext create;

    @Resource
    private GradeService gradeService;

    @Resource
    private MailService mailService;

    @Resource
    private Wordbook wordbook;

    private Configuration configuration;

    private String baseUrl;

    private Users users;

    private  int wdFormatPDF = 17;

    private  int xlTypePDF = 0;

    private  int ppSaveAsPDF = 32;

    private  int msoTrue = -1;

    private  int msofalse = 0;

    @Autowired
    public FileServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.configuration = configuration;
    }

    /**
     * 读取excel
     *
     * @param realPath
     * @param ext
     * @param assignmentBookAddVo
     * @param teachTaskInfo
     */
    @Async
    @Override
    public void readFileForTeachTaskInfo(String realPath, String ext, AssignmentBookAddVo assignmentBookAddVo, TeachTaskInfo teachTaskInfo,Users users,String baseUrl) {
        this.baseUrl = baseUrl;
        this.users = users;
        create.transaction(outer -> {
            this.configuration = outer;
            final TeachTaskInfoRecord teachTaskInfoRecord =
                    DSL.using(outer)
                            .insertInto(Tables.TEACH_TASK_INFO)
                            .set(Tables.TEACH_TASK_INFO.TIE_ID, teachTaskInfo.getTieId())
                            .set(Tables.TEACH_TASK_INFO.TEACH_TASK_FILE_URL, teachTaskInfo.getTeachTaskFileUrl())
                            .set(Tables.TEACH_TASK_INFO.TEACH_TASK_FILE_SIZE, teachTaskInfo.getTeachTaskFileSize())
                            .set(Tables.TEACH_TASK_INFO.TEACH_TASK_FILE_DATE, teachTaskInfo.getTeachTaskFileDate())
                            .set(Tables.TEACH_TASK_INFO.TEACH_TASK_TERM, teachTaskInfo.getTeachTaskTerm())
                            .set(Tables.TEACH_TASK_INFO.TEACH_TYPE_ID, teachTaskInfo.getTeachTypeId())
                            .set(Tables.TEACH_TASK_INFO.TERM_START_TIME, teachTaskInfo.getTermStartTime())
                            .set(Tables.TEACH_TASK_INFO.TERM_END_TIME, teachTaskInfo.getTermEndTime())
                            .set(Tables.TEACH_TASK_INFO.FILE_USER, teachTaskInfo.getFileUser())
                            .set(Tables.TEACH_TASK_INFO.FILE_TYPE, teachTaskInfo.getFileType())
                            .set(Tables.TEACH_TASK_INFO.TEACH_TASK_TITLE, teachTaskInfo.getTeachTaskTitle())
                            .set(Tables.TEACH_TASK_INFO.YEAR_X, teachTaskInfo.getYearX())
                            .set(Tables.TEACH_TASK_INFO.YEAR_Y, teachTaskInfo.getYearY())
                            .set(Tables.TEACH_TASK_INFO.GRADE_X, teachTaskInfo.getGradeX())
                            .set(Tables.TEACH_TASK_INFO.GRADE_Y, teachTaskInfo.getGradeY())
                            .set(Tables.TEACH_TASK_INFO.GRADE_NUM_X, teachTaskInfo.getGradeNumX())
                            .set(Tables.TEACH_TASK_INFO.GRADE_NUM_Y, teachTaskInfo.getGradeNumY())
                            .returning()
                            .fetchOne();
            assignmentBookAddVo.setTaskInfoId(teachTaskInfoRecord.getId());
            if (StringUtils.hasLength(ext)) {
                if (ext.equals(Wordbook.EXCEL_XLSX)) {
                    XSSFWorkbook wb = FilesUtils.readXSSFFile(realPath);
                    log.debug("Data dump xlsx:");
                    List<String> years = resolveXSSFTitleYears(wb, assignmentBookAddVo);
                    saveXSSFTitleForTeachTaskInfo(wb, assignmentBookAddVo, years);
                } else if (ext.equals(Wordbook.EXCEL_XLS)) {
                    HSSFWorkbook wb = FilesUtils.readHSSFFile(realPath);
                    log.debug("Data dump xls:");
                    List<String> years = resolveHSSFTitleYears(wb, assignmentBookAddVo);
                    saveHSSFTitleForTeachTaskInfo(wb, assignmentBookAddVo, years);
                }
                String msg = "您上传的["+assignmentBookAddVo.getTeachTaskTitle()+"] 教学任务书通过了检验,您可以登录系统查看详细!";
                sendMailMsg(msg);
            }
        });

    }

    /**
     * 保存xls内容
     *
     * @param wb
     * @param assignmentBookAddVo
     * @param years               解析出的年级
     */
    public void saveHSSFTitleForTeachTaskInfo(HSSFWorkbook wb, AssignmentBookAddVo assignmentBookAddVo, List<String> years) throws TeachTaskExceptionHandle {
        try {
            HSSFSheet sheet = wb.getSheetAt(0);
            int rows = sheet.getPhysicalNumberOfRows();
            log.info("sheet 0 \"{}\" has {} rows(s)", wb.getSheetName(0), rows);
            List<Integer> taskTitleIds = new ArrayList<>();
            List<TeachTaskGradeCheck> teachTaskGradeChecks = new ArrayList<>();//存储班级信息
            Result<GradeRecord> gradeRecords = gradeService.findAllInYearWithCache(years);
            for (int r = 0; r < rows; r++) {
                HSSFRow row = sheet.getRow(r);
                if (row == null) {
                    continue;
                }

                int cells = row.getPhysicalNumberOfCells();
                log.info("\nRow {} has {} cell(s)", row.getRowNum(), cells);
                for (int c = 0; c < cells; c++) {
                    HSSFCell cell = row.getCell(c);
                    String value = getHSSFValue(cell);
                    dealData(taskTitleIds, teachTaskGradeChecks, value, r, c, assignmentBookAddVo, years, gradeRecords);
                    log.debug("CELL col={} VALUE={}", cell.getColumnIndex(), value);
                }
            }
        } catch (TeachTaskExceptionHandle teachTaskExceptionHandle) {
            throw new TeachTaskExceptionHandle(teachTaskExceptionHandle.getMessage());
        }

    }

    /**
     * 解析xls中的年级
     *
     * @param wb
     * @param assignmentBookAddVo
     * @return
     * @throws TeachTaskExceptionHandle
     */
    public List<String> resolveHSSFTitleYears(HSSFWorkbook wb, AssignmentBookAddVo assignmentBookAddVo) throws TeachTaskExceptionHandle {
        HSSFSheet sheet = wb.getSheetAt(0);
        int rows = sheet.getPhysicalNumberOfRows();
        log.info("sheet 0 \"{}\" has {} rows(s)", wb.getSheetName(0), rows);
        List<String> years = new ArrayList<>();
        for (int r = assignmentBookAddVo.getYearX(); r < rows; r++) {
            HSSFRow row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            HSSFCell cell = row.getCell(assignmentBookAddVo.getYearY());
            String value = getHSSFValue(cell);
            checkYear(years,value,assignmentBookAddVo,r);
            log.debug("CELL col={} VALUE={}", cell.getColumnIndex(), value);
        }
        return years;
    }

    /**
     * 获取xls中的值
     *
     * @param cell
     * @return
     */
    private String getHSSFValue(HSSFCell cell) {
        String value = "";

        switch (cell.getCellType()) {

            case HSSFCell.CELL_TYPE_FORMULA:
                value = cell.getCellFormula();
                break;

            case HSSFCell.CELL_TYPE_NUMERIC:
                value = cell.getNumericCellValue() + "";
                break;

            case HSSFCell.CELL_TYPE_STRING:
                value = cell.getStringCellValue();
                break;

            default:
        }
        return value;
    }

    /**
     * 保存xlsx数据
     *
     * @param wb
     * @param assignmentBookAddVo
     * @param years               解析出的年级
     */
    public void saveXSSFTitleForTeachTaskInfo(XSSFWorkbook wb, AssignmentBookAddVo assignmentBookAddVo, List<String> years) throws TeachTaskExceptionHandle {
        try {
            XSSFSheet sheet = wb.getSheetAt(0);
            int rows = sheet.getPhysicalNumberOfRows();
            log.info("sheet 0 \"{}\" has {} rows(s).", wb.getSheetName(0), rows);
            List<Integer> taskTitleIds = new ArrayList<>();
            List<TeachTaskGradeCheck> teachTaskGradeChecks = new ArrayList<>();//存储班级信息
            Result<GradeRecord> gradeRecords = gradeService.findAllInYearWithCache(years);
            for (int r = 0; r < rows; r++) {
                XSSFRow row = sheet.getRow(r);
                if (row == null) {
                    continue;
                }

                int cells = row.getPhysicalNumberOfCells();
                log.info("\nRow {} has {} cell(s).", row.getRowNum(), cells);
                for (int c = 0; c < cells; c++) {
                    XSSFCell cell = row.getCell(c);
                    String value = getXSSFValue(cell);
                    dealData(taskTitleIds, teachTaskGradeChecks, value, r, c, assignmentBookAddVo, years, gradeRecords);
                    log.debug("CELL col={} VALUE={}", cell.getColumnIndex(), value);
                }
            }
        } catch (TeachTaskExceptionHandle teachTaskExceptionHandle) {
            throw new TeachTaskExceptionHandle(teachTaskExceptionHandle.getMessage());
        }

    }

    /**
     * 处理数据
     *
     * @param taskTitleIds
     * @param teachTaskGradeChecks
     * @param value
     * @param r
     * @param c
     * @param assignmentBookAddVo
     * @param years
     * @param gradeRecords
     * @throws TeachTaskExceptionHandle
     */
    private void dealData(List<Integer> taskTitleIds, final List<TeachTaskGradeCheck> teachTaskGradeChecks, String value, int r, int c,
                          AssignmentBookAddVo assignmentBookAddVo, List<String> years, Result<GradeRecord> gradeRecords) throws TeachTaskExceptionHandle {
        if (r == 1) {//标题
            saveTeachTaskTitle(taskTitleIds, value, r, c, assignmentBookAddVo);
        } else if (r > 1) {
            //在班级人数在同一列
            if (assignmentBookAddVo.isAlikeGradeOfNum()) {
                if (c == assignmentBookAddVo.getGradeY()) {
                    checkGradeInRow(r, assignmentBookAddVo, value, years, gradeRecords);
                }
            } else {
                if (c == assignmentBookAddVo.getGradeY()) {
                    teachTaskGradeChecks.clear();
                    teachTaskGradeChecks.addAll(checkGrade(r, assignmentBookAddVo, value, years, gradeRecords));
                }
                if (c == assignmentBookAddVo.getGradeNumY()) {
                    DSL.using(configuration)
                            .transaction(nested -> {
                                String[] gradeNum = dealString(value).split("[,]");
                                for (int i = 0; i < teachTaskGradeChecks.size(); i++) {
                                    if (i < gradeNum.length) {
                                        if (NumberUtils.isNumber(gradeNum[i])) {
                                            teachTaskGradeChecks.get(i).setGradeNum(Integer.parseInt(gradeNum[i]));
                                        } else {
                                            String msg = "您上传的教学任务书:["+assignmentBookAddVo.getTeachTaskTitle()+"] 存在以下问题:\n"+
                                                    "第" + (r + 1) + "行,解析出的人数不是数字!解析出的字符串为:[" + gradeNum[i] + "] 系统已为您回溯操作,请您重新添加教学任务书!";
                                            sendMailMsg(msg);
                                            throw new TeachTaskExceptionHandle("第" + (r + 1) + "行,解析出的人数不是数字!解析出的字符串为:[" + gradeNum[i] + "]");
                                        }
                                        DSL.using(nested)
                                                .insertInto(Tables.TEACH_TASK_GRADE_CHECK)
                                                .set(Tables.TEACH_TASK_GRADE_CHECK.ROW_X, teachTaskGradeChecks.get(i).getRowX())
                                                .set(Tables.TEACH_TASK_GRADE_CHECK.TEACH_TASK_INFO_ID, teachTaskGradeChecks.get(i).getTeachTaskInfoId())
                                                .set(Tables.TEACH_TASK_GRADE_CHECK.GRADE, teachTaskGradeChecks.get(i).getGrade())
                                                .set(Tables.TEACH_TASK_GRADE_CHECK.GRADE_NUM, teachTaskGradeChecks.get(i).getGradeNum())
                                                .set(Tables.TEACH_TASK_GRADE_CHECK.CHECK_IS_RIGHT, teachTaskGradeChecks.get(i).getCheckIsRight())
                                                .set(Tables.TEACH_TASK_GRADE_CHECK.DATABASE_GRADE_ID, teachTaskGradeChecks.get(i).getDatabaseGradeId())
                                                .execute();
                                    }
                                }
                            });
                }
            }
            saveTeachTaskContent(taskTitleIds, value, r, c);
        }
    }

    /**
     * 解析xlsx中的年级
     *
     * @param wb
     * @param assignmentBookAddVo
     * @return
     * @throws TeachTaskExceptionHandle
     */
    public List<String> resolveXSSFTitleYears(XSSFWorkbook wb, AssignmentBookAddVo assignmentBookAddVo) throws TeachTaskExceptionHandle {
        XSSFSheet sheet = wb.getSheetAt(0);
        int rows = sheet.getPhysicalNumberOfRows();
        log.info("sheet 0 \"{}\" has {} rows(s).", wb.getSheetName(0), rows);
        List<String> years = new ArrayList<>();
        for (int r = assignmentBookAddVo.getYearX(); r < rows; r++) {
            XSSFRow row = sheet.getRow(r);
            if (row == null) {
                continue;
            }

            XSSFCell cell = row.getCell(assignmentBookAddVo.getYearY());
            String value = StringUtils.trimWhitespace(getXSSFValue(cell));
            checkYear(years,value,assignmentBookAddVo,r);
            log.debug("CELL col={} VALUE={}", cell.getColumnIndex(), value);
        }
        return years;
    }

    private void checkYear(List<String> years,String value,AssignmentBookAddVo assignmentBookAddVo,int r) throws TeachTaskExceptionHandle {
        if (NumberUtils.isNumber(value)) {
            if (!years.contains(value)) {
                years.add(value);
            }
        } else {
            String msg = "您上传的教学任务书:["+assignmentBookAddVo.getTeachTaskTitle()+"] 存在以下问题:\n"+
                    "第" + (r + 1) + "行,解析出的年级不是数字!  系统已为您回溯操作,请您重新添加教学任务书!";
            sendMailMsg(msg);
            throw new TeachTaskExceptionHandle("第" + (r + 1) + "行,解析出的年级不是数字!");
        }
    }

    /**
     * 获取xlsx中的值
     *
     * @param cell
     * @return
     */
    private String getXSSFValue(XSSFCell cell) {
        String value = "";

        switch (cell.getCellType()) {

            case XSSFCell.CELL_TYPE_FORMULA:
                value = cell.getCellFormula();
                break;

            case XSSFCell.CELL_TYPE_NUMERIC:
                value = cell.getNumericCellValue() + "";
                break;

            case XSSFCell.CELL_TYPE_STRING:
                value = cell.getStringCellValue();
                break;

            default:
        }
        return value;
    }

    /**
     * 保存标题
     *
     * @param taskTitleIds
     * @param value
     * @param row
     * @param cell
     * @param assignmentBookAddVo
     */
    private void saveTeachTaskTitle(List<Integer> taskTitleIds, String value, int row, int cell, AssignmentBookAddVo assignmentBookAddVo) {
        DSL.using(configuration)
                .transaction(nested -> {
                    final TeachTaskTitleRecord teachTaskTitleRecord = DSL.using(nested)
                            .insertInto(Tables.TEACH_TASK_TITLE)
                            .set(Tables.TEACH_TASK_TITLE.TITLE, value)
                            .set(Tables.TEACH_TASK_TITLE.TITLE_X, row)
                            .set(Tables.TEACH_TASK_TITLE.TITLE_Y, cell)
                            .set(Tables.TEACH_TASK_TITLE.TEACH_TASK_INFO_ID, assignmentBookAddVo.getTaskInfoId())
                            .returning()
                            .fetchOne();

                    taskTitleIds.add(teachTaskTitleRecord.getId());
                });
    }

    /**
     * 保存内容
     *
     * @param taskTitleIds
     * @param value
     * @param row
     * @param cell
     */
    private void saveTeachTaskContent(List<Integer> taskTitleIds, String value, int row, int cell) {
        DSL.using(configuration)
                .transaction(nested -> {
                    DSL.using(nested)
                            .insertInto(Tables.TEACH_TASK_CONTENT)
                            .set(Tables.TEACH_TASK_CONTENT.CONTENT, value)
                            .set(Tables.TEACH_TASK_CONTENT.CONTENT_X, row)
                            .set(Tables.TEACH_TASK_CONTENT.CONTENT_Y, cell)
                            .set(Tables.TEACH_TASK_CONTENT.TEACH_TASK_TITLE_ID, taskTitleIds.get(cell))
                            .execute();
                });
    }

    /**
     * 处理中文
     *
     * @param str
     * @return
     */
    private String dealString(String str) {
        str = str.replaceAll("，", ",");
        str = str.replaceAll("）", ")");
        str = str.replaceAll("（", "(");
        return str;
    }

    /**
     * 班级与人数在同一列时的检验
     *
     * @param row
     * @param assignmentBookAddVo
     * @param value
     * @param years
     * @param gradeRecords
     * @throws TeachTaskExceptionHandle
     */
    private void checkGradeInRow(int row, AssignmentBookAddVo assignmentBookAddVo, String value, List<String> years, Result<GradeRecord> gradeRecords) throws TeachTaskExceptionHandle {
        DSL.using(configuration)
                .transaction(nested -> {
                    String[] grades = dealString(value).split("[,]");
                    for (String s : grades) {
                        String grade = StringUtils.trimWhitespace(s);
                        TeachTaskGradeCheck teachTaskGradeCheck = new TeachTaskGradeCheck();
                        teachTaskGradeCheck.setRowX(row);
                        teachTaskGradeCheck.setTeachTaskInfoId(assignmentBookAddVo.getTaskInfoId());
                        String temp = grade.substring(0, grade.lastIndexOf("("));
                        teachTaskGradeCheck.setGrade(temp);

                        Byte isRight = 0;
                        int gradeId = 0;
                        for (GradeRecord g : gradeRecords) {
                            if (g.getGradeName().equals(grade)) {
                                isRight = 1;
                                gradeId = g.getId();
                                break;
                            }
                        }
                        teachTaskGradeCheck.setCheckIsRight(isRight);
                        teachTaskGradeCheck.setDatabaseGradeId(gradeId);

                        String gradeNum = grade.substring(grade.lastIndexOf("(") + 1, grade.length() - 1);
                        if (NumberUtils.isNumber(gradeNum)) {
                            teachTaskGradeCheck.setGradeNum(Integer.parseInt(gradeNum));
                        } else {
                            String msg = "您上传的教学任务书:["+assignmentBookAddVo.getTeachTaskTitle()+"] 存在以下问题:\n"+
                                    "第" + (row + 1) + "行,解析出的班级人数不是数字!解析出的字符串为:[" + gradeNum + "] 系统已为您回溯操作,请您重新添加教学任务书!";
                            sendMailMsg(msg);
                            throw new TeachTaskExceptionHandle("第" + (row + 1) + "行,解析出的人数不是数字!解析出的字符串为:[" + gradeNum + "]");
                        }

                        DSL.using(nested)
                                .insertInto(Tables.TEACH_TASK_GRADE_CHECK)
                                .set(Tables.TEACH_TASK_GRADE_CHECK.ROW_X, teachTaskGradeCheck.getRowX())
                                .set(Tables.TEACH_TASK_GRADE_CHECK.TEACH_TASK_INFO_ID, teachTaskGradeCheck.getTeachTaskInfoId())
                                .set(Tables.TEACH_TASK_GRADE_CHECK.GRADE, teachTaskGradeCheck.getGrade())
                                .set(Tables.TEACH_TASK_GRADE_CHECK.GRADE_NUM, teachTaskGradeCheck.getGradeNum())
                                .set(Tables.TEACH_TASK_GRADE_CHECK.CHECK_IS_RIGHT, teachTaskGradeCheck.getCheckIsRight())
                                .set(Tables.TEACH_TASK_GRADE_CHECK.DATABASE_GRADE_ID, teachTaskGradeCheck.getDatabaseGradeId())
                                .execute();
                    }

                });
    }

    /**
     * 班级与人数不在同一列时的检验班级
     * @param row
     * @param assignmentBookAddVo
     * @param value
     * @param years
     * @param gradeRecords
     * @return
     */
    private List<TeachTaskGradeCheck> checkGrade(int row, AssignmentBookAddVo assignmentBookAddVo, String value, List<String> years, Result<GradeRecord> gradeRecords) {
        List<TeachTaskGradeCheck> teachTaskGradeChecks = new ArrayList<>();
        String[] grades = dealString(value).split("[,]");
        for (String s : grades) {
            String grade = StringUtils.trimWhitespace(s);
            TeachTaskGradeCheck teachTaskGradeCheck = new TeachTaskGradeCheck();
            teachTaskGradeCheck.setRowX(row);
            teachTaskGradeCheck.setTeachTaskInfoId(assignmentBookAddVo.getTaskInfoId());
            teachTaskGradeCheck.setGrade(grade);

            Byte isRight = 0;
            int gradeId = 0;
            for (GradeRecord g : gradeRecords) {
                if (g.getGradeName().equals(grade)) {
                    isRight = 1;
                    gradeId = g.getId();
                    break;
                }
            }
            teachTaskGradeCheck.setCheckIsRight(isRight);
            teachTaskGradeCheck.setDatabaseGradeId(gradeId);
            teachTaskGradeChecks.add(teachTaskGradeCheck);
        }
        return teachTaskGradeChecks;
    }

    /**
     * 发送消息邮件
     * @param msg
     */
    public void sendMailMsg(String msg){
        if(wordbook.mailSwitch){
            if(users.getIsCheckEmail() == 1){
                mailService.sendTeachTaskMsg(users,baseUrl,msg);
            }
        }
    }

    @Override
    public boolean convert2PDF(String inputFile, String pdfFile) {
        String suffix = getFileSufix(inputFile);
        File file = new File(inputFile);
        if (!file.exists()) {
            log.debug("文件 {} 不存在!",inputFile);
            return false;
        }
        if (suffix.equals("pdf")) {
            log.info("PDF not need to convert!");
            return false;
        }
        if (suffix.equals("doc") || suffix.equals("docx")
                || suffix.equals("txt")) {
            return word2PDF(inputFile, pdfFile);
        } else if (suffix.equals("ppt") || suffix.equals("pptx")) {
            return ppt2PDF(inputFile, pdfFile);
        } else if (suffix.equals("xls") || suffix.equals("xlsx")) {
            return excel2PDF(inputFile, pdfFile);
        } else {
            log.debug("文件 {} 格式不支持转换!",inputFile);
            return false;
        }
    }

    private boolean excel2PDF(String inputFile, String pdfFile) {
        try {
            ActiveXComponent app = new ActiveXComponent("Excel.Application");
            app.setProperty("Visible", false);
            Dispatch excels = app.getProperty("Workbooks").toDispatch();
            Dispatch excel = Dispatch.call(excels, "Open", inputFile, false,
                    true).toDispatch();
            Dispatch.call(excel, "ExportAsFixedFormat", xlTypePDF, pdfFile);
            Dispatch.call(excel, "Close", false);
            app.invoke("Quit");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean ppt2PDF(String inputFile, String pdfFile) {
        try {
            ActiveXComponent app = new ActiveXComponent(
                    "PowerPoint.Application");
            // app.setProperty("Visible", msofalse);
            Dispatch ppts = app.getProperty("Presentations").toDispatch();

            Dispatch ppt = Dispatch.call(ppts, "Open", inputFile, true,// ReadOnly
                    true,// Untitled指定文件是否有标题
                    false// WithWindow指定文件是否可见
            ).toDispatch();

            Dispatch.call(ppt, "SaveAs", pdfFile, ppSaveAsPDF);

            Dispatch.call(ppt, "Close");

            app.invoke("Quit");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getFileSufix(String fileName) {
        int splitIndex = fileName.lastIndexOf(".");
        return fileName.substring(splitIndex + 1);
    }

    public boolean word2PDF(String inputFile, String pdfFile) {
        try {
            ActiveXComponent app = new ActiveXComponent("Word.Application");
            app.setProperty("Visible", false);
            Dispatch docs = app.getProperty("Documents").toDispatch();
            Dispatch doc = Dispatch.call(docs, "Open", inputFile, false, true)
                    .toDispatch();
            Dispatch.call(doc, "ExportAsFixedFormat", pdfFile, wdFormatPDF);
            Dispatch.call(doc, "Close", false);

            app.invoke("Quit", 0);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
