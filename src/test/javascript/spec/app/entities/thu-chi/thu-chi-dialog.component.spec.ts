/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ServertdjhipTestModule } from '../../../test.module';
import { ThuChiDialogComponent } from '../../../../../../main/webapp/app/entities/thu-chi/thu-chi-dialog.component';
import { ThuChiService } from '../../../../../../main/webapp/app/entities/thu-chi/thu-chi.service';
import { ThuChi } from '../../../../../../main/webapp/app/entities/thu-chi/thu-chi.model';
import { CuaHangService } from '../../../../../../main/webapp/app/entities/cua-hang';
import { NhanVienService } from '../../../../../../main/webapp/app/entities/nhan-vien';

describe('Component Tests', () => {

    describe('ThuChi Management Dialog Component', () => {
        let comp: ThuChiDialogComponent;
        let fixture: ComponentFixture<ThuChiDialogComponent>;
        let service: ThuChiService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServertdjhipTestModule],
                declarations: [ThuChiDialogComponent],
                providers: [
                    CuaHangService,
                    NhanVienService,
                    ThuChiService
                ]
            })
            .overrideTemplate(ThuChiDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ThuChiDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ThuChiService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ThuChi(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.thuChi = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'thuChiListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ThuChi();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.thuChi = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'thuChiListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
