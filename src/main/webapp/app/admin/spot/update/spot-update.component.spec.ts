import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IFloor } from 'app/admin/floor/floor.model';
import { FloorService } from 'app/admin/floor/service/floor.service';
import { SpotService } from '../service/spot.service';
import { ISpot } from '../spot.model';
import { SpotFormService } from './spot-form.service';

import { SpotUpdateComponent } from './spot-update.component';

describe('Spot Management Update Component', () => {
  let comp: SpotUpdateComponent;
  let fixture: ComponentFixture<SpotUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let spotFormService: SpotFormService;
  let spotService: SpotService;
  let floorService: FloorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [SpotUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(SpotUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SpotUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    spotFormService = TestBed.inject(SpotFormService);
    spotService = TestBed.inject(SpotService);
    floorService = TestBed.inject(FloorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Floor query and add missing value', () => {
      const spot: ISpot = { id: 456 };
      const floor: IFloor = { id: 2572 };
      spot.floor = floor;

      const floorCollection: IFloor[] = [{ id: 26237 }];
      jest.spyOn(floorService, 'query').mockReturnValue(of(new HttpResponse({ body: floorCollection })));
      const additionalFloors = [floor];
      const expectedCollection: IFloor[] = [...additionalFloors, ...floorCollection];
      jest.spyOn(floorService, 'addFloorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ spot });
      comp.ngOnInit();

      expect(floorService.query).toHaveBeenCalled();
      expect(floorService.addFloorToCollectionIfMissing).toHaveBeenCalledWith(
        floorCollection,
        ...additionalFloors.map(expect.objectContaining),
      );
      expect(comp.floorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const spot: ISpot = { id: 456 };
      const floor: IFloor = { id: 28514 };
      spot.floor = floor;

      activatedRoute.data = of({ spot });
      comp.ngOnInit();

      expect(comp.floorsSharedCollection).toContain(floor);
      expect(comp.spot).toEqual(spot);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISpot>>();
      const spot = { id: 123 };
      jest.spyOn(spotFormService, 'getSpot').mockReturnValue(spot);
      jest.spyOn(spotService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ spot });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: spot }));
      saveSubject.complete();

      // THEN
      expect(spotFormService.getSpot).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(spotService.update).toHaveBeenCalledWith(expect.objectContaining(spot));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISpot>>();
      const spot = { id: 123 };
      jest.spyOn(spotFormService, 'getSpot').mockReturnValue({ id: null });
      jest.spyOn(spotService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ spot: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: spot }));
      saveSubject.complete();

      // THEN
      expect(spotFormService.getSpot).toHaveBeenCalled();
      expect(spotService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISpot>>();
      const spot = { id: 123 };
      jest.spyOn(spotService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ spot });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(spotService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareFloor', () => {
      it('Should forward to floorService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(floorService, 'compareFloor');
        comp.compareFloor(entity, entity2);
        expect(floorService.compareFloor).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
