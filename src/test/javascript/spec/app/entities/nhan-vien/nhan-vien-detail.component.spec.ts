/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { ServertdjhipTestModule } from '../../../test.module';
import { NhanVienDetailComponent } from '../../../../../../main/webapp/app/entities/nhan-vien/nhan-vien-detail.component';
import { NhanVienService } from '../../../../../../main/webapp/app/entities/nhan-vien/nhan-vien.service';
import { NhanVien } from '../../../../../../main/webapp/app/entities/nhan-vien/nhan-vien.model';

describe('Component Tests', () => {

    describe('NhanVien Management Detail Component', () => {
        let comp: NhanVienDetailComponent;
        let fixture: ComponentFixture<NhanVienDetailComponent>;
        let service: NhanVienService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServertdjhipTestModule],
                declarations: [NhanVienDetailComponent],
                providers: [
                    NhanVienService
                ]
            })
            .overrideTemplate(NhanVienDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NhanVienDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NhanVienService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new NhanVien(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.nhanVien).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
