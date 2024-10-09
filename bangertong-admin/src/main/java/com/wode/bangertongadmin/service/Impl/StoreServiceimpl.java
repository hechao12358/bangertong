package com.wode.bangertongadmin.service.Impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.wode.bangertong.common.entity.Store;
import com.wode.bangertong.common.model.LocationData;
import com.wode.bangertong.common.model.Result;
import com.wode.bangertong.common.utils.DateTimeUtils;
import com.wode.bangertong.common.utils.HttpsUtil;
import com.wode.bangertongadmin.mapper.StoreMapper;
import com.wode.bangertongadmin.service.StoreService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class StoreServiceimpl implements StoreService {

    @Resource
    StoreMapper storeMapper;

    @Override
    public Result getPage(Page page, Store store) {
        Page selectPage = storeMapper.selectPage(page, Wrappers.query(store).orderByDesc("update_time"));
        return new Result(Result.RESULT_OK, selectPage, new Date().getTime());
    }

    @Override
    public Result addStore(Store store) {
        Date now = DateTimeUtils.getCurrentDate();
        LocationData location = getLocation(store.getAddress());
        if(location.getInfocode().equals("10000")){
            store.setLocation(location.getLocation());
        } else {
            return new Result(Result.RESULT_FAIL, "错误码:"+location.getInfocode()+","+location.getInfo(), new Date().getTime());
        }
        store.setCreateTime(now);
        store.setUpdateTime(now);
        int insert = storeMapper.insert(store);

        return new Result(Result.RESULT_OK, SqlHelper.retBool(insert) ? "成功" : "失败", new Date().getTime());
    }

    @Override
    public Result editStore(Store store) {
        Date now = DateTimeUtils.getCurrentDate();

        String address = store.getAddress();
        if(null != address){
            LocationData location = getLocation(store.getAddress());
            if(location.getInfocode().equals("10000")){
                store.setLocation(location.getLocation());
            } else {
                return new Result(Result.RESULT_FAIL, "错误码:"+location.getInfocode()+","+location.getInfo(), new Date().getTime());
            }
        }
        store.setUpdateTime(now);

        int update = storeMapper.updateById(store);
        return new Result(Result.RESULT_OK, SqlHelper.retBool(update) ? "成功" : "失败", new Date().getTime());
    }

    public LocationData getLocation(String address){
        LocationData locationData = new LocationData();
        //        double longitude = 0;//经度
//        double latitude= 0;//纬度
        String location = "";
        String s = HttpsUtil.doGet("https://restapi.amap.com/v3/geocode/geo?address="+ address +"&key=00b442616c785e98e5a11265389d5a02");

        JSONObject jsonObject = JSONObject.parseObject(s);
        String status = (String)jsonObject.get("status");
        String infocode = (String)jsonObject.get("infocode");
        String info = (String)jsonObject.get("info");

        locationData.setInfo(info);
        locationData.setInfocode(infocode);

        if(status.equals("1") && info.equals("OK") && infocode.equals("10000")){
            JSONArray geocodes = jsonObject.getJSONArray("geocodes");
            if (geocodes.size() > 0) {
                JSONObject res = geocodes.getJSONObject(0);
                location =(String) res.get("location");
//                String[] split = location.split(",");
//                longitude = Double.valueOf(split[0]);
//                latitude = Double.valueOf(split[1]);

            }
            locationData.setLocation(location);
        }
        return locationData;
    }
}
