package com.evil.liveplayer;

import com.evil.helper.recycler.inface.IRecycleData;

import org.litepal.crud.LitePalSupport;

public class LiveInfo extends LitePalSupport implements IRecycleData {
    private int id;
    private String name;
    private String url;
    private boolean loseEfficacy;//是否失效

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isLoseEfficacy() {
        return loseEfficacy;
    }

    public void setLoseEfficacy(boolean loseEfficacy) {
        this.loseEfficacy = loseEfficacy;
    }

    @Override
    public int getRecycleType() {
        return 0;
    }
}
