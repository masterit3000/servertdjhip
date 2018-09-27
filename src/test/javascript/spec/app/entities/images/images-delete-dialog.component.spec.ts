/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ServertdjhipTestModule } from '../../../test.module';
import { ImagesDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/images/images-delete-dialog.component';
import { ImagesService } from '../../../../../../main/webapp/app/entities/images/images.service';

describe('Component Tests', () => {

    describe('Images Management Delete Component', () => {
        let comp: ImagesDeleteDialogComponent;
        let fixture: ComponentFixture<ImagesDeleteDialogComponent>;
        let service: ImagesService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServertdjhipTestModule],
                declarations: [ImagesDeleteDialogComponent],
                providers: [
                    ImagesService
                ]
            })
            .overrideTemplate(ImagesDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ImagesDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ImagesService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
