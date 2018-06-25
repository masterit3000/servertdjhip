/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { ServertdjhipTestModule } from '../../../test.module';
import { KhachHangDetailComponent } from '../../../../../../main/webapp/app/entities/khach-hang/khach-hang-detail.component';
import { KhachHangService } from '../../../../../../main/webapp/app/entities/khach-hang/khach-hang.service';
import { KhachHang } from '../../../../../../main/webapp/app/entities/khach-hang/khach-hang.model';

describe('Component Tests', () => {

    describe('KhachHang Management Detail Component', () => {
        let comp: KhachHangDetailComponent;
        let fixture: ComponentFixture<KhachHangDetailComponent>;
        let service: KhachHangService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServertdjhipTestModule],
                declarations: [KhachHangDetailComponent],
                providers: [
                    KhachHangService
                ]
            })
            .overrideTemplate(KhachHangDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(KhachHangDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(KhachHangService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new KhachHang(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.khachHang).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
