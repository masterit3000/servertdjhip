/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { ServertdjhipTestModule } from '../../../test.module';
import { CuaHangDetailComponent } from '../../../../../../main/webapp/app/entities/cua-hang/cua-hang-detail.component';
import { CuaHangService } from '../../../../../../main/webapp/app/entities/cua-hang/cua-hang.service';
import { CuaHang } from '../../../../../../main/webapp/app/entities/cua-hang/cua-hang.model';

describe('Component Tests', () => {

    describe('CuaHang Management Detail Component', () => {
        let comp: CuaHangDetailComponent;
        let fixture: ComponentFixture<CuaHangDetailComponent>;
        let service: CuaHangService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServertdjhipTestModule],
                declarations: [CuaHangDetailComponent],
                providers: [
                    CuaHangService
                ]
            })
            .overrideTemplate(CuaHangDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CuaHangDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CuaHangService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new CuaHang(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.cuaHang).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
