/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { ServertdjhipTestModule } from '../../../test.module';
import { GhiNoDetailComponent } from '../../../../../../main/webapp/app/entities/ghi-no/ghi-no-detail.component';
import { GhiNoService } from '../../../../../../main/webapp/app/entities/ghi-no/ghi-no.service';
import { GhiNo } from '../../../../../../main/webapp/app/entities/ghi-no/ghi-no.model';

describe('Component Tests', () => {

    describe('GhiNo Management Detail Component', () => {
        let comp: GhiNoDetailComponent;
        let fixture: ComponentFixture<GhiNoDetailComponent>;
        let service: GhiNoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServertdjhipTestModule],
                declarations: [GhiNoDetailComponent],
                providers: [
                    GhiNoService
                ]
            })
            .overrideTemplate(GhiNoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GhiNoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GhiNoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new GhiNo(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.ghiNo).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
