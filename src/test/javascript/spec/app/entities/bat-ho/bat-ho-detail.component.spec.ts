/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { ServertdjhipTestModule } from '../../../test.module';
import { BatHoDetailComponent } from '../../../../../../main/webapp/app/entities/bat-ho/bat-ho-detail.component';
import { BatHoService } from '../../../../../../main/webapp/app/entities/bat-ho/bat-ho.service';
import { BatHo } from '../../../../../../main/webapp/app/entities/bat-ho/bat-ho.model';

describe('Component Tests', () => {

    describe('BatHo Management Detail Component', () => {
        let comp: BatHoDetailComponent;
        let fixture: ComponentFixture<BatHoDetailComponent>;
        let service: BatHoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServertdjhipTestModule],
                declarations: [BatHoDetailComponent],
                providers: [
                    BatHoService
                ]
            })
            .overrideTemplate(BatHoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BatHoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BatHoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new BatHo(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.batHo).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
