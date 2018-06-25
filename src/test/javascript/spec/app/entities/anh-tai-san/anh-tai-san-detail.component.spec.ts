/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { ServertdjhipTestModule } from '../../../test.module';
import { AnhTaiSanDetailComponent } from '../../../../../../main/webapp/app/entities/anh-tai-san/anh-tai-san-detail.component';
import { AnhTaiSanService } from '../../../../../../main/webapp/app/entities/anh-tai-san/anh-tai-san.service';
import { AnhTaiSan } from '../../../../../../main/webapp/app/entities/anh-tai-san/anh-tai-san.model';

describe('Component Tests', () => {

    describe('AnhTaiSan Management Detail Component', () => {
        let comp: AnhTaiSanDetailComponent;
        let fixture: ComponentFixture<AnhTaiSanDetailComponent>;
        let service: AnhTaiSanService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServertdjhipTestModule],
                declarations: [AnhTaiSanDetailComponent],
                providers: [
                    AnhTaiSanService
                ]
            })
            .overrideTemplate(AnhTaiSanDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AnhTaiSanDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AnhTaiSanService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new AnhTaiSan(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.anhTaiSan).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
