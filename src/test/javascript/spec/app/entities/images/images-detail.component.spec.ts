/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { ServertdjhipTestModule } from '../../../test.module';
import { ImagesDetailComponent } from '../../../../../../main/webapp/app/entities/images/images-detail.component';
import { ImagesService } from '../../../../../../main/webapp/app/entities/images/images.service';
import { Images } from '../../../../../../main/webapp/app/entities/images/images.model';

describe('Component Tests', () => {

    describe('Images Management Detail Component', () => {
        let comp: ImagesDetailComponent;
        let fixture: ComponentFixture<ImagesDetailComponent>;
        let service: ImagesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServertdjhipTestModule],
                declarations: [ImagesDetailComponent],
                providers: [
                    ImagesService
                ]
            })
            .overrideTemplate(ImagesDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ImagesDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ImagesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Images(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.images).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
