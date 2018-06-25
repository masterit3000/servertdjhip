/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { ServertdjhipTestModule } from '../../../test.module';
import { LichSuThaoTacHopDongDetailComponent } from '../../../../../../main/webapp/app/entities/lich-su-thao-tac-hop-dong/lich-su-thao-tac-hop-dong-detail.component';
import { LichSuThaoTacHopDongService } from '../../../../../../main/webapp/app/entities/lich-su-thao-tac-hop-dong/lich-su-thao-tac-hop-dong.service';
import { LichSuThaoTacHopDong } from '../../../../../../main/webapp/app/entities/lich-su-thao-tac-hop-dong/lich-su-thao-tac-hop-dong.model';

describe('Component Tests', () => {

    describe('LichSuThaoTacHopDong Management Detail Component', () => {
        let comp: LichSuThaoTacHopDongDetailComponent;
        let fixture: ComponentFixture<LichSuThaoTacHopDongDetailComponent>;
        let service: LichSuThaoTacHopDongService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServertdjhipTestModule],
                declarations: [LichSuThaoTacHopDongDetailComponent],
                providers: [
                    LichSuThaoTacHopDongService
                ]
            })
            .overrideTemplate(LichSuThaoTacHopDongDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LichSuThaoTacHopDongDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LichSuThaoTacHopDongService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new LichSuThaoTacHopDong(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.lichSuThaoTacHopDong).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
