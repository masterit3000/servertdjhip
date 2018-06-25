/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ServertdjhipTestModule } from '../../../test.module';
import { AnhKhachHangComponent } from '../../../../../../main/webapp/app/entities/anh-khach-hang/anh-khach-hang.component';
import { AnhKhachHangService } from '../../../../../../main/webapp/app/entities/anh-khach-hang/anh-khach-hang.service';
import { AnhKhachHang } from '../../../../../../main/webapp/app/entities/anh-khach-hang/anh-khach-hang.model';

describe('Component Tests', () => {

    describe('AnhKhachHang Management Component', () => {
        let comp: AnhKhachHangComponent;
        let fixture: ComponentFixture<AnhKhachHangComponent>;
        let service: AnhKhachHangService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServertdjhipTestModule],
                declarations: [AnhKhachHangComponent],
                providers: [
                    AnhKhachHangService
                ]
            })
            .overrideTemplate(AnhKhachHangComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AnhKhachHangComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AnhKhachHangService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new AnhKhachHang(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.anhKhachHangs[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
