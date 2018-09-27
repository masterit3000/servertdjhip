/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ServertdjhipTestModule } from '../../../test.module';
import { ImagesComponent } from '../../../../../../main/webapp/app/entities/images/images.component';
import { ImagesService } from '../../../../../../main/webapp/app/entities/images/images.service';
import { Images } from '../../../../../../main/webapp/app/entities/images/images.model';

describe('Component Tests', () => {

    describe('Images Management Component', () => {
        let comp: ImagesComponent;
        let fixture: ComponentFixture<ImagesComponent>;
        let service: ImagesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServertdjhipTestModule],
                declarations: [ImagesComponent],
                providers: [
                    ImagesService
                ]
            })
            .overrideTemplate(ImagesComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ImagesComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ImagesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Images(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.images[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
