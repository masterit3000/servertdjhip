/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ServertdjhipTestModule } from '../../../test.module';
import { TaiSanComponent } from '../../../../../../main/webapp/app/entities/tai-san/tai-san.component';
import { TaiSanService } from '../../../../../../main/webapp/app/entities/tai-san/tai-san.service';
import { TaiSan } from '../../../../../../main/webapp/app/entities/tai-san/tai-san.model';

describe('Component Tests', () => {

    describe('TaiSan Management Component', () => {
        let comp: TaiSanComponent;
        let fixture: ComponentFixture<TaiSanComponent>;
        let service: TaiSanService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServertdjhipTestModule],
                declarations: [TaiSanComponent],
                providers: [
                    TaiSanService
                ]
            })
            .overrideTemplate(TaiSanComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TaiSanComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TaiSanService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new TaiSan(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.taiSans[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
