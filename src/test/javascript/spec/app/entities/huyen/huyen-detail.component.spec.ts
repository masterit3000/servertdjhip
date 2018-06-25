/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { ServertdjhipTestModule } from '../../../test.module';
import { HuyenDetailComponent } from '../../../../../../main/webapp/app/entities/huyen/huyen-detail.component';
import { HuyenService } from '../../../../../../main/webapp/app/entities/huyen/huyen.service';
import { Huyen } from '../../../../../../main/webapp/app/entities/huyen/huyen.model';

describe('Component Tests', () => {

    describe('Huyen Management Detail Component', () => {
        let comp: HuyenDetailComponent;
        let fixture: ComponentFixture<HuyenDetailComponent>;
        let service: HuyenService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServertdjhipTestModule],
                declarations: [HuyenDetailComponent],
                providers: [
                    HuyenService
                ]
            })
            .overrideTemplate(HuyenDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HuyenDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HuyenService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Huyen(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.huyen).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
