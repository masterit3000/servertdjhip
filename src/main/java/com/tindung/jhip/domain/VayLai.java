package com.tindung.jhip.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.tindung.jhip.domain.enumeration.HINHTHUCLAI;

import com.tindung.jhip.domain.enumeration.TINHLAI;

/**
 * A VayLai.
 */
@Entity
@Table(name = "vay_lai")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class VayLai implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "tienvay", nullable = false)
    private Double tienvay;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "hinhthuclai", nullable = false)
    private HINHTHUCLAI hinhthuclai;

    @NotNull
    @Column(name = "thoigianvay", nullable = false)
    private Integer thoigianvay;

    @NotNull
    @Column(name = "chukylai", nullable = false)
    private Integer chukylai;

    @NotNull
    @Column(name = "lai", nullable = false)
    private Float lai;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "cachtinhlai", nullable = false)
    private TINHLAI cachtinhlai;

    @Column(name = "thulaitruoc")
    private Boolean thulaitruoc;

    @OneToOne
    @JoinColumn(unique = true)
    private HopDong hopdongvl;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTienvay() {
        return tienvay;
    }

    public VayLai tienvay(Double tienvay) {
        this.tienvay = tienvay;
        return this;
    }

    public void setTienvay(Double tienvay) {
        this.tienvay = tienvay;
    }

    public HINHTHUCLAI getHinhthuclai() {
        return hinhthuclai;
    }

    public VayLai hinhthuclai(HINHTHUCLAI hinhthuclai) {
        this.hinhthuclai = hinhthuclai;
        return this;
    }

    public void setHinhthuclai(HINHTHUCLAI hinhthuclai) {
        this.hinhthuclai = hinhthuclai;
    }

    public Integer getThoigianvay() {
        return thoigianvay;
    }

    public VayLai thoigianvay(Integer thoigianvay) {
        this.thoigianvay = thoigianvay;
        return this;
    }

    public void setThoigianvay(Integer thoigianvay) {
        this.thoigianvay = thoigianvay;
    }

    public Integer getChukylai() {
        return chukylai;
    }

    public VayLai chukylai(Integer chukylai) {
        this.chukylai = chukylai;
        return this;
    }

    public void setChukylai(Integer chukylai) {
        this.chukylai = chukylai;
    }

    public Float getLai() {
        return lai;
    }

    public VayLai lai(Float lai) {
        this.lai = lai;
        return this;
    }

    public void setLai(Float lai) {
        this.lai = lai;
    }

    public TINHLAI getCachtinhlai() {
        return cachtinhlai;
    }

    public VayLai cachtinhlai(TINHLAI cachtinhlai) {
        this.cachtinhlai = cachtinhlai;
        return this;
    }

    public void setCachtinhlai(TINHLAI cachtinhlai) {
        this.cachtinhlai = cachtinhlai;
    }

    public Boolean isThulaitruoc() {
        return thulaitruoc;
    }

    public VayLai thulaitruoc(Boolean thulaitruoc) {
        this.thulaitruoc = thulaitruoc;
        return this;
    }

    public void setThulaitruoc(Boolean thulaitruoc) {
        this.thulaitruoc = thulaitruoc;
    }

    public HopDong getHopdongvl() {
        return hopdongvl;
    }

    public VayLai hopdongvl(HopDong hopDong) {
        this.hopdongvl = hopDong;
        return this;
    }

    public void setHopdongvl(HopDong hopDong) {
        this.hopdongvl = hopDong;
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
        VayLai vayLai = (VayLai) o;
        if (vayLai.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vayLai.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VayLai{" +
            "id=" + getId() +
            ", tienvay=" + getTienvay() +
            ", hinhthuclai='" + getHinhthuclai() + "'" +
            ", thoigianvay=" + getThoigianvay() +
            ", chukylai=" + getChukylai() +
            ", lai=" + getLai() +
            ", cachtinhlai='" + getCachtinhlai() + "'" +
            ", thulaitruoc='" + isThulaitruoc() + "'" +
            "}";
    }
}
