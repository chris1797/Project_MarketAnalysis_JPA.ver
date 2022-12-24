package com.mac.demo.controller;

import com.mac.demo.model.Coordinates;
import com.mac.demo.service.BigdataService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/big")
public class BigdataController {

    @Autowired
    private final BigdataService bigdataSvc;

    @GetMapping("/location")
    public String location() {
        return "/bigdata/bigdate";
    }

    //해당 데이터 테이블의 구 데이터 가져오기
    @PostMapping("/kind")
    public Map<String, Object> gu(@RequestParam("kind") String kind, Model model) {
        Map<String, Object> map = new HashMap<>();

        //선택창
        String selectgu = "<option>-- 구를 선택해주세요 --</option>";
        String selectgu2 = "<option>-- 구를 먼저 선택해주세요 --</option>";
        //구 리스트 저장
        List<String> gulist = bigdataSvc.getgu(kind);
        //html 해석하게 수정
        List<String> gulist2 = new ArrayList<>();
        for (int i = 0; i < gulist.size(); i++) {
            String gu2 = "<option>" + gulist.get(i) + "</option>";
            gulist2.add(gu2);
        }
        gulist2.add(0, selectgu);

        //구동길 리스트를 맵으로 보여줌
        map.put("gulist", gulist2);
        map.put("donglist", selectgu2);
        map.put("gillist", selectgu2);
        return map;
    }

    //년도 선택

    @PostMapping("/svc")
    public Map<String, Object> svc(@RequestParam("thissvc") String thissvc, @RequestParam("thisgil") String thisgil, @RequestParam("kind") String kind) {
        Map<String, Object> map = new HashMap<>();
        String selectyear = "<option>년도</option>";
        List<String> yearlist = bigdataSvc.getyear(thissvc, thisgil, kind);
        List<String> yearlist2 = new ArrayList<>();
        for (int i = 0; i < yearlist.size(); i++) {
            String dong2 = "<option>" + yearlist.get(i) + "</option>";
            yearlist2.add(dong2);
        }
        yearlist2.add(0, selectyear);
        map.put("yearlist", yearlist2);

        return map;
    }

    @PostMapping("/year")
    public Map<String, Object> year(@RequestParam("year") String year, @RequestParam("thissvc") String thissvc, @RequestParam("thisgil") String thisgil, @RequestParam("kind") String kind) {
        Map<String, Object> map = new HashMap<>();
        String selectquarter = "<option>분기</option>";
        if (kind.equals("sales") || kind.equals("store")) {
            List<String> quarterlist = bigdataSvc.getquarter(year, thissvc, thisgil, kind);
            List<String> quarterlist2 = new ArrayList<>();
            for (int i = 0; i < quarterlist.size(); i++) {
                String dong2 = "<option>" + quarterlist.get(i) + "</option>";
                quarterlist2.add(dong2);
            }
            quarterlist2.add(0, selectquarter);
            map.put("quarterlist", quarterlist2);

        } else if (kind.equals("population")) {
            List<String> quarterlist = bigdataSvc.getquarter(year, thisgil, kind);
            List<String> quarterlist2 = new ArrayList<>();
            for (int i = 0; i < quarterlist.size(); i++) {
                String dong2 = "<option>" + quarterlist.get(i) + "</option>";
                quarterlist2.add(dong2);
            }
            quarterlist2.add(0, selectquarter);
            map.put("quarterlist", quarterlist2);
        }

        return map;
    }

    //해당 데이터 테이블의 동 데이터 가져오기
    @PostMapping("/dong")
    public Map<String, Object> dong(@RequestParam("gu") String gu, @RequestParam("kind") String kind) {
        Map<String, Object> map = new HashMap<>();
        String selectdong = "<option>-- 동을 선택해주세요 --</option>";
        String selectdong2 = "<option>-- 동를 먼저 선택해주세요 --</option>";
        List<String> donglist = bigdataSvc.getdong(gu, kind);
        List<String> donglist2 = new ArrayList<>();
        for (int i = 0; i < donglist.size(); i++) {
            String dong2 = "<option>" + donglist.get(i) + "</option>";
            donglist2.add(dong2);
        }
        donglist2.add(0, selectdong);
        map.put("donglist", donglist2);
        map.put("gillist", selectdong2);
        map.put("kind", kind);
        return map;
    }

    //해당 데이터 테이블의 골목길 데이터 가져오기
    @PostMapping("/gil")
    public Map<String, Object> gil(@RequestParam("dong") String dong,
                                   @RequestParam("kind") String kind) {

        Map<String, Object> map = new HashMap<>();

        String selectgill = "<option>-- 길을 선택하세요 --</option>";

        List<String> gillist = bigdataSvc.getgil(dong, kind);
        List<String> gillist2 = new ArrayList<>();

        for (int i = 0; i < gillist.size(); i++) {
            String dong2 = "<option>" + gillist.get(i) + "</option>";
            gillist2.add(dong2);
        }
        gillist2.add(0, selectgill);

        map.put("gillist", gillist2);
        map.put("kind", kind);

        return map;
    }

    //골목길 좌표 가져오기
    @PostMapping("/xy")
    public Map<String, Object> xy(@RequestParam("gil") String gil, @RequestParam("kind") String kind) {

        Map<String, Object> map = new HashMap<>();
        String selectsvc = "<option>서비스명</option>";
        String selectyear = "<option>년도</option>";

        List<String> svclist2 = new ArrayList<>();
        List<String> yearlist2 = new ArrayList<>();

        if (kind.equals("sales") || kind.equals("store")) {

            List<String> svclist = bigdataSvc.getsvc(gil, kind);
            for (int i = 0; i < svclist.size(); i++) {
                String svc2 = "<option>" + svclist.get(i) + "</option>";
                svclist2.add(svc2);
            }
            svclist2.add(0, selectsvc);
            map.put("svclist", svclist2);

        } else if (kind.equals("population")) {
            List<String> yearlist = bigdataSvc.getPopyear(gil, kind);
            for (int i = 0; i < yearlist.size(); i++) {
                String year2 = "<option>" + yearlist.get(i) + "</option>";
                yearlist2.add(year2);
            }
            yearlist2.add(0, selectyear);
            map.put("yearlist", yearlist2);

        }

        //x,y 좌표값 가져오는 곳
        Coordinates xy = bigdataSvc.getxy(gil);
        int x = xy.getX();
        int y = xy.getY();
        map.put("x", x);
        map.put("y", y);

        return map;
    }
}
