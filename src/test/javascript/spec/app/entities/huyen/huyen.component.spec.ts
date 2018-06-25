/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ServertdjhipTestModule } from '../../../test.module';
import { HuyenComponent } from '../../../../../../main/webapp/app/entities/huyen/huyen.component';
import { HuyenService } from '../../../../../../main/webapp/app/entities/huyen/huyen.service';
import { Huyen } from '../../../../../../main/webapp/app/entities/huyen/huyen.model';

describe('Component Tests', () => {

    describe('Huyen Management Component', () => {
        let comp: HuyenComponent;
        let fixture: ComponentFixture<HuyenComponent>;
        let service: HuyenService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServertdjhipTestModule],
                declarations: [HuyenComponent],
                providers: [
                    HuyenService
                ]
            })
            .overrideTemplate(HuyenComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HuyenComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HuyenService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Huyen(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.huyens[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
