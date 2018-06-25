/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ServertdjhipTestModule } from '../../../test.module';
import { GhiNoDialogComponent } from '../../../../../../main/webapp/app/entities/ghi-no/ghi-no-dialog.component';
import { GhiNoService } from '../../../../../../main/webapp/app/entities/ghi-no/ghi-no.service';
import { GhiNo } from '../../../../../../main/webapp/app/entities/ghi-no/ghi-no.model';
import { NhanVienService } from '../../../../../../main/webapp/app/entities/nhan-vien';
import { HopDongService } from '../../../../../../main/webapp/app/entities/hop-dong';

describe('Component Tests', () => {

    describe('GhiNo Management Dialog Component', () => {
        let comp: GhiNoDialogComponent;
        let fixture: ComponentFixture<GhiNoDialogComponent>;
        let service: GhiNoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServertdjhipTestModule],
                declarations: [GhiNoDialogComponent],
                providers: [
                    NhanVienService,
                    HopDongService,
                    GhiNoService
                ]
            })
            .overrideTemplate(GhiNoDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GhiNoDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GhiNoService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new GhiNo(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.ghiNo = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'ghiNoListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new GhiNo();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.ghiNo = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'ghiNoListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
