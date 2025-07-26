package com.we.coupon.controller;

import com.we.coupon.entity.Coupon;
import com.we.coupon.service.CouponService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

/**
 * 优惠券信息(Coupon)表控制层
 */
@Slf4j
@RestController
@RequestMapping("/coupon")
@RefreshScope
public class CouponController {
    /**
     * 服务对象
     */
    @Resource
    private CouponService couponService;

    @Value("${config.name}")
    private String name;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/info/{id}")
    public Coupon selectOne(@PathVariable Serializable id) {
        log.info("selectOne: {}", id);
        Coupon coupon = couponService.getById(id);
        String couponName = coupon.getCouponName();
        log.info("couponName: {}", couponName);
        return coupon;
    }

    @PostMapping
    public boolean insert(@RequestBody Coupon coupon) {
        return couponService.save(coupon);
    }

    @GetMapping("/test")
    public String test() {
        return "test result = " + name;
    }

}