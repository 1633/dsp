package com.tuanzhang.ad.controller;

import com.tuanzhang.ad.annotation.IgnoreResponseAdvice;
import com.tuanzhang.ad.client.SponsorClient;
import com.tuanzhang.ad.client.vo.AdPlan;
import com.tuanzhang.ad.client.vo.AdPlanGetRquest;
import com.tuanzhang.ad.search.ISearch;
import com.tuanzhang.ad.search.vo.SearchRequest;
import com.tuanzhang.ad.search.vo.SearchResponse;
import com.tuanzhang.ad.vo.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
public class SearchController {

    private final RestTemplate restTemplate;

    private final ISearch iSearch;

    @Autowired
    public SearchController(RestTemplate restTemplate, ISearch iSearch) {
        this.restTemplate = restTemplate;
        this.iSearch = iSearch;
    }

    @Resource
    private SponsorClient sponsorClient;

    @SuppressWarnings("all")
    @IgnoreResponseAdvice
    @PostMapping("/getAdPlansBuRibbon")
    public BaseResult<List<AdPlan>> getAdPlanByRebbon(@RequestBody AdPlanGetRquest request){
        return restTemplate.postForEntity(
                "http://eureka-client-ad-sponsor/ad-sponsor/get/plan",
                request,
                BaseResult.class
        ).getBody();
    }


    @IgnoreResponseAdvice
    @PostMapping("/getAdPlans")
    public BaseResult<List<AdPlan>> getAdPlanByFeign(@RequestBody AdPlanGetRquest request){
        return sponsorClient.getAdPlans(request);
    }


    @PostMapping("/fetchAds")
    public SearchResponse fetchAds(@RequestBody SearchRequest request){
        return iSearch.fetch(request);
    }
}
