package com.school.cbis.web.eadmin;

import com.school.cbis.data.AjaxData;
import com.school.cbis.data.PaginationData;
import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.pojos.*;
import com.school.cbis.domain.tables.records.FourItemsRecord;
import com.school.cbis.domain.tables.records.TeachTaskContentRecord;
import com.school.cbis.domain.tables.records.TeachTaskGradeCheckRecord;
import com.school.cbis.service.*;
import com.school.cbis.util.FilesUtils;
import com.school.cbis.vo.eadmin.AddFourItemsLineVo;
import com.school.cbis.vo.eadmin.AssignmentBookListVo;
import com.school.cbis.vo.eadmin.FourItemsLineVo;
import org.jooq.*;
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
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2016-06-07.
 */
@Controller
public class FourItemsController {

    private final Logger log = LoggerFactory.getLogger(FourItemsController.class);

    @Resource
    private TeachTaskInfoService teachTaskInfoService;

    @Resource
    private TeachTaskTitleService teachTaskTitleService;

    @Resource
    private TeachTaskContentService teachTaskContentService;

    @Resource
    private FourItemsService fourItemsService;

    @Resource
    private UploadService uploadService;

    @Resource
    private UsersService usersService;

    /**
     * 选择教学任务书
     *
     * @param assignmentBookListVo
     * @param modelMap
     * @return
     */
    @RequestMapping("/administrator/eadmin/fourItemsList")
    public String fourItemsList(AssignmentBookListVo assignmentBookListVo, ModelMap modelMap) {
        if (!StringUtils.hasLength(assignmentBookListVo.getTeachTaskTerm())) {
            assignmentBookListVo.setTeachTaskTerm("");
        }
        modelMap.addAttribute("assignmentBookListVo", assignmentBookListVo);
        return "/administrator/eadmin/fouritemslist";
    }

    /**
     * 列出教学任务书行
     *
     * @param fourItemsLineVo
     * @param modelMap
     * @return
     */
    @RequestMapping("/administrator/eadmin/fourItemsLine")
    public String fourItemsLine(FourItemsLineVo fourItemsLineVo, ModelMap modelMap) {
        modelMap.addAttribute("fourItemsLineVo", fourItemsLineVo);
        return "/administrator/eadmin/fouritemsline";
    }

    /**
     * 数据
     *
     * @param fourItemsLineVo
     * @return
     */
    @RequestMapping("/administrator/eadmin/fourItemsLineData")
    @ResponseBody
    public AjaxData fourItemsLineData(FourItemsLineVo fourItemsLineVo) {
        AjaxData ajaxData = new AjaxData<>();
        //先distinct content x
        Result<Record1<Integer>> distinctContentX = teachTaskContentService.findByTeachTaskInfoIdAndDistinctContentXAndPage(fourItemsLineVo);
        //查询出对应 内容
        if (distinctContentX.isNotEmpty()) {
            List<Integer> contentX = new ArrayList<>();
            for (Record r : distinctContentX) {
                contentX.add(r.getValue(Tables.TEACH_TASK_CONTENT.CONTENT_X));
            }
            Result<Record7<Integer, String, String, Integer, Integer, String, Integer>> teachTaskContents = teachTaskContentService.findByTeachTaskInfoIdWithTeachTaskTitle(fourItemsLineVo.getTaskInfoId());
            if (teachTaskContents.isNotEmpty()) {
                List<FourItemsLineVo> list = teachTaskContents.into(FourItemsLineVo.class);
                Result<FourItemsRecord> fourItemsRecords = fourItemsService.findByTeachTaskInfoIdInContentX(fourItemsLineVo.getTaskInfoId(), contentX);
                List<FourItems> fourItemses = fourItemsRecords.into(FourItems.class);
                Map<String, Object> map = new HashMap<>();
                map.put("result", list);
                map.put("contentX", contentX);
                map.put("fourItems", fourItemses);
                PaginationData paginationData = new PaginationData();
                paginationData.setPageNum(fourItemsLineVo.getPageNum());
                paginationData.setPageSize(fourItemsLineVo.getPageSize());
                paginationData.setTotalDatas(teachTaskContentService.findByTeachTaskInfoIdAndDistinctContentXAndPageCount(fourItemsLineVo));
                ajaxData.success().mapData(map).paginationData(paginationData);
            } else {
                ajaxData.fail().msg("teachTaskContents");
            }
        } else {
            ajaxData.fail().msg("无内容!");
        }

        return ajaxData;
    }

