/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ServertdjhipTestModule } from '../../../test.module';
import { LichSuThaoTacHopDongDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/lich-su-thao-tac-hop-dong/lich-su-thao-tac-hop-dong-delete-dialog.component';
import { LichSuThaoTacHopDongService } from '../../../../../../main/webapp/app/entities/lich-su-thao-tac-hop-dong/lich-su-thao-tac-hop-dong.service';

describe('Component Tests', () => {

    describe('LichSuThaoTacHopDong Management Delete Component', () => {
        let comp: LichSuThaoTacHopDongDeleteDialogComponent;
        let fixture: ComponentFixture<LichSuThaoTacHopDongDeleteDialogComponent>;
        let service: LichSuThaoTacHopDongService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServertdjhipTestModule],
                declarations: [LichSuThaoTacHopDongDeleteDialogComponent],
                providers: [
                    LichSuThaoTacHopDongService
                ]
            })
            .overrideTemplate(LichSuThaoTacHopDongDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LichSuThaoTacHopDongDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LichSuThaoTacHopDongService);
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
