/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ServertdjhipTestModule } from '../../../test.module';
import { KhachHangComponent } from '../../../../../../main/webapp/app/entities/khach-hang/khach-hang.component';
import { KhachHangService } from '../../../../../../main/webapp/app/entities/khach-hang/khach-hang.service';
import { KhachHang } from '../../../../../../main/webapp/app/entities/khach-hang/khach-hang.model';

describe('Component Tests', () => {

    describe('KhachHang Management Component', () => {
        let comp: KhachHangComponent;
        let fixture: ComponentFixture<KhachHangComponent>;
        let service: KhachHangService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServertdjhipTestModule],
                declarations: [KhachHangComponent],
                providers: [
                    KhachHangService
                ]
            })
            .overrideTemplate(KhachHangComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(KhachHangComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(KhachHangService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new KhachHang(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.khachHangs[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
