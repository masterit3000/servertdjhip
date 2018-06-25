/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ServertdjhipTestModule } from '../../../test.module';
import { LichSuDongTienDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/lich-su-dong-tien/lich-su-dong-tien-delete-dialog.component';
import { LichSuDongTienService } from '../../../../../../main/webapp/app/entities/lich-su-dong-tien/lich-su-dong-tien.service';

describe('Component Tests', () => {

    describe('LichSuDongTien Management Delete Component', () => {
        let comp: LichSuDongTienDeleteDialogComponent;
        let fixture: ComponentFixture<LichSuDongTienDeleteDialogComponent>;
        let service: LichSuDongTienService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServertdjhipTestModule],
                declarations: [LichSuDongTienDeleteDialogComponent],
                providers: [
                    LichSuDongTienService
                ]
            })
            .overrideTemplate(LichSuDongTienDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LichSuDongTienDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LichSuDongTienService);
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
