import { ComponentFixture, TestBed, fakeAsync, inject, tick, waitForAsync } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Authority } from 'app/config/authority.constants';
import { FloorService } from '../service/floor.service';
import { Floor } from '../floor.model';

import FloorUpdateComponent from './floor-update.component';

describe('Floor Management Update Component', () => {
  let comp: FloorUpdateComponent;
  let fixture: ComponentFixture<FloorUpdateComponent>;
  let service: FloorService;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [FloorUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({ floor: new Floor(123, 'floor', 'first', 'last', 'first@last.com', true, 'en', [Authority.USER], 'admin') }),
          },
        },
      ],
    })
      .overrideTemplate(FloorUpdateComponent, '')
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FloorUpdateComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(FloorService);
  });

  describe('OnInit', () => {
    it('Should load authorities and language on init', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'authorities').mockReturnValue(of(['USER']));

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(service.authorities).toHaveBeenCalled();
        expect(comp.authorities()).toEqual(['USER']);
      }),
    ));
  });

  describe('save', () => {
    it('Should call update service on save for existing floor', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        const entity = { id: 123 };
        jest.spyOn(service, 'update').mockReturnValue(of(entity));
        comp.editForm.patchValue(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(expect.objectContaining(entity));
        expect(comp.isSaving()).toEqual(false);
      }),
    ));

    it('Should call create service on save for new floor', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        const entity = { login: 'foo' } as Floor;
        jest.spyOn(service, 'create').mockReturnValue(of(entity));
        comp.editForm.patchValue(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(comp.editForm.getRawValue().id).toBeNull();
        expect(service.create).toHaveBeenCalledWith(expect.objectContaining(entity));
        expect(comp.isSaving()).toEqual(false);
      }),
    ));
  });
});
