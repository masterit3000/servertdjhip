/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { ServertdjhipTestModule } from '../../../test.module';
import { AnhKhachHangDetailComponent } from '../../../../../../main/webapp/app/entities/anh-khach-hang/anh-khach-hang-detail.component';
import { AnhKhachHangService } from '../../../../../../main/webapp/app/entities/anh-khach-hang/anh-khach-hang.service';
import { AnhKhachHang } from '../../../../../../main/webapp/app/entities/anh-khach-hang/anh-khach-hang.model';

describe('Component Tests', () => {

    describe('AnhKhachHang Management Detail Component', () => {
        let comp: AnhKhachHangDetailComponent;
        let fixture: ComponentFixture<AnhKhachHangDetailComponent>;
        let service: AnhKhachHangService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServertdjhipTestModule],
                declarations: [AnhKhachHangDetailComponent],
                providers: [
                    AnhKhachHangService
                ]
            })
            .overrideTemplate(AnhKhachHangDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AnhKhachHangDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AnhKhachHangService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new AnhKhachHang(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.anhKhachHang).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
