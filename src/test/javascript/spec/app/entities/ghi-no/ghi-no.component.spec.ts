/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ServertdjhipTestModule } from '../../../test.module';
import { GhiNoComponent } from '../../../../../../main/webapp/app/entities/ghi-no/ghi-no.component';
import { GhiNoService } from '../../../../../../main/webapp/app/entities/ghi-no/ghi-no.service';
import { GhiNo } from '../../../../../../main/webapp/app/entities/ghi-no/ghi-no.model';

describe('Component Tests', () => {

    describe('GhiNo Management Component', () => {
        let comp: GhiNoComponent;
        let fixture: ComponentFixture<GhiNoComponent>;
        let service: GhiNoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServertdjhipTestModule],
                declarations: [GhiNoComponent],
                providers: [
                    GhiNoService
                ]
            })
            .overrideTemplate(GhiNoComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GhiNoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GhiNoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new GhiNo(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.ghiNos[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
