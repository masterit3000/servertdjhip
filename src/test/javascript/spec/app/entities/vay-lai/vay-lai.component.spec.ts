/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ServertdjhipTestModule } from '../../../test.module';
import { VayLaiComponent } from '../../../../../../main/webapp/app/entities/vay-lai/vay-lai.component';
import { VayLaiService } from '../../../../../../main/webapp/app/entities/vay-lai/vay-lai.service';
import { VayLai } from '../../../../../../main/webapp/app/entities/vay-lai/vay-lai.model';

describe('Component Tests', () => {

    describe('VayLai Management Component', () => {
        let comp: VayLaiComponent;
        let fixture: ComponentFixture<VayLaiComponent>;
        let service: VayLaiService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServertdjhipTestModule],
                declarations: [VayLaiComponent],
                providers: [
                    VayLaiService
                ]
            })
            .overrideTemplate(VayLaiComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VayLaiComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VayLaiService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new VayLai(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.vayLais[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
