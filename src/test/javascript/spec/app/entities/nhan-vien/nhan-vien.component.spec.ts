/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ServertdjhipTestModule } from '../../../test.module';
import { NhanVienComponent } from '../../../../../../main/webapp/app/entities/nhan-vien/nhan-vien.component';
import { NhanVienService } from '../../../../../../main/webapp/app/entities/nhan-vien/nhan-vien.service';
import { NhanVien } from '../../../../../../main/webapp/app/entities/nhan-vien/nhan-vien.model';

describe('Component Tests', () => {

    describe('NhanVien Management Component', () => {
        let comp: NhanVienComponent;
        let fixture: ComponentFixture<NhanVienComponent>;
        let service: NhanVienService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServertdjhipTestModule],
                declarations: [NhanVienComponent],
                providers: [
                    NhanVienService
                ]
            })
            .overrideTemplate(NhanVienComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NhanVienComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NhanVienService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new NhanVien(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.nhanViens[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
