/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { ServertdjhipTestModule } from '../../../test.module';
import { VayLaiDetailComponent } from '../../../../../../main/webapp/app/entities/vay-lai/vay-lai-detail.component';
import { VayLaiService } from '../../../../../../main/webapp/app/entities/vay-lai/vay-lai.service';
import { VayLai } from '../../../../../../main/webapp/app/entities/vay-lai/vay-lai.model';

describe('Component Tests', () => {

    describe('VayLai Management Detail Component', () => {
        let comp: VayLaiDetailComponent;
        let fixture: ComponentFixture<VayLaiDetailComponent>;
        let service: VayLaiService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServertdjhipTestModule],
                declarations: [VayLaiDetailComponent],
                providers: [
                    VayLaiService
                ]
            })
            .overrideTemplate(VayLaiDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VayLaiDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VayLaiService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new VayLai(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.vayLai).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
