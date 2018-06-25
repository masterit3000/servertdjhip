/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ServertdjhipTestModule } from '../../../test.module';
import { HopDongDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/hop-dong/hop-dong-delete-dialog.component';
import { HopDongService } from '../../../../../../main/webapp/app/entities/hop-dong/hop-dong.service';

describe('Component Tests', () => {

    describe('HopDong Management Delete Component', () => {
        let comp: HopDongDeleteDialogComponent;
        let fixture: ComponentFixture<HopDongDeleteDialogComponent>;
        let service: HopDongService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServertdjhipTestModule],
                declarations: [HopDongDeleteDialogComponent],
                providers: [
                    HopDongService
                ]
            })
            .overrideTemplate(HopDongDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HopDongDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HopDongService);
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
