/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ServertdjhipTestModule } from '../../../test.module';
import { AnhKhachHangDialogComponent } from '../../../../../../main/webapp/app/entities/anh-khach-hang/anh-khach-hang-dialog.component';
import { AnhKhachHangService } from '../../../../../../main/webapp/app/entities/anh-khach-hang/anh-khach-hang.service';
import { AnhKhachHang } from '../../../../../../main/webapp/app/entities/anh-khach-hang/anh-khach-hang.model';
import { KhachHangService } from '../../../../../../main/webapp/app/entities/khach-hang';

describe('Component Tests', () => {

    describe('AnhKhachHang Management Dialog Component', () => {
        let comp: AnhKhachHangDialogComponent;
        let fixture: ComponentFixture<AnhKhachHangDialogComponent>;
        let service: AnhKhachHangService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServertdjhipTestModule],
                declarations: [AnhKhachHangDialogComponent],
                providers: [
                    KhachHangService,
                    AnhKhachHangService
                ]
            })
            .overrideTemplate(AnhKhachHangDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AnhKhachHangDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AnhKhachHangService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new AnhKhachHang(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.anhKhachHang = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'anhKhachHangListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new AnhKhachHang();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.anhKhachHang = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'anhKhachHangListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
