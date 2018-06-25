/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ServertdjhipTestModule } from '../../../test.module';
import { ThuChiComponent } from '../../../../../../main/webapp/app/entities/thu-chi/thu-chi.component';
import { ThuChiService } from '../../../../../../main/webapp/app/entities/thu-chi/thu-chi.service';
import { ThuChi } from '../../../../../../main/webapp/app/entities/thu-chi/thu-chi.model';

describe('Component Tests', () => {

    describe('ThuChi Management Component', () => {
        let comp: ThuChiComponent;
        let fixture: ComponentFixture<ThuChiComponent>;
        let service: ThuChiService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServertdjhipTestModule],
                declarations: [ThuChiComponent],
                providers: [
                    ThuChiService
                ]
            })
            .overrideTemplate(ThuChiComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ThuChiComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ThuChiService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new ThuChi(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.thuChis[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
