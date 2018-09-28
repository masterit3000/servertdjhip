package com.tindung.jhip.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.tindung.jhip.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.tindung.jhip.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.tindung.jhip.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.tindung.jhip.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.tindung.jhip.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.tindung.jhip.domain.Tinh.class.getName(), jcacheConfiguration);
            cm.createCache(com.tindung.jhip.domain.Tinh.class.getName() + ".huyens", jcacheConfiguration);
            cm.createCache(com.tindung.jhip.domain.Huyen.class.getName(), jcacheConfiguration);
            cm.createCache(com.tindung.jhip.domain.Huyen.class.getName() + ".xas", jcacheConfiguration);
            cm.createCache(com.tindung.jhip.domain.Xa.class.getName(), jcacheConfiguration);
            cm.createCache(com.tindung.jhip.domain.Xa.class.getName() + ".khachangs", jcacheConfiguration);
            cm.createCache(com.tindung.jhip.domain.Xa.class.getName() + ".nhanviens", jcacheConfiguration);
            cm.createCache(com.tindung.jhip.domain.Xa.class.getName() + ".cuahangs", jcacheConfiguration);
            cm.createCache(com.tindung.jhip.domain.KhachHang.class.getName(), jcacheConfiguration);
            cm.createCache(com.tindung.jhip.domain.KhachHang.class.getName() + ".anhs", jcacheConfiguration);
            cm.createCache(com.tindung.jhip.domain.KhachHang.class.getName() + ".hopdongs", jcacheConfiguration);
            cm.createCache(com.tindung.jhip.domain.CuaHang.class.getName(), jcacheConfiguration);
            cm.createCache(com.tindung.jhip.domain.CuaHang.class.getName() + ".thuchis", jcacheConfiguration);
            cm.createCache(com.tindung.jhip.domain.CuaHang.class.getName() + ".khachhangs", jcacheConfiguration);
            cm.createCache(com.tindung.jhip.domain.CuaHang.class.getName() + ".nhanviens", jcacheConfiguration);
            cm.createCache(com.tindung.jhip.domain.CuaHang.class.getName() + ".hopdongs", jcacheConfiguration);
            cm.createCache(com.tindung.jhip.domain.NhanVien.class.getName(), jcacheConfiguration);
            cm.createCache(com.tindung.jhip.domain.NhanVien.class.getName() + ".thuchis", jcacheConfiguration);
            cm.createCache(com.tindung.jhip.domain.NhanVien.class.getName() + ".lichsudongtiens", jcacheConfiguration);
            cm.createCache(com.tindung.jhip.domain.NhanVien.class.getName() + ".ghinos", jcacheConfiguration);
            cm.createCache(com.tindung.jhip.domain.NhanVien.class.getName() + ".hopdongs", jcacheConfiguration);
            cm.createCache(com.tindung.jhip.domain.NhanVien.class.getName() + ".lichsuthaotacnvs", jcacheConfiguration);
            cm.createCache(com.tindung.jhip.domain.AnhKhachHang.class.getName(), jcacheConfiguration);
            cm.createCache(com.tindung.jhip.domain.HopDong.class.getName(), jcacheConfiguration);
            cm.createCache(com.tindung.jhip.domain.HopDong.class.getName() + ".taisans", jcacheConfiguration);
            cm.createCache(com.tindung.jhip.domain.HopDong.class.getName() + ".ghinos", jcacheConfiguration);
            cm.createCache(com.tindung.jhip.domain.HopDong.class.getName() + ".lichsudongtiens", jcacheConfiguration);
            cm.createCache(com.tindung.jhip.domain.HopDong.class.getName() + ".lichsuthaotachds", jcacheConfiguration);
            cm.createCache(com.tindung.jhip.domain.LichSuDongTien.class.getName(), jcacheConfiguration);
            cm.createCache(com.tindung.jhip.domain.BatHo.class.getName(), jcacheConfiguration);
            cm.createCache(com.tindung.jhip.domain.VayLai.class.getName(), jcacheConfiguration);
            cm.createCache(com.tindung.jhip.domain.GhiNo.class.getName(), jcacheConfiguration);
            cm.createCache(com.tindung.jhip.domain.LichSuThaoTacHopDong.class.getName(), jcacheConfiguration);
            cm.createCache(com.tindung.jhip.domain.ThuChi.class.getName(), jcacheConfiguration);
            cm.createCache(com.tindung.jhip.domain.TaiSan.class.getName(), jcacheConfiguration);
            cm.createCache(com.tindung.jhip.domain.TaiSan.class.getName() + ".anhs", jcacheConfiguration);
            cm.createCache(com.tindung.jhip.domain.AnhTaiSan.class.getName(), jcacheConfiguration);
            cm.createCache(com.tindung.jhip.domain.NhanVien.class.getName() + ".nhatkies", jcacheConfiguration);
            cm.createCache(com.tindung.jhip.domain.NhatKy.class.getName(), jcacheConfiguration);
            cm.createCache(com.tindung.jhip.domain.CuaHang.class.getName() + ".nhatkies", jcacheConfiguration);
            cm.createCache(com.tindung.jhip.domain.Xa.class.getName() + ".khachhangs", jcacheConfiguration);
            cm.createCache(com.tindung.jhip.domain.Images.class.getName(),  jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
