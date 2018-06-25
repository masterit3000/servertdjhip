/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { ServertdjhipTestModule } from '../../../test.module';
import { NhatKyDetailComponent } from '../../../../../../main/webapp/app/entities/nhat-ky/nhat-ky-detail.component';
import { NhatKyService } from '../../../../../../main/webapp/app/entities/nhat-ky/nhat-ky.service';
import { NhatKy } from '../../../../../../main/webapp/app/entities/nhat-ky/nhat-ky.model';

describe('Component Tests', () => {

    describe('NhatKy Management Detail Component', () => {
        let comp: NhatKyDetailComponent;
        let fixture: ComponentFixture<NhatKyDetailComponent>;
        let service: NhatKyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServertdjhipTestModule],
                declarations: [NhatKyDetailComponent],
                providers: [
                    NhatKyService
                ]
            })
            .overrideTemplate(NhatKyDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NhatKyDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NhatKyService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new NhatKy(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.nhatKy).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
