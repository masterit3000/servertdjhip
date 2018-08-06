import { Component, OnInit } from '@angular/core';
import { NhanVienService, NhanVien } from '../../nhan-vien';
import { HopDong } from '../../hop-dong';
import { BatHo } from '../../bat-ho';

@Component({
  selector: 'jhi-bao-cao-vay-lai',
  templateUrl: './bao-cao-vay-lai.component.html',
  styles: []
})
export class BaoCaoVayLaiComponent implements OnInit {
  batHos: BatHo[];
  selected: BatHo;
  tungay: Date;
  denngay: Date;
  nhanviens: NhanVien[];
  hopDong: HopDong;
  constructor(
    private nhanVienService: NhanVienService,
  ) {
    
   }

  ngOnInit() {
  }

}
