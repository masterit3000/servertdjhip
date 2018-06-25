/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ServertdjhipTestModule } from '../../../test.module';
import { CuaHangComponent } from '../../../../../../main/webapp/app/entities/cua-hang/cua-hang.component';
import { CuaHangService } from '../../../../../../main/webapp/app/entities/cua-hang/cua-hang.service';
import { CuaHang } from '../../../../../../main/webapp/app/entities/cua-hang/cua-hang.model';

describe('Component Tests', () => {

    describe('CuaHang Management Component', () => {
        let comp: CuaHangComponent;
        let fixture: ComponentFixture<CuaHangComponent>;
        let service: CuaHangService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServertdjhipTestModule],
                declarations: [CuaHangComponent],
                providers: [
                    CuaHangService
                ]
            })
            .overrideTemplate(CuaHangComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CuaHangComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CuaHangService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new CuaHang(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.cuaHangs[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
