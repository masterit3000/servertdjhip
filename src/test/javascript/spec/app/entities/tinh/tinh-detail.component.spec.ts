/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { ServertdjhipTestModule } from '../../../test.module';
import { TinhDetailComponent } from '../../../../../../main/webapp/app/entities/tinh/tinh-detail.component';
import { TinhService } from '../../../../../../main/webapp/app/entities/tinh/tinh.service';
import { Tinh } from '../../../../../../main/webapp/app/entities/tinh/tinh.model';

describe('Component Tests', () => {

    describe('Tinh Management Detail Component', () => {
        let comp: TinhDetailComponent;
        let fixture: ComponentFixture<TinhDetailComponent>;
        let service: TinhService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServertdjhipTestModule],
                declarations: [TinhDetailComponent],
                providers: [
                    TinhService
                ]
            })
            .overrideTemplate(TinhDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TinhDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TinhService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Tinh(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.tinh).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
