/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ServertdjhipTestModule } from '../../../test.module';
import { XaComponent } from '../../../../../../main/webapp/app/entities/xa/xa.component';
import { XaService } from '../../../../../../main/webapp/app/entities/xa/xa.service';
import { Xa } from '../../../../../../main/webapp/app/entities/xa/xa.model';

describe('Component Tests', () => {

    describe('Xa Management Component', () => {
        let comp: XaComponent;
        let fixture: ComponentFixture<XaComponent>;
        let service: XaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServertdjhipTestModule],
                declarations: [XaComponent],
                providers: [
                    XaService
                ]
            })
            .overrideTemplate(XaComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(XaComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(XaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Xa(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.xas[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
