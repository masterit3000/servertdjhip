/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ServertdjhipTestModule } from '../../../test.module';
import { NhatKyDialogComponent } from '../../../../../../main/webapp/app/entities/nhat-ky/nhat-ky-dialog.component';
import { NhatKyService } from '../../../../../../main/webapp/app/entities/nhat-ky/nhat-ky.service';
import { NhatKy } from '../../../../../../main/webapp/app/entities/nhat-ky/nhat-ky.model';
import { CuaHangService } from '../../../../../../main/webapp/app/entities/cua-hang';
import { NhanVienService } from '../../../../../../main/webapp/app/entities/nhan-vien';

describe('Component Tests', () => {

    describe('NhatKy Management Dialog Component', () => {
        let comp: NhatKyDialogComponent;
        let fixture: ComponentFixture<NhatKyDialogComponent>;
        let service: NhatKyService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServertdjhipTestModule],
                declarations: [NhatKyDialogComponent],
                providers: [
                    CuaHangService,
                    NhanVienService,
                    NhatKyService
                ]
            })
            .overrideTemplate(NhatKyDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NhatKyDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NhatKyService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new NhatKy(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.nhatKy = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'nhatKyListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new NhatKy();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.nhatKy = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'nhatKyListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
