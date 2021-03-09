package ru.job4j.carsales.repo;

import lombok.Setter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;


@Setter
public class Filter {
    private Long modelId = 0L;
    private Long markId = 0L;
    private boolean freshAd = false;
    private boolean withPhotos = false;

    public String doFilter() {
        String sql = "select distinct a from Announcement a left join fetch a.photos ";
        StringJoiner joiner = new StringJoiner(" and ");
        if (modelId != null && modelId > 0L) {
            joiner.add("a.car.model.id = :moId");
        } else if (markId != null && markId > 0L) {
            joiner.add("a.car.model.mark.id = :mId");
        }
        if (freshAd)
            joiner.add("a.created > : date");
        if (withPhotos)
            joiner.add("size(a.photos) > 0");
        String filter = joiner.toString();
        if (!filter.isEmpty()) {
            sql += " where " + filter;
        }
        return sql;
    }

    public Map<String, Object> getParametersSet(){
        Map<String, Object> result = new HashMap<>();
        if (modelId != null && modelId > 0L) {
            result.put("moId", modelId);
        } else if (markId != null && markId > 0L) {
            result.put("mId", markId);
        }
        if (freshAd) {
            Date date = yesterday();
            result.put("date", date);
        }
        return result;
    }

    private Date yesterday() {
        LocalDate yesterday = LocalDate.now().minusDays(1L);
        return Date.from(yesterday.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}