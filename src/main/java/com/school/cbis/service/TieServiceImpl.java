package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.records.TieRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lenovo on 2016-01-17.
 */
@Service("tieService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TieServiceImpl implements TieService {

    private final DSLContext create;

    @Autowired
    public TieServiceImpl(DSLContext dslContext) {
        this.create = dslContext;
    }

    @Override
    public boolean updateTie(TieRecord tieRecord) {
        int count = create.update(Tables.TIE)
                .set(Tables.TIE.TIE_NAME, tieRecord.getTieName())
                .set(Tables.TIE.TIE_ADDRESS, tieRecord.getTieAddress())
                .set(Tables.TIE.TIE_PHONE, tieRecord.getTiePhone())
                .set(Tables.TIE.TIE_PRINCIPAL_ARTICLE_INFO_ID, tieRecord.getTiePrincipalArticleInfoId())
                .set(Tables.TIE.TIE_INTRODUCE_ARTICLE_INFO_ID, tieRecord.getTieIntroduceArticleInfoId())
                .set(Tables.TIE.TIE_TRAINING_GOAL_ARTICLE_INFO_ID, tieRecord.getTieTrainingGoalArticleInfoId())
                .set(Tables.TIE.TIE_TRAIT_ARTICLE_INFO_ID, tieRecord.getTieTraitArticleInfoId())
                .set(Tables.TIE.YARD_ID, tieRecord.getYardId())
                .where(Tables.TIE.ID.equal(tieRecord.getId()))
                .execute();
        if (count > 0) {
            return true;
        }
        return false;
    }

    @Override
    public TieRecord getTieInfo(int id) {
        TieRecord tieRecord = create.selectFrom(Tables.TIE).where(Tables.TIE.ID.equal(id)).fetchAny();
        return tieRecord;
    }
}
