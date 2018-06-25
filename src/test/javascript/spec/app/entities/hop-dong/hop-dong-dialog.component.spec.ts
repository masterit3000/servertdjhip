/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ServertdjhipTestModule } from '../../../test.module';
import { HopDongDialogComponent } from '../../../../../../main/webapp/app/entities/hop-dong/hop-dong-dialog.component';
import { HopDongService } from '../../../../../../main/webapp/app/entities/hop-dong/hop-dong.service';
import { HopDong } from '../../../../../../main/webapp/app/entities/hop-dong/hop-dong.model';
import { KhachHangService } from '../../../../../../main/webapp/app/entities/khach-hang';
import { CuaHangService } from '../../../../../../main/webapp/app/entities/cua-hang';
import { NhanVienService } from '../../../../../../main/webapp/app/entities/nhan-vien';

describe('Component Tests', () => {

    describe('HopDong Management Dialog Component', () => {
        let comp: HopDongDialogComponent;
        let fixture: ComponentFixture<HopDongDialogComponent>;
        let service: HopDongService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServertdjhipTestModule],
                declarations: [HopDongDialogComponent],
                providers: [
                    KhachHangService,
                    CuaHangService,
                    NhanVienService,
                    HopDongService
                ]
            })
            .overrideTemplate(HopDongDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HopDongDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HopDongService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new HopDong(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.hopDong = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'hopDongListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new HopDong();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.hopDong = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'hopDongListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
