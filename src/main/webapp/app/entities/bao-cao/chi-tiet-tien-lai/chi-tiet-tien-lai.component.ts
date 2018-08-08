import { Component, OnInit } from '@angular/core';
import { NhanVien, NhanVienService } from '../../nhan-vien';
import { HttpResponse, HttpErrorResponse } from '../../../../../../../node_modules/@angular/common/http';
import { JhiAlertService } from '../../../../../../../node_modules/ng-jhipster';

@Component({
  selector: 'jhi-chi-tiet-tien-lai',
  templateUrl: './chi-tiet-tien-lai.component.html',
  styles: []
})
export class ChiTietTienLaiComponent implements OnInit {
  tungay: Date;
  denngay: Date;
  nhanVien: NhanVien;
  nhanViens: NhanVien[];
  constructor(
    private nhanVienService: NhanVienService,
    private jhiAlertService: JhiAlertService,
  ) {

   }

  ngOnInit() {
    this.loadNhanVien();
  }
  loadNhanVien() {
    this.nhanVienService.query().subscribe(
      (res: HttpResponse<NhanVien[]>) => {
        this.nhanViens = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }
  private onError(error) {
    this.jhiAlertService.error(error.message, null, null);
  }

}
