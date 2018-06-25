/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ServertdjhipTestModule } from '../../../test.module';
import { AnhTaiSanComponent } from '../../../../../../main/webapp/app/entities/anh-tai-san/anh-tai-san.component';
import { AnhTaiSanService } from '../../../../../../main/webapp/app/entities/anh-tai-san/anh-tai-san.service';
import { AnhTaiSan } from '../../../../../../main/webapp/app/entities/anh-tai-san/anh-tai-san.model';

describe('Component Tests', () => {

    describe('AnhTaiSan Management Component', () => {
        let comp: AnhTaiSanComponent;
        let fixture: ComponentFixture<AnhTaiSanComponent>;
        let service: AnhTaiSanService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServertdjhipTestModule],
                declarations: [AnhTaiSanComponent],
                providers: [
                    AnhTaiSanService
                ]
            })
            .overrideTemplate(AnhTaiSanComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AnhTaiSanComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AnhTaiSanService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new AnhTaiSan(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.anhTaiSans[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
