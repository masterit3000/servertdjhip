/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ServertdjhipTestModule } from '../../../test.module';
import { NhatKyComponent } from '../../../../../../main/webapp/app/entities/nhat-ky/nhat-ky.component';
import { NhatKyService } from '../../../../../../main/webapp/app/entities/nhat-ky/nhat-ky.service';
import { NhatKy } from '../../../../../../main/webapp/app/entities/nhat-ky/nhat-ky.model';

describe('Component Tests', () => {

    describe('NhatKy Management Component', () => {
        let comp: NhatKyComponent;
        let fixture: ComponentFixture<NhatKyComponent>;
        let service: NhatKyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServertdjhipTestModule],
                declarations: [NhatKyComponent],
                providers: [
                    NhatKyService
                ]
            })
            .overrideTemplate(NhatKyComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NhatKyComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NhatKyService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new NhatKy(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.nhatKies[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