    /**
     * 添加四大件
     * @param addFourItemsLineVo
     * @param request
     * @return
     */
    @RequestMapping("/administrator/eadmin/addFourItemsLine")
    @ResponseBody
    public AjaxData addFourItemsLine(AddFourItemsLineVo addFourItemsLineVo, HttpServletRequest request) {
        FourItems fourItems = new FourItems();
        fourItems.setTeachTaskInfoId(addFourItemsLineVo.getTeachTaskInfoId());
        fourItems.setContentX(addFourItemsLineVo.getFourItemContentX());
        fourItems.setFourItemsFileUrl(addFourItemsLineVo.getFilePath());
        String inputFile = request.getSession().getServletContext().getRealPath("/") + addFourItemsLineVo.getFilePath();
        File file = new File(inputFile);
        fourItems.setFourItemsFileSize(file.length() + "");
        fourItems.setFourItemsFileName(addFourItemsLineVo.getUploadFile().substring(0, addFourItemsLineVo.getUploadFile().lastIndexOf(".")));
        fourItems.setFourItemsFileDate(new Timestamp(System.currentTimeMillis()));
        fourItems.setFileUser(usersService.getUserName());
        fourItems.setFileType(addFourItemsLineVo.getUploadFile().substring(addFourItemsLineVo.getUploadFile().lastIndexOf(".") + 1));
        fourItemsService.save(fourItems);
        return new AjaxData().success().msg("保存成功!");
    }

    /**
     * 下载四大件
     * @param id
     * @param response
     * @param request
     */
    @RequestMapping("/administrator/eadmin/downloadFourItemsLine")
    public void downloadFourItemsLine(@RequestParam("id") int id, HttpServletResponse response, HttpServletRequest request) {
        FourItems fourItems = fourItemsService.findById(id);
        if (!ObjectUtils.isEmpty(fourItems)) {
            uploadService.download(fourItems.getFourItemsFileName(), fourItems.getFourItemsFileUrl(), response, request);
        }
    }

    /**
     * 删除四大件
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("/administrator/eadmin/deleteFourItemsLine")
    @ResponseBody
    public AjaxData deleteFourItemsLine(@RequestParam("id") int id, HttpServletRequest request) {
        try {
            FourItems fourItems = fourItemsService.findById(id);
            if (!ObjectUtils.isEmpty(fourItems)) {
                String webPath = request.getSession().getServletContext().getRealPath("/");
                boolean s = FilesUtils.deleteFile(webPath + fourItems.getFourItemsFileUrl());
                log.debug(webPath + fourItems.getFourItemsFileUrl());
                System.out.println(s);
                fourItemsService.deleteById(id);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new AjaxData().success().msg("删除成功!");
    }

    /**
     * 更新四大件
     * @param addFourItemsLineVo
     * @param request
     * @return
     */
    @RequestMapping("/administrator/eadmin/updateFourItemsLine")
    @ResponseBody
    public AjaxData updateFourItemsLine(AddFourItemsLineVo addFourItemsLineVo, HttpServletRequest request){
        FourItems fourItems = fourItemsService.findById(addFourItemsLineVo.getFourItemId());
        if(!ObjectUtils.isEmpty(fourItems)){
            fourItems.setFourItemsFileUrl(addFourItemsLineVo.getFilePath());
            String inputFile = request.getSession().getServletContext().getRealPath("/") + addFourItemsLineVo.getFilePath();
            File file = new File(inputFile);
            fourItems.setFourItemsFileSize(file.length() + "");
            fourItems.setFourItemsFileName(addFourItemsLineVo.getUploadFile().substring(0, addFourItemsLineVo.getUploadFile().lastIndexOf(".")));
            fourItems.setFileType(addFourItemsLineVo.getUploadFile().substring(addFourItemsLineVo.getUploadFile().lastIndexOf(".") + 1));
            fourItemsService.update(fourItems);
        }
        return new AjaxData().success().msg("更新成功!");
    }

}
