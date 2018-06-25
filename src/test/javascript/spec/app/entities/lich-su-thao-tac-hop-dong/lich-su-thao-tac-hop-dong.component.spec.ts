/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ServertdjhipTestModule } from '../../../test.module';
import { LichSuThaoTacHopDongComponent } from '../../../../../../main/webapp/app/entities/lich-su-thao-tac-hop-dong/lich-su-thao-tac-hop-dong.component';
import { LichSuThaoTacHopDongService } from '../../../../../../main/webapp/app/entities/lich-su-thao-tac-hop-dong/lich-su-thao-tac-hop-dong.service';
import { LichSuThaoTacHopDong } from '../../../../../../main/webapp/app/entities/lich-su-thao-tac-hop-dong/lich-su-thao-tac-hop-dong.model';

describe('Component Tests', () => {

    describe('LichSuThaoTacHopDong Management Component', () => {
        let comp: LichSuThaoTacHopDongComponent;
        let fixture: ComponentFixture<LichSuThaoTacHopDongComponent>;
        let service: LichSuThaoTacHopDongService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServertdjhipTestModule],
                declarations: [LichSuThaoTacHopDongComponent],
                providers: [
                    LichSuThaoTacHopDongService
                ]
            })
            .overrideTemplate(LichSuThaoTacHopDongComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LichSuThaoTacHopDongComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LichSuThaoTacHopDongService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new LichSuThaoTacHopDong(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.lichSuThaoTacHopDongs[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
