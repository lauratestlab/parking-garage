import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { FloorService } from '../service/floor.service';
import { IFloor } from '../floor.model';
import { FloorFormService } from './floor-form.service';

import { FloorUpdateComponent } from './floor-update.component';

describe('Floor Management Update Component', () => {
  let comp: FloorUpdateComponent;
  let fixture: ComponentFixture<FloorUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let floorFormService: FloorFormService;
  let floorService: FloorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [FloorUpdateComponent],
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
      .overrideTemplate(FloorUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FloorUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    floorFormService = TestBed.inject(FloorFormService);
    floorService = TestBed.inject(FloorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const floor: IFloor = { id: 456 };

      activatedRoute.data = of({ floor });
      comp.ngOnInit();

      expect(comp.floor).toEqual(floor);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFloor>>();
      const floor = { id: 123 };
      jest.spyOn(floorFormService, 'getFloor').mockReturnValue(floor);
      jest.spyOn(floorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ floor });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: floor }));
      saveSubject.complete();

      // THEN
      expect(floorFormService.getFloor).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(floorService.update).toHaveBeenCalledWith(expect.objectContaining(floor));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFloor>>();
      const floor = { id: 123 };
      jest.spyOn(floorFormService, 'getFloor').mockReturnValue({ id: null });
      jest.spyOn(floorService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ floor: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: floor }));
      saveSubject.complete();

      // THEN
      expect(floorFormService.getFloor).toHaveBeenCalled();
      expect(floorService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFloor>>();
      const floor = { id: 123 };
      jest.spyOn(floorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ floor });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(floorService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
