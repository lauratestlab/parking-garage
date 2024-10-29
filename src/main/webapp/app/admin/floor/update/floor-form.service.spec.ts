import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../floor.test-samples';

import { FloorFormService } from './floor-form.service';

describe('Floor Form Service', () => {
  let service: FloorFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FloorFormService);
  });

  describe('Service methods', () => {
    describe('createFloorFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFloorFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
          }),
        );
      });

      it('passing IFloor should create a new form with FormGroup', () => {
        const formGroup = service.createFloorFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
          }),
        );
      });
    });

    describe('getFloor', () => {
      it('should return NewFloor for default Floor initial value', () => {
        const formGroup = service.createFloorFormGroup(sampleWithNewData);

        const floor = service.getFloor(formGroup) as any;

        expect(floor).toMatchObject(sampleWithNewData);
      });

      it('should return NewFloor for empty Floor initial value', () => {
        const formGroup = service.createFloorFormGroup();

        const floor = service.getFloor(formGroup) as any;

        expect(floor).toMatchObject({});
      });

      it('should return IFloor', () => {
        const formGroup = service.createFloorFormGroup(sampleWithRequiredData);

        const floor = service.getFloor(formGroup) as any;

        expect(floor).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFloor should not enable id FormControl', () => {
        const formGroup = service.createFloorFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFloor should disable id FormControl', () => {
        const formGroup = service.createFloorFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
