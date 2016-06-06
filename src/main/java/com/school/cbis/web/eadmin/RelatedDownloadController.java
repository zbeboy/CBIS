package com.school.cbis.web.eadmin;

import com.school.cbis.commons.Wordbook;
import com.school.cbis.data.AjaxData;
import com.school.cbis.data.PaginationData;
import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.pojos.ClassroomCourseTimetableInfo;
import com.school.cbis.domain.tables.pojos.RelatedDownload;
import com.school.cbis.domain.tables.records.ClassroomCourseTimetableInfoRecord;
import com.school.cbis.service.*;
import com.school.cbis.util.FilesUtils;
import com.school.cbis.vo.eadmin.AddClassroomTimetableVo;
import com.school.cbis.vo.eadmin.AddRelatedDownloadVo;
import com.school.cbis.vo.eadmin.ClassroomTimetableListVo;
import com.school.cbis.vo.eadmin.RelatedDownloadListVo;
import org.jooq.Record;
import org.jooq.Record10;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by lenovo on 2016-06-06.
 */
@Controller
public class RelatedDownloadController {

    private final Logger log = LoggerFactory.getLogger(RelatedDownloadController.class);

    @Resource
    private RelatedDownloadService relatedDownloadService;

    @Resource
    private UsersService usersService;

    @Resource
    private GradeService gradeService;

    @Resource
    private FileService fileService;

    @Resource
    private Wordbook wordbook;

    /**
     * 列表页
     *
     * @param relatedDownloadListVo
     * @param modelMap
     * @return
     */
    @RequestMapping("/administrator/eadmin/relatedDownloadList")
    public String relatedDownloadList(RelatedDownloadListVo relatedDownloadListVo, ModelMap modelMap) {
        modelMap.addAttribute("relatedDownloadListVo", relatedDownloadListVo);
        return "/administrator/eadmin/relateddownloadlist";
    }

    /**
     * 列表数据
     *
     * @param relatedDownloadListVo
     * @return
     */
    @RequestMapping("/administrator/eadmin/relatedDownloadData")
    @ResponseBody
    public AjaxData<RelatedDownloadListVo> relatedDownloadData(RelatedDownloadListVo relatedDownloadListVo) {
        AjaxData<RelatedDownloadListVo> ajaxData = new AjaxData<>();
        Record record = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (!ObjectUtils.isEmpty(record)) {
            tieId = record.getValue(Tables.TIE.ID);
        }
        if (tieId > 0) {
            Result<Record10<Integer, String, String, String, Timestamp, Integer, Integer, String, String, String>> records = relatedDownloadService.findByTieIdAndPage(relatedDownloadListVo, tieId);
            if (records.isNotEmpty()) {
                List<RelatedDownloadListVo> list = records.into(RelatedDownloadListVo.class);
                list.forEach(s -> {
                    s.setFileSize(FilesUtils.sizeToString(Long.parseLong(s.getFileSize())));
                });
                PaginationData paginationData = new PaginationData();
                paginationData.setPageNum(relatedDownloadListVo.getPageNum());
                paginationData.setPageSize(relatedDownloadListVo.getPageSize());
                paginationData.setTotalDatas(relatedDownloadService.findByTieIdAndPageCount(relatedDownloadListVo, tieId));
                ajaxData.success().listData(list).paginationData(paginationData);
            } else {
                ajaxData.fail();
            }
        } else {
            ajaxData.fail();
        }
        return ajaxData;
    }

    @RequestMapping("/administrator/eadmin/relatedDownloadAdd")
    public String relatedDownloadAdd() {
        return "/administrator/eadmin/relateddownloadadd";
    }

