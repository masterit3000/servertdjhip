/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { ServertdjhipTestModule } from '../../../test.module';
import { HopDongDetailComponent } from '../../../../../../main/webapp/app/entities/hop-dong/hop-dong-detail.component';
import { HopDongService } from '../../../../../../main/webapp/app/entities/hop-dong/hop-dong.service';
import { HopDong } from '../../../../../../main/webapp/app/entities/hop-dong/hop-dong.model';

describe('Component Tests', () => {

    describe('HopDong Management Detail Component', () => {
        let comp: HopDongDetailComponent;
        let fixture: ComponentFixture<HopDongDetailComponent>;
        let service: HopDongService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServertdjhipTestModule],
                declarations: [HopDongDetailComponent],
                providers: [
                    HopDongService
                ]
            })
            .overrideTemplate(HopDongDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HopDongDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HopDongService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new HopDong(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.hopDong).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
