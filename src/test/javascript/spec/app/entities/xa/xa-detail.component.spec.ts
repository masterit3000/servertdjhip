/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { ServertdjhipTestModule } from '../../../test.module';
import { XaDetailComponent } from '../../../../../../main/webapp/app/entities/xa/xa-detail.component';
import { XaService } from '../../../../../../main/webapp/app/entities/xa/xa.service';
import { Xa } from '../../../../../../main/webapp/app/entities/xa/xa.model';

describe('Component Tests', () => {

    describe('Xa Management Detail Component', () => {
        let comp: XaDetailComponent;
        let fixture: ComponentFixture<XaDetailComponent>;
        let service: XaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServertdjhipTestModule],
                declarations: [XaDetailComponent],
                providers: [
                    XaService
                ]
            })
            .overrideTemplate(XaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(XaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(XaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Xa(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.xa).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
