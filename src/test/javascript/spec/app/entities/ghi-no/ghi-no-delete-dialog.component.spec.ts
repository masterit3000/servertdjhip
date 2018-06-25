/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ServertdjhipTestModule } from '../../../test.module';
import { GhiNoDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/ghi-no/ghi-no-delete-dialog.component';
import { GhiNoService } from '../../../../../../main/webapp/app/entities/ghi-no/ghi-no.service';

describe('Component Tests', () => {

    describe('GhiNo Management Delete Component', () => {
        let comp: GhiNoDeleteDialogComponent;
        let fixture: ComponentFixture<GhiNoDeleteDialogComponent>;
        let service: GhiNoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServertdjhipTestModule],
                declarations: [GhiNoDeleteDialogComponent],
                providers: [
                    GhiNoService
                ]
            })
            .overrideTemplate(GhiNoDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GhiNoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GhiNoService);
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
