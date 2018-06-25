/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ServertdjhipTestModule } from '../../../test.module';
import { BatHoComponent } from '../../../../../../main/webapp/app/entities/bat-ho/bat-ho.component';
import { BatHoService } from '../../../../../../main/webapp/app/entities/bat-ho/bat-ho.service';
import { BatHo } from '../../../../../../main/webapp/app/entities/bat-ho/bat-ho.model';

describe('Component Tests', () => {

    describe('BatHo Management Component', () => {
        let comp: BatHoComponent;
        let fixture: ComponentFixture<BatHoComponent>;
        let service: BatHoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServertdjhipTestModule],
                declarations: [BatHoComponent],
                providers: [
                    BatHoService
                ]
            })
            .overrideTemplate(BatHoComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BatHoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BatHoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new BatHo(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.batHos[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
