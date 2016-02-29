package com.school.cbis.web.tie;

import com.school.cbis.commons.Wordbook;
import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.pojos.*;
import com.school.cbis.plugin.jsgrid.JsGrid;
import com.school.cbis.service.*;
import com.school.cbis.data.AjaxData;
import com.school.cbis.util.FilesUtils;
import com.school.cbis.vo.tie.TieElegantVo;
import com.school.cbis.vo.tie.TieVo;
import org.jooq.Record;
import org.jooq.Record4;
import org.jooq.Result;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lenovo on 2016-02-03.
 */
@Controller
public class TieManagerController {

    @Resource
    private TieService tieService;

    @Resource
    private ArticleInfoService articleInfoService;

    @Resource
    private ArticleSubService articleSubService;

    @Resource
    private UsersService usersService;

    @Resource
    private Wordbook wordbook;

    @Resource
    private TieElegantService tieElegantService;

    @Resource
    private TieElegantTimeService tieElegantTimeService;

    /**
     * 检验系名
     *
     * @param tieName
     * @return
     */
    @RequestMapping(value = "/maintainer/validTieName", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> validTieName(@RequestParam("tieName") String tieName) {
        Map<String, Object> map = new HashMap<>();
        if (!StringUtils.isEmpty(tieName)) {
            List<Tie> ties = tieService.findByTieName(tieName);

            if (!ties.isEmpty() && ties.size() > 1) {
                map.put("error", "系名已存在!");
            } else {
                map.put("ok", "");
            }
        } else {
            map.put("error", "参数异常!");
        }
        return map;
    }

    /**
     * 更新系信息
     *
     * @param tieVo
     * @param result
     * @return
     */
    @RequestMapping(value = "/maintainer/updateTieInfo", method = RequestMethod.POST)
    public String updateTieInfo(@Valid TieVo tieVo, BindingResult result, ModelMap modelMap) {
        if (!result.hasErrors()) {
            Tie tie = tieService.findById(tieVo.getTieId());
            tie.setTieName(tieVo.getTieName());
            tie.setTieAddress(tieVo.getTieAddress());
            tie.setTiePhone(tieVo.getTiePhone());
            tie.setYardId(tieVo.getYard());
            tieService.update(tie);
        }
        return "redirect:/maintainer/tieManager";
    }

    /**
     * 系风采管理页面
     *
     * @return 页面地址
     */
    @RequestMapping(value = "/maintainer/tieElegant")
    public String backstageTieElegant() {
        return "/maintainer/tieelegantlist";
    }

    /**
     * 加载系风采数据
     * @param tieElegantVo
     * @return
     */
    @RequestMapping(value = "/maintainer/tieElegantData")
    @ResponseBody
    public Map<String, Object> tieElegantData(TieElegantVo tieElegantVo) {
        JsGrid<TieElegantVo> jsGrid = new JsGrid<>(new HashMap<>());
        Result<Record> records = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (records.isNotEmpty()) {
            for (Record r : records) {
                tieId = r.getValue(Tables.TIE.ID);
            }
        }
        List<TieElegantVo> list = new ArrayList<>();
        if (tieId > 0) {
            Result<Record4<Integer, String, String, Timestamp>> record5s = tieElegantService.findByTieIdWithBigTitleAndPage(tieElegantVo, tieId);
            if (record5s.isNotEmpty()) {
                list = record5s.into(TieElegantVo.class);
                jsGrid.loadData(list,tieElegantService.findByTieIdWithBigTitleAndCount(tieElegantVo, tieId));
            } else {
                jsGrid.loadData(list,0);
            }
        } else {
            jsGrid.loadData(list,0);
        }
        return jsGrid.getMap();
    }

    /**
     * 删除系风采
     *
     * @param id 文章id
     * @return
     */
    @RequestMapping(value = "/maintainer/deleteTieElegant", method = RequestMethod.POST)
    @ResponseBody
    public TieElegantVo deleteTieElegant(@RequestParam(value = "id", defaultValue = "0") int id, String imgpath) {
        JsGrid<TieElegantVo> jsGrid = new JsGrid<>();
        try {
            if (id > 0) {
                ArticleInfo articleInfo = articleInfoService.findById(id);
                if (!StringUtils.isEmpty(articleInfo)) {
                    tieElegantService.deleteById(id);
                    articleSubService.deleteByArticleInfoId(id);
                    articleInfoService.deleteById(id);
                    FilesUtils.deleteFile(imgpath);
                    TieElegantVo tieElegantVo = new TieElegantVo();
                    tieElegantVo.setId(id);
                    tieElegantVo.setBigTitle(articleInfo.getBigTitle());
                    tieElegantVo.setUsername(usersService.getUserName());
                    tieElegantVo.setDate(new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(new Date(articleInfo.getDate().getTime())));
                    return jsGrid.deleteItem(tieElegantVo);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 系风采添加页面
     *
     * @return
     */
    @RequestMapping("/maintainer/tieElegantAdd")
    public String tieElegantAdd() {
        return "/maintainer/tieelegantadd";
    }


    /**
     * 跳转更新页面
     *
     * @param id  文章id
     * @param map
     * @return
     */
    @RequestMapping("/maintainer/tieelegantupdate")
    public String tieElegantUpdate(@RequestParam("id") int id, ModelMap map) {
        map.addAttribute("articleinfo", articleInfoService.findById(id));
        List<ArticleSub> articleSubs = articleSubService.findByArticleInfoId(id);
        map.addAttribute("articlesubinfo", articleSubs);
        return "/maintainer/tieelegantupdate";
    }



}
