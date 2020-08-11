package com.tpf.mysql.sequence.dao;

import com.tpf.mysql.sequence.domain.Sequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * <p> </p >
 *
 * @author : tianpf
 * @version :  SequenceDAOImpl.java,v 1.0, 2020/8/10-18:51 Exp $
 */

public class SequenceDAOImpl implements SequenceDAO {

    private static final String QUERY_ALL = " select * from T_SEQUENCE ";
    private static final String LOCK_BY = " select * from T_SEQUENCE where NAME = ? for update ";
    private static final String UPDATE = " update T_SEQUENCE set CURRENT_VALUE = ? where CURRENT_VALUE = ? and NAME = ? ";

    @Autowired
    JdbcTemplate jdbcTemplate;


    private RowMapper<Sequence> rowMapper = new RowMapper<Sequence>() {
        @Override
        public Sequence mapRow(ResultSet paramResultSet, int paramInt) throws SQLException {
            Sequence sequence = new Sequence();
            sequence.setName(paramResultSet.getString("NAME"));
            sequence.setCurrentValue(paramResultSet.getLong("CURRENT_VALUE"));
            sequence.setIncrement(paramResultSet.getInt("INCREMENT"));
            sequence.setTotal(paramResultSet.getInt("TOTAL"));
            sequence.setThreshold(paramResultSet.getInt("THRESHOLD"));

            for(int i = 1; i <= paramResultSet.getMetaData().getColumnCount(); ++i) {
                if ("MAX_VALUE".equals(paramResultSet.getMetaData().getColumnName(i))) {
                    sequence.setMaxValue(paramResultSet.getLong("MAX_VALUE"));
                    break;
                }
            }

            return sequence;
        }
    };

//    private RowMapper<Sequence> rowMapper = (rs, rowNm) -> {
//        Sequence sequence = new Sequence();
//        sequence.setName(rs.getString("NAME"));
//        sequence.setCurrentValue(rs.getLong("CURRENT_VALUE"));
//        sequence.setIncrement(rs.getInt("INCREMENT"));
//        sequence.setTotal(rs.getInt("TOTAL"));
//        sequence.setThreshold(rs.getInt("THRESHOLD"));
//        for (int i = 1; i <= rs.getMetaData().getColumnCount(); ++i) {
//            if ("MAX_VALUE".equals(rs.getMetaData().getColumnName(i))) {
//                sequence.setMaxValue(rs.getLong("MAX_VALUE"));
//                break;
//            }
//        }
//        return sequence;
//    };

    @Override
    public List<Sequence> loadAll() {
        return jdbcTemplate.query(QUERY_ALL, this.rowMapper);
    }

    @Override
    public Sequence lock(String sequenceName) {
        return (Sequence) jdbcTemplate.queryForObject(LOCK_BY, new Object[]{sequenceName}, this.rowMapper);
    }

    @Override
    public void update(String sequenceName, Long beforeValue, Long afterValue) {
        jdbcTemplate.update(UPDATE, new Object[]{afterValue, beforeValue, sequenceName});
    }

}
