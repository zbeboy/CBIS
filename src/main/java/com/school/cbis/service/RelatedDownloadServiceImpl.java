package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.RelatedDownloadDao;
import com.school.cbis.domain.tables.daos.StudentCourseTimetableInfoDao;
import com.school.cbis.domain.tables.pojos.RelatedDownload;
import com.school.cbis.vo.eadmin.RelatedDownloadListVo;
import org.jooq.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;

/**
 * Created by lenovo on 2016-06-06.
 */
@Service("relatedDownloadService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class RelatedDownloadServiceImpl implements RelatedDownloadService {

    private final Logger log = LoggerFactory.getLogger(RelatedDownloadServiceImpl.class);

    private final DSLContext create;

    private RelatedDownloadDao relatedDownloadDao;

    @Autowired
    public RelatedDownloadServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.relatedDownloadDao = new RelatedDownloadDao(configuration);
    }

    @Override
    public Result<Record10<Integer, String, String, String, Timestamp, Integer, Integer, String, String, String>> findByTieIdAndTeachTypeIdAndPage(RelatedDownloadListVo relatedDownloadListVo, int tieId) {
        Condition a = Tables.RELATED_DOWNLOAD.TIE_ID.eq(tieId).and(Tables.RELATED_DOWNLOAD.TEACH_TYPE_ID.eq(relatedDownloadListVo.getTeachTypeId()));

        if(StringUtils.hasLength(relatedDownloadListVo.getFileName())){
            a = a.and(Tables.RELATED_DOWNLOAD.FILE_NAME.like("%"+relatedDownloadListVo.getFileName()+"%"));
            a = a.or(Tables.RELATED_DOWNLOAD.FILE_NAME.like("%"+relatedDownloadListVo.getRemark()+"%"));
        }

        if(StringUtils.hasLength(relatedDownloadListVo.getRealName())){
            a = a.and(Tables.USERS.REAL_NAME.like("%"+relatedDownloadListVo.getRealName()+"%"));
        }

        int pageNum = relatedDownloadListVo.getPageNum();
        int pageSize = relatedDownloadListVo.getPageSize();
        if(pageNum<=0){
            pageNum = 1;
        }

        Result<Record10<Integer, String, String, String, Timestamp, Integer, Integer, String, String, String>> record10s =  create.select(Tables.RELATED_DOWNLOAD.ID,
                Tables.RELATED_DOWNLOAD.FILE_URL,
                Tables.RELATED_DOWNLOAD.FILE_SIZE,
                Tables.RELATED_DOWNLOAD.FILE_NAME,
                Tables.RELATED_DOWNLOAD.FILE_DATE,
                Tables.RELATED_DOWNLOAD.FILE_DOWN_TIMES,
                Tables.RELATED_DOWNLOAD.TEACH_TYPE_ID,
                Tables.USERS.REAL_NAME,
                Tables.RELATED_DOWNLOAD.FILE_TYPE,
                Tables.RELATED_DOWNLOAD.REMARK)
                .from(Tables.RELATED_DOWNLOAD)
                .join(Tables.USERS)
                .on(Tables.RELATED_DOWNLOAD.FILE_USER.eq(Tables.USERS.USERNAME))
                .where(a)
                .orderBy(Tables.RELATED_DOWNLOAD.FILE_DATE.desc())
                .limit((pageNum-1)*pageSize,pageSize)
                .fetch();

        return record10s;
    }

    @Override
    public int findByTieIdAndTeachTypeIdAndPageCount(RelatedDownloadListVo relatedDownloadListVo, int tieId) {
        Condition a = Tables.RELATED_DOWNLOAD.TIE_ID.eq(tieId).and(Tables.RELATED_DOWNLOAD.TEACH_TYPE_ID.eq(relatedDownloadListVo.getTeachTypeId()));

        if(StringUtils.hasLength(relatedDownloadListVo.getFileName())){
            a = a.and(Tables.RELATED_DOWNLOAD.FILE_NAME.like("%"+relatedDownloadListVo.getFileName()+"%"));
            a = a.or(Tables.RELATED_DOWNLOAD.FILE_NAME.like("%"+relatedDownloadListVo.getRemark()+"%"));
        }

        if(StringUtils.hasLength(relatedDownloadListVo.getRealName())){
            a = a.and(Tables.USERS.REAL_NAME.like("%"+relatedDownloadListVo.getRealName()+"%"));
        }

      Record1<Integer> record1 = create.selectCount()
                .from(Tables.RELATED_DOWNLOAD)
                .join(Tables.USERS)
                .on(Tables.RELATED_DOWNLOAD.FILE_USER.eq(Tables.USERS.USERNAME))
                .where(a)
                .fetchOne();
        return record1.value1();
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void save(RelatedDownload relatedDownload) {
        relatedDownloadDao.insert(relatedDownload);
    }

    @Override
    public void update(RelatedDownload relatedDownload) {
        relatedDownloadDao.update(relatedDownload);
    }

    @Override
    public RelatedDownload findById(int id) {
        RelatedDownload relatedDownload = relatedDownloadDao.findById(id);
        return relatedDownload;
    }

    @Override
    public void deleteById(int id) {
        relatedDownloadDao.deleteById(id);
    }
}
