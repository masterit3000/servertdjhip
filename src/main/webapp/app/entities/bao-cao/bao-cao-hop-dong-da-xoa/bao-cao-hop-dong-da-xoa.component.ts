
import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { LichSuDongTien, LichSuDongTienService, DONGTIEN } from '../../lich-su-dong-tien';
import { Principal } from '../../../shared';
import { LOAIHOPDONG } from '../../hop-dong';
@Component({
  selector: 'jhi-bao-cao-hop-dong-da-xoa',
  templateUrl: './bao-cao-hop-dong-da-xoa.component.html',
  styles: []
})
export class BaoCaoHopDongDaXoaComponent implements OnInit {
  lichSuDongTienBHs: LichSuDongTien[];
  lichSuDongTienHomNayBHs: LichSuDongTien[];
  lichSuDongTienVLs: LichSuDongTien[];
  lichSuDongTienHomNayVLs: LichSuDongTien[];
  currentAccount: any;
  eventSubscriber: Subscription;
  constructor(
    private lichSuDongTienService: LichSuDongTienService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private principal: Principal
  ) { }

  ngOnInit() {
    this.loadLichSuTraChamBatHo();
    this.loadLichSuTraChamVayLai();
    this.loadLichSuTraBatHoHomNay();
    this.loadLichSuTraVayLaiHomNay();
    this.principal.identity().then((account) => {
      this.currentAccount = account;
    });
  }
  loadLichSuTraChamBatHo() {
    this.lichSuDongTienService.lichSuTraCham(DONGTIEN.CHUADONG, LOAIHOPDONG.BATHO).subscribe(
      (res: HttpResponse<LichSuDongTien[]>) => {
        this.lichSuDongTienBHs = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }
  loadLichSuTraChamVayLai() {
    this.lichSuDongTienService.lichSuTraCham(DONGTIEN.CHUADONG, LOAIHOPDONG.VAYLAI).subscribe(
      (res: HttpResponse<LichSuDongTien[]>) => {
        this.lichSuDongTienVLs = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }
  loadLichSuTraBatHoHomNay() {
    this.lichSuDongTienService.lichSuTraHomNay(DONGTIEN.CHUADONG, LOAIHOPDONG.BATHO).subscribe(
      (res: HttpResponse<LichSuDongTien[]>) => {
        this.lichSuDongTienHomNayBHs = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }
  loadLichSuTraVayLaiHomNay() {
    this.lichSuDongTienService.lichSuTraHomNay(DONGTIEN.CHUADONG, LOAIHOPDONG.VAYLAI).subscribe(
      (res: HttpResponse<LichSuDongTien[]>) => {
        this.lichSuDongTienHomNayVLs = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }
  private onError(error) {
    this.jhiAlertService.error(error.message, null, null);
  }
}
