jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, fakeAsync, inject, tick, waitForAsync } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';

import { SpotService } from '../service/spot.service';

import SpotManagementDeleteDialogComponent from './spot-delete-dialog.component';

describe('Spot Management Delete Component', () => {
  let comp: SpotManagementDeleteDialogComponent;
  let fixture: ComponentFixture<SpotManagementDeleteDialogComponent>;
  let service: SpotService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [SpotManagementDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(SpotManagementDeleteDialogComponent, '')
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
        comp.confirmDelete('spot');
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith('spot');
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      }),
    ));
  });
});
