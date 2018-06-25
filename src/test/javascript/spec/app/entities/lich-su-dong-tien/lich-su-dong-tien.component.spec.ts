/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ServertdjhipTestModule } from '../../../test.module';
import { LichSuDongTienComponent } from '../../../../../../main/webapp/app/entities/lich-su-dong-tien/lich-su-dong-tien.component';
import { LichSuDongTienService } from '../../../../../../main/webapp/app/entities/lich-su-dong-tien/lich-su-dong-tien.service';
import { LichSuDongTien } from '../../../../../../main/webapp/app/entities/lich-su-dong-tien/lich-su-dong-tien.model';

describe('Component Tests', () => {

    describe('LichSuDongTien Management Component', () => {
        let comp: LichSuDongTienComponent;
        let fixture: ComponentFixture<LichSuDongTienComponent>;
        let service: LichSuDongTienService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServertdjhipTestModule],
                declarations: [LichSuDongTienComponent],
                providers: [
                    LichSuDongTienService
                ]
            })
            .overrideTemplate(LichSuDongTienComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LichSuDongTienComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LichSuDongTienService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new LichSuDongTien(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.lichSuDongTiens[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
