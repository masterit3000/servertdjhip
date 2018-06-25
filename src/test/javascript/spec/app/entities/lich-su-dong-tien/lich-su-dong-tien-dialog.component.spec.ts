/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ServertdjhipTestModule } from '../../../test.module';
import { LichSuDongTienDialogComponent } from '../../../../../../main/webapp/app/entities/lich-su-dong-tien/lich-su-dong-tien-dialog.component';
import { LichSuDongTienService } from '../../../../../../main/webapp/app/entities/lich-su-dong-tien/lich-su-dong-tien.service';
import { LichSuDongTien } from '../../../../../../main/webapp/app/entities/lich-su-dong-tien/lich-su-dong-tien.model';
import { NhanVienService } from '../../../../../../main/webapp/app/entities/nhan-vien';
import { HopDongService } from '../../../../../../main/webapp/app/entities/hop-dong';

describe('Component Tests', () => {

    describe('LichSuDongTien Management Dialog Component', () => {
        let comp: LichSuDongTienDialogComponent;
        let fixture: ComponentFixture<LichSuDongTienDialogComponent>;
        let service: LichSuDongTienService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServertdjhipTestModule],
                declarations: [LichSuDongTienDialogComponent],
                providers: [
                    NhanVienService,
                    HopDongService,
                    LichSuDongTienService
                ]
            })
            .overrideTemplate(LichSuDongTienDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LichSuDongTienDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LichSuDongTienService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new LichSuDongTien(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.lichSuDongTien = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'lichSuDongTienListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new LichSuDongTien();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.lichSuDongTien = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'lichSuDongTienListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
