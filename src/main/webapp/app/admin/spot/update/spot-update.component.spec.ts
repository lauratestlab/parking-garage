import { ComponentFixture, TestBed, fakeAsync, inject, tick, waitForAsync } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Authority } from 'app/config/authority.constants';
import { SpotService } from '../service/spot.service';
import { Spot } from '../spot.model';

import SpotUpdateComponent from './spot-update.component';

describe('Spot Management Update Component', () => {
  let comp: SpotUpdateComponent;
  let fixture: ComponentFixture<SpotUpdateComponent>;
  let service: SpotService;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [SpotUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({ spot: new Spot(123, 'spot', 'first', 'last', 'first@last.com', true, 'en', [Authority.USER], 'admin') }),
          },
        },
      ],
    })
      .overrideTemplate(SpotUpdateComponent, '')
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SpotUpdateComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(SpotService);
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
    it('Should call update service on save for existing spot', inject(
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

    it('Should call create service on save for new spot', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        const entity = { login: 'foo' } as Spot;
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
