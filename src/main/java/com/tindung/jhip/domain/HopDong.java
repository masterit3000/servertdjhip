package com.tindung.jhip.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.tindung.jhip.domain.enumeration.LOAIHOPDONG;
import com.tindung.jhip.domain.enumeration.TRANGTHAIHOPDONG;

/**
 * A HopDong.
 */
@Entity
@Table(name = "hop_dong")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class HopDong implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "mahopdong", length = 20, nullable = false)
    private String mahopdong;

    @Size(max = 3000)
    @Column(name = "ghichu", length = 3000)
    private String ghichu;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "loaihopdong", nullable = false)
    private LOAIHOPDONG loaihopdong;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "trangthai", nullable = false)
    private TRANGTHAIHOPDONG trangthaihopdong;

    @NotNull
    @Column(name = "ngaytao", nullable = false)
    private ZonedDateTime ngaytao;

    @ManyToOne
    private KhachHang khachHang;

    @ManyToOne
    private CuaHang cuaHang;

    @ManyToOne
    private NhanVien nhanVien;

    @OneToOne
    @JoinColumn(unique = true)
    private HopDong hopdonggoc;

    @OneToMany(mappedBy = "hopDong")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TaiSan> taisans = new HashSet<>();

    @OneToMany(mappedBy = "hopDong")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<GhiNo> ghinos = new HashSet<>();

    @OneToMany(mappedBy = "hopDong")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LichSuDongTien> lichsudongtiens = new HashSet<>();

    @OneToMany(mappedBy = "hopDong")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LichSuThaoTacHopDong> lichsuthaotachds = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMahopdong() {
        return mahopdong;
    }

    public HopDong mahopdong(String mahopdong) {
        this.mahopdong = mahopdong;
        return this;
    }

    public void setMahopdong(String mahopdong) {
        this.mahopdong = mahopdong;
    }

    public String getGhichu() {
        return ghichu;
    }

    public HopDong ghichu(String ghichu) {
        this.ghichu = ghichu;
        return this;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public LOAIHOPDONG getLoaihopdong() {
        return loaihopdong;
    }

    public HopDong loaihopdong(LOAIHOPDONG loaihopdong) {
        this.loaihopdong = loaihopdong;
        return this;
    }

    public void setLoaihopdong(LOAIHOPDONG loaihopdong) {
        this.loaihopdong = loaihopdong;
    }

    public ZonedDateTime getNgaytao() {
        return ngaytao;
    }

    public HopDong ngaytao(ZonedDateTime ngaytao) {
        this.ngaytao = ngaytao;
        return this;
    }

    public void setNgaytao(ZonedDateTime ngaytao) {
        this.ngaytao = ngaytao;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public HopDong khachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
        return this;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public CuaHang getCuaHang() {
        return cuaHang;
    }

    public HopDong cuaHang(CuaHang cuaHang) {
        this.cuaHang = cuaHang;
        return this;
    }

    public void setCuaHang(CuaHang cuaHang) {
        this.cuaHang = cuaHang;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public HopDong nhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
        return this;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public TRANGTHAIHOPDONG getTrangthaihopdong() {
        return trangthaihopdong;
    }

    public void setTrangthaihopdong(TRANGTHAIHOPDONG trangthaihopdong) {
        this.trangthaihopdong = trangthaihopdong;
    }

    public HopDong getHopdonggoc() {
        return hopdonggoc;
    }

    public HopDong hopdonggoc(HopDong hopDong) {
        this.hopdonggoc = hopDong;
        return this;
    }

    public void setHopdonggoc(HopDong hopDong) {
        this.hopdonggoc = hopDong;
    }

    public Set<TaiSan> getTaisans() {
        return taisans;
    }

    public HopDong taisans(Set<TaiSan> taiSans) {
        this.taisans = taiSans;
        return this;
    }

    public HopDong addTaisan(TaiSan taiSan) {
        this.taisans.add(taiSan);
        taiSan.setHopDong(this);
        return this;
    }

    public HopDong removeTaisan(TaiSan taiSan) {
        this.taisans.remove(taiSan);
        taiSan.setHopDong(null);
        return this;
    }

    public void setTaisans(Set<TaiSan> taiSans) {
        this.taisans = taiSans;
    }

    public Set<GhiNo> getGhinos() {
        return ghinos;
    }

    public HopDong ghinos(Set<GhiNo> ghiNos) {
        this.ghinos = ghiNos;
        return this;
    }

    public HopDong addGhino(GhiNo ghiNo) {
        this.ghinos.add(ghiNo);
        ghiNo.setHopDong(this);
        return this;
    }

    public HopDong removeGhino(GhiNo ghiNo) {
        this.ghinos.remove(ghiNo);
        ghiNo.setHopDong(null);
        return this;
    }

    public void setGhinos(Set<GhiNo> ghiNos) {
        this.ghinos = ghiNos;
    }

    public Set<LichSuDongTien> getLichsudongtiens() {
        return lichsudongtiens;
    }

    public HopDong lichsudongtiens(Set<LichSuDongTien> lichSuDongTiens) {
        this.lichsudongtiens = lichSuDongTiens;
        return this;
    }

    public HopDong addLichsudongtien(LichSuDongTien lichSuDongTien) {
        this.lichsudongtiens.add(lichSuDongTien);
        lichSuDongTien.setHopDong(this);
        return this;
    }

    public HopDong removeLichsudongtien(LichSuDongTien lichSuDongTien) {
        this.lichsudongtiens.remove(lichSuDongTien);
        lichSuDongTien.setHopDong(null);
        return this;
    }

    public void setLichsudongtiens(Set<LichSuDongTien> lichSuDongTiens) {
        this.lichsudongtiens = lichSuDongTiens;
    }

    public Set<LichSuThaoTacHopDong> getLichsuthaotachds() {
        return lichsuthaotachds;
    }

    public HopDong lichsuthaotachds(Set<LichSuThaoTacHopDong> lichSuThaoTacHopDongs) {
        this.lichsuthaotachds = lichSuThaoTacHopDongs;
        return this;
    }

    public HopDong addLichsuthaotachd(LichSuThaoTacHopDong lichSuThaoTacHopDong) {
        this.lichsuthaotachds.add(lichSuThaoTacHopDong);
        lichSuThaoTacHopDong.setHopDong(this);
        return this;
    }

    public HopDong removeLichsuthaotachd(LichSuThaoTacHopDong lichSuThaoTacHopDong) {
        this.lichsuthaotachds.remove(lichSuThaoTacHopDong);
        lichSuThaoTacHopDong.setHopDong(null);
        return this;
    }

    public void setLichsuthaotachds(Set<LichSuThaoTacHopDong> lichSuThaoTacHopDongs) {
        this.lichsuthaotachds = lichSuThaoTacHopDongs;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HopDong hopDong = (HopDong) o;
        if (hopDong.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), hopDong.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HopDong{"
                + "id=" + getId()
                + ", mahopdong='" + getMahopdong() + "'"
                + ", ghichu='" + getGhichu() + "'"
                + ", loaihopdong='" + getLoaihopdong() + "'"
                + ", ngaytao='" + getNgaytao() + "'"
                + "}";
    }
}
