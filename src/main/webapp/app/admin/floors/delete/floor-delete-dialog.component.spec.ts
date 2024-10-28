jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, fakeAsync, inject, tick, waitForAsync } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';

import { FloorService } from '../service/floor.service';

import FloorManagementDeleteDialogComponent from './floor-delete-dialog.component';

describe('Floor Management Delete Component', () => {
  let comp: FloorManagementDeleteDialogComponent;
  let fixture: ComponentFixture<FloorManagementDeleteDialogComponent>;
  let service: FloorService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [FloorManagementDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(FloorManagementDeleteDialogComponent, '')
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FloorManagementDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(FloorService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of({}));

        // WHEN
        comp.confirmDelete('floor');
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith('floor');
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      }),
    ));
  });
});
