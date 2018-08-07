
import { BatHo } from '../../bat-ho';
import { NhanVien, NhanVienService } from '../../nhan-vien';
import { HopDong, HopDongService, LOAIHOPDONG } from '../../hop-dong';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { LichSuDongTien } from '../../lich-su-dong-tien/lich-su-dong-tien.model';
import { LichSuDongTienService } from '../../lich-su-dong-tien/lich-su-dong-tien.service';
import { Principal } from '../../../shared';
import { GhiNo, GhiNoService } from '../../ghi-no';
@Component({
  selector: 'jhi-bao-cao-bat-ho',
  templateUrl: './bao-cao-bat-ho.component.html',
  styles: []
})
export class BaoCaoBatHoComponent implements OnInit {
  batHos: BatHo[];
  selected: BatHo;
  tungay: Date;
  denngay: Date;
  nhanviens: NhanVien[];
  hopDong: HopDong;
  lichSuDongTienVLs: LichSuDongTien[];
  lichSuDongTienBHs: LichSuDongTien[];
  lichSuDongTien: LichSuDongTien;
  currentAccount: any;
  eventSubscriber: Subscription;
  hopDongs: HopDong[];
  nhanViens: NhanVien[];
  nhanVien: NhanVien;
  ghiNos: GhiNo[];
  ghiNo: GhiNo;


  constructor(
    private nhanVienService: NhanVienService,
    private hopDongService: HopDongService,
    private lichSuDongTienService: LichSuDongTienService,
    private ghiNoService: GhiNoService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private principal: Principal
  ) {
    this.lichSuDongTienVLs = new Array<LichSuDongTien>();
    this.lichSuDongTienBHs = new Array<LichSuDongTien>();
  }

  ngOnInit() {
    this.loadLichSuDongTienBH();
    this.loadLichSuDongTienVL();
    this.principal.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInHopDongs();
  }
  timKiem() {
    console.log(this.denngay);
    this.lichSuDongTienService.baoCao(LOAIHOPDONG.BATHO, this.tungay, this.denngay).subscribe(
      (res: HttpResponse<LichSuDongTien[]>) => {
        this.lichSuDongTienBHs = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.lichSuDongTienService.baoCao(LOAIHOPDONG.VAYLAI, this.tungay, this.denngay).subscribe(
      (res: HttpResponse<LichSuDongTien[]>) => {
        this.lichSuDongTienVLs = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }
  registerChangeInHopDongs() {
    this.eventSubscriber = this.eventManager.subscribe(
      'hopDongListModification',
      response => {
        this.loadLichSuDongTienBH();
        this.loadLichSuDongTienVL();
      }
    );
  }
  loadNhanVien() {
    this.nhanVienService.query().subscribe(
      (res: HttpResponse<NhanVien[]>) => {
        this.nhanViens = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }
  loadLichSuDongTienBH() {
    this.tungay = new Date();
    this.denngay = new Date();
    console.log(this.tungay);
    this.lichSuDongTienService.baoCao(LOAIHOPDONG.BATHO, this.tungay, this.denngay).subscribe(
      (res: HttpResponse<LichSuDongTien[]>) => {
        this.lichSuDongTienBHs = res.body;
        this.lichSuDongTienBHs.forEach(element => {
          console.log(element.sotien);
        });

      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }
  loadLichSuDongTienVL() {
    this.tungay = new Date();
    this.denngay = new Date();
    console.log(this.tungay);
    this.lichSuDongTienService.baoCao(LOAIHOPDONG.VAYLAI, this.tungay, this.denngay).subscribe(
      (res: HttpResponse<LichSuDongTien[]>) => {
        this.lichSuDongTienVLs = res.body;
        this.lichSuDongTienVLs.forEach(element => {
          console.log(element.sotien);
        });

      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }
  loadGhiNo() {
    this.ghiNoService.query().subscribe(
      (res: HttpResponse<GhiNo[]>) => {
        this.ghiNos = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }
  private onError(error) {
    this.jhiAlertService.error(error.message, null, null);
  }

}
