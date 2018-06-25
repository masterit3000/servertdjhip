/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ServertdjhipTestModule } from '../../../test.module';
import { TinhComponent } from '../../../../../../main/webapp/app/entities/tinh/tinh.component';
import { TinhService } from '../../../../../../main/webapp/app/entities/tinh/tinh.service';
import { Tinh } from '../../../../../../main/webapp/app/entities/tinh/tinh.model';

describe('Component Tests', () => {

    describe('Tinh Management Component', () => {
        let comp: TinhComponent;
        let fixture: ComponentFixture<TinhComponent>;
        let service: TinhService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServertdjhipTestModule],
                declarations: [TinhComponent],
                providers: [
                    TinhService
                ]
            })
            .overrideTemplate(TinhComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TinhComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TinhService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Tinh(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.tinhs[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
