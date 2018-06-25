/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ServertdjhipTestModule } from '../../../test.module';
import { LichSuThaoTacHopDongDialogComponent } from '../../../../../../main/webapp/app/entities/lich-su-thao-tac-hop-dong/lich-su-thao-tac-hop-dong-dialog.component';
import { LichSuThaoTacHopDongService } from '../../../../../../main/webapp/app/entities/lich-su-thao-tac-hop-dong/lich-su-thao-tac-hop-dong.service';
import { LichSuThaoTacHopDong } from '../../../../../../main/webapp/app/entities/lich-su-thao-tac-hop-dong/lich-su-thao-tac-hop-dong.model';
import { NhanVienService } from '../../../../../../main/webapp/app/entities/nhan-vien';
import { HopDongService } from '../../../../../../main/webapp/app/entities/hop-dong';

describe('Component Tests', () => {

    describe('LichSuThaoTacHopDong Management Dialog Component', () => {
        let comp: LichSuThaoTacHopDongDialogComponent;
        let fixture: ComponentFixture<LichSuThaoTacHopDongDialogComponent>;
        let service: LichSuThaoTacHopDongService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServertdjhipTestModule],
                declarations: [LichSuThaoTacHopDongDialogComponent],
                providers: [
                    NhanVienService,
                    HopDongService,
                    LichSuThaoTacHopDongService
                ]
            })
            .overrideTemplate(LichSuThaoTacHopDongDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LichSuThaoTacHopDongDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LichSuThaoTacHopDongService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new LichSuThaoTacHopDong(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.lichSuThaoTacHopDong = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'lichSuThaoTacHopDongListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new LichSuThaoTacHopDong();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.lichSuThaoTacHopDong = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'lichSuThaoTacHopDongListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
