/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ServertdjhipTestModule } from '../../../test.module';
import { HopDongComponent } from '../../../../../../main/webapp/app/entities/hop-dong/hop-dong.component';
import { HopDongService } from '../../../../../../main/webapp/app/entities/hop-dong/hop-dong.service';
import { HopDong } from '../../../../../../main/webapp/app/entities/hop-dong/hop-dong.model';

describe('Component Tests', () => {

    describe('HopDong Management Component', () => {
        let comp: HopDongComponent;
        let fixture: ComponentFixture<HopDongComponent>;
        let service: HopDongService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServertdjhipTestModule],
                declarations: [HopDongComponent],
                providers: [
                    HopDongService
                ]
            })
            .overrideTemplate(HopDongComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HopDongComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HopDongService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new HopDong(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.hopDongs[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
