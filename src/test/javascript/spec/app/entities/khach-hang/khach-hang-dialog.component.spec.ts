/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ServertdjhipTestModule } from '../../../test.module';
import { KhachHangDialogComponent } from '../../../../../../main/webapp/app/entities/khach-hang/khach-hang-dialog.component';
import { KhachHangService } from '../../../../../../main/webapp/app/entities/khach-hang/khach-hang.service';
import { KhachHang } from '../../../../../../main/webapp/app/entities/khach-hang/khach-hang.model';
import { XaService } from '../../../../../../main/webapp/app/entities/xa';
import { CuaHangService } from '../../../../../../main/webapp/app/entities/cua-hang';

describe('Component Tests', () => {

    describe('KhachHang Management Dialog Component', () => {
        let comp: KhachHangDialogComponent;
        let fixture: ComponentFixture<KhachHangDialogComponent>;
        let service: KhachHangService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServertdjhipTestModule],
                declarations: [KhachHangDialogComponent],
                providers: [
                    XaService,
                    CuaHangService,
                    KhachHangService
                ]
            })
            .overrideTemplate(KhachHangDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(KhachHangDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(KhachHangService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new KhachHang(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.khachHang = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'khachHangListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new KhachHang();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.khachHang = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'khachHangListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
