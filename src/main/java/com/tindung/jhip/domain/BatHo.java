package com.tindung.jhip.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A BatHo.
 */
@Entity
@Table(name = "bat_ho")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BatHo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "tienduakhach", nullable = false)
    private Double tienduakhach;

    @NotNull
    @Column(name = "tongtien", nullable = false)
    private Double tongtien;

    @NotNull
    @Column(name = "tongsongay", nullable = false)
    private Integer tongsongay;

    @NotNull
    @Column(name = "chuky", nullable = false)
    private Integer chuky;

    @OneToOne
    @JoinColumn(unique = true)
    private HopDong hopdongbh;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTienduakhach() {
        return tienduakhach;
    }

    public BatHo tienduakhach(Double tienduakhach) {
        this.tienduakhach = tienduakhach;
        return this;
    }

    public void setTienduakhach(Double tienduakhach) {
        this.tienduakhach = tienduakhach;
    }

    public Double getTongtien() {
        return tongtien;
    }

    public BatHo tongtien(Double tongtien) {
        this.tongtien = tongtien;
        return this;
    }

    public void setTongtien(Double tongtien) {
        this.tongtien = tongtien;
    }

    public Integer getTongsongay() {
        return tongsongay;
    }

    public BatHo tongsongay(Integer tongsongay) {
        this.tongsongay = tongsongay;
        return this;
    }

    public void setTongsongay(Integer tongsongay) {
        this.tongsongay = tongsongay;
    }

    public Integer getChuky() {
        return chuky;
    }

    public BatHo chuky(Integer chuky) {
        this.chuky = chuky;
        return this;
    }

    public void setChuky(Integer chuky) {
        this.chuky = chuky;
    }

    public HopDong getHopdongbh() {
        return hopdongbh;
    }

    public BatHo hopdongbh(HopDong hopDong) {
        this.hopdongbh = hopDong;
        return this;
    }

    public void setHopdongbh(HopDong hopDong) {
        this.hopdongbh = hopDong;
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
        BatHo batHo = (BatHo) o;
        if (batHo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), batHo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BatHo{" +
            "id=" + getId() +
            ", tienduakhach=" + getTienduakhach() +
            ", tongtien=" + getTongtien() +
            ", tongsongay=" + getTongsongay() +
            ", chuky=" + getChuky() +
            "}";
    }
}