    @RequestMapping("/administrator/eadmin/addRelatedDownload")
    @ResponseBody
    public AjaxData addRelatedDownload(AddRelatedDownloadVo addRelatedDownloadVo, HttpServletRequest request) {
        AjaxData ajaxData = new AjaxData();
        String inputFile = request.getSession().getServletContext().getRealPath("/") + addRelatedDownloadVo.getFilePath();
        File file = new File(inputFile);

        Record record = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (!ObjectUtils.isEmpty(record)) {
            tieId = record.getValue(Tables.TIE.ID);
        }
        if (tieId > 0) {
            RelatedDownload relatedDownload = new RelatedDownload();
            relatedDownload.setTieId(tieId);
            relatedDownload.setFileUrl(addRelatedDownloadVo.getFilePath());
            relatedDownload.setFileSize(file.length() + "");
            relatedDownload.setFileName(addRelatedDownloadVo.getFileName());
            relatedDownload.setFileDate(new Timestamp(System.currentTimeMillis()));
            relatedDownload.setFileDownTimes(0);
            relatedDownload.setTeachTypeId(wordbook.getTeachTypeMap().get(StringUtils.trimWhitespace(addRelatedDownloadVo.getTeachType())));
            relatedDownload.setFileUser(usersService.getUserName());
            relatedDownload.setRemark(addRelatedDownloadVo.getRemark());
            relatedDownload.setFileType(addRelatedDownloadVo.getFilePath().substring(addRelatedDownloadVo.getFilePath().lastIndexOf(".") + 1));
            relatedDownloadService.save(relatedDownload);
            ajaxData.success().msg("保存信息成功!");
        } else {
            ajaxData.fail().msg("获取用户信息失败!");
        }
        return ajaxData;
    }

    /**
     * 更新界面
     *
     * @param id
     * @param modelMap
     * @return
     */
    @RequestMapping("/administrator/eadmin/relatedDownloadUpdate")
    public String relatedDownloadUpdate(@RequestParam("id") int id, ModelMap modelMap) {
        Record record = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (!ObjectUtils.isEmpty(record)) {
            tieId = record.getValue(Tables.TIE.ID);
        }
        if (tieId > 0) {
            RelatedDownload relatedDownload = relatedDownloadService.findById(id);
            if (!ObjectUtils.isEmpty(relatedDownload)) {
                modelMap.addAttribute("relatedDownload", relatedDownload);
                return "/administrator/eadmin/relateddownloadupdate";
            }
        }
        return "redirect:/administrator/eadmin/relatedDownloadList";
    }

    /**
     * 更新
     *
     * @param addRelatedDownloadVo
     * @param request
     * @return
     */
    @RequestMapping("/administrator/eadmin/updateRelatedDownload")
    @ResponseBody
    public AjaxData updateRelatedDownload(AddRelatedDownloadVo addRelatedDownloadVo, HttpServletRequest request) {
        AjaxData ajaxData = new AjaxData();
        RelatedDownload relatedDownload = relatedDownloadService.findById(addRelatedDownloadVo.getId());
        relatedDownload.setFileName(addRelatedDownloadVo.getFileName());
        relatedDownload.setRemark(addRelatedDownloadVo.getRemark());

        if (!relatedDownload.getFileUrl().equals(StringUtils.trimWhitespace(addRelatedDownloadVo.getFilePath()))) {
            String inputFile = request.getSession().getServletContext().getRealPath("/") + addRelatedDownloadVo.getFilePath();
            File file = new File(inputFile);
            relatedDownload.setFileUrl(addRelatedDownloadVo.getFilePath());
            relatedDownload.setFileSize(file.length() + "");
            relatedDownload.setFileType(addRelatedDownloadVo.getFilePath().substring(addRelatedDownloadVo.getFilePath().lastIndexOf(".") + 1));
        }
        relatedDownloadService.update(relatedDownload);
        ajaxData.success().msg("保存信息成功!");
        return ajaxData;
    }

    /**
     * 删除
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("/administrator/eadmin/deleteRelatedDownload")
    @ResponseBody
    public AjaxData deleteRelatedDownload(@RequestParam("id") int id, HttpServletRequest request){
        try {
            RelatedDownload relatedDownload = relatedDownloadService.findById(id);
            if (!ObjectUtils.isEmpty(relatedDownload)) {
                String webPath = request.getSession().getServletContext().getRealPath("/");
                FilesUtils.deleteFile(webPath + relatedDownload.getFileUrl());
                relatedDownloadService.deleteById(id);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new AjaxData().success().msg("删除成功!");
    }
}
