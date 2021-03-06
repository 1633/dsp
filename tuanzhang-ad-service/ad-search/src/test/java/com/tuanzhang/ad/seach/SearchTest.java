package com.tuanzhang.ad.seach;

import com.alibaba.fastjson.JSON;
import com.tuanzhang.ad.Application;
import com.tuanzhang.ad.search.ISearch;
import com.tuanzhang.ad.search.vo.SearchRequest;
import com.tuanzhang.ad.search.vo.feature.DistrictFeature;
import com.tuanzhang.ad.search.vo.feature.FeatureRelation;
import com.tuanzhang.ad.search.vo.feature.ItFeature;
import com.tuanzhang.ad.search.vo.feature.KeywordFeature;
import com.tuanzhang.ad.search.vo.media.AdSlot;
import com.tuanzhang.ad.search.vo.media.App;
import com.tuanzhang.ad.search.vo.media.Device;
import com.tuanzhang.ad.search.vo.media.Geo;
import io.micrometer.core.instrument.search.Search;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class SearchTest {

    @Autowired
    private ISearch iSearch;

    @Test
    public void testFetchAds(){
        SearchRequest request = new SearchRequest();
        request.setMediaId("tuanzhang-ad");
        //第一个测试条件
        request.setRequestInfo(new SearchRequest.RequestInfo("aaa", Collections.singletonList(new AdSlot("ad-x", 1, 1080, 720, Arrays.asList(1,2), 1000
        )),buildAdd(),buildGeo(),buildDevice()));
        request.setFeatureInfo(buildExampleDeatureInfo(Arrays.asList("宝马","大众"), Collections.singletonList(new DistrictFeature.ProvinceAndCity("安徽省","合肥市")),
                Arrays.asList("台球","游泳"),FeatureRelation.OR));

        System.out.println(JSON.toJSONString(request));
        System.out.println(JSON.toJSONString(iSearch.fetch(request)));


        request.setRequestInfo(new SearchRequest.RequestInfo("aaa", Collections.singletonList(new AdSlot("ad-y", 1, 1080, 720, Arrays.asList(1,2), 1000
        )),buildAdd(),buildGeo(),buildDevice()));
        request.setFeatureInfo(buildExampleDeatureInfo(Arrays.asList("宝马","大众","标志"), Collections.singletonList(new DistrictFeature.ProvinceAndCity("安徽省","合肥市")),
                Arrays.asList("台球","游泳"),FeatureRelation.AND));

        System.out.println(JSON.toJSONString(request));
        System.out.println(JSON.toJSONString(iSearch.fetch(request)));


    }

    private App buildAdd(){
        return new App("tuanzhang", "tuanzhang",
                "com.tuanzhang", "video");
    }


    private Geo buildGeo(){
        return new Geo((float)100.28, (float)88.61,
                "北京市", "北京市");
    }

    private Device buildDevice(){
        return new Device("iphone","0xxxxx","127.0.0.1",
                "x","1080 720", "1080 720", "1234567989");
    }

    private SearchRequest.FeatureInfo buildExampleDeatureInfo(List<String> keywords, List<DistrictFeature.ProvinceAndCity> provinceAndCities,
                   List<String> its, FeatureRelation relation){
        return new SearchRequest.FeatureInfo(new KeywordFeature(keywords), new DistrictFeature(provinceAndCities), new ItFeature(its), relation);
    }
}
