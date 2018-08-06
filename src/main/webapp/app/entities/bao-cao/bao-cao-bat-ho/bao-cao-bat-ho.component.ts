import { Component, OnInit } from '@angular/core';
import { BatHo } from '../../bat-ho';
import { NhanVien, NhanVienService } from '../../nhan-vien';
import { HopDong } from '../../hop-dong';

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
  constructor(
    private nhanVienService: NhanVienService,
  ) { 
    
  }

  ngOnInit() {
  }
  timKiem(){}

}
