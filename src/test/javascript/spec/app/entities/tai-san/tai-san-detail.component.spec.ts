/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { ServertdjhipTestModule } from '../../../test.module';
import { TaiSanDetailComponent } from '../../../../../../main/webapp/app/entities/tai-san/tai-san-detail.component';
import { TaiSanService } from '../../../../../../main/webapp/app/entities/tai-san/tai-san.service';
import { TaiSan } from '../../../../../../main/webapp/app/entities/tai-san/tai-san.model';

describe('Component Tests', () => {

    describe('TaiSan Management Detail Component', () => {
        let comp: TaiSanDetailComponent;
        let fixture: ComponentFixture<TaiSanDetailComponent>;
        let service: TaiSanService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServertdjhipTestModule],
                declarations: [TaiSanDetailComponent],
                providers: [
                    TaiSanService
                ]
            })
            .overrideTemplate(TaiSanDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TaiSanDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TaiSanService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new TaiSan(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.taiSan).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
