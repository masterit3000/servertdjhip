/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { ServertdjhipTestModule } from '../../../test.module';
import { ThuChiDetailComponent } from '../../../../../../main/webapp/app/entities/thu-chi/thu-chi-detail.component';
import { ThuChiService } from '../../../../../../main/webapp/app/entities/thu-chi/thu-chi.service';
import { ThuChi } from '../../../../../../main/webapp/app/entities/thu-chi/thu-chi.model';

describe('Component Tests', () => {

    describe('ThuChi Management Detail Component', () => {
        let comp: ThuChiDetailComponent;
        let fixture: ComponentFixture<ThuChiDetailComponent>;
        let service: ThuChiService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServertdjhipTestModule],
                declarations: [ThuChiDetailComponent],
                providers: [
                    ThuChiService
                ]
            })
            .overrideTemplate(ThuChiDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ThuChiDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ThuChiService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new ThuChi(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.thuChi).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
