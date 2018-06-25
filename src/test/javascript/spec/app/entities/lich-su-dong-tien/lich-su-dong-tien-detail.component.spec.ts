/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { ServertdjhipTestModule } from '../../../test.module';
import { LichSuDongTienDetailComponent } from '../../../../../../main/webapp/app/entities/lich-su-dong-tien/lich-su-dong-tien-detail.component';
import { LichSuDongTienService } from '../../../../../../main/webapp/app/entities/lich-su-dong-tien/lich-su-dong-tien.service';
import { LichSuDongTien } from '../../../../../../main/webapp/app/entities/lich-su-dong-tien/lich-su-dong-tien.model';

describe('Component Tests', () => {

    describe('LichSuDongTien Management Detail Component', () => {
        let comp: LichSuDongTienDetailComponent;
        let fixture: ComponentFixture<LichSuDongTienDetailComponent>;
        let service: LichSuDongTienService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServertdjhipTestModule],
                declarations: [LichSuDongTienDetailComponent],
                providers: [
                    LichSuDongTienService
                ]
            })
            .overrideTemplate(LichSuDongTienDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LichSuDongTienDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LichSuDongTienService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new LichSuDongTien(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.lichSuDongTien).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
