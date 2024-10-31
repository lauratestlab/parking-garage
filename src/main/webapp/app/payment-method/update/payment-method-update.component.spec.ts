import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/service/user.service';
import { PaymentMethodService } from '../service/payment-method.service';
import { IPaymentMethod } from '../payment-method.model';
import { PaymentMethodFormService } from './payment-method-form.service';

import { PaymentMethodUpdateComponent } from './payment-method-update.component';

describe('PaymentMethod Management Update Component', () => {
  let comp: PaymentMethodUpdateComponent;
  let fixture: ComponentFixture<PaymentMethodUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let paymentMethodFormService: PaymentMethodFormService;
  let paymentMethodService: PaymentMethodService;
  let userService: UserService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [PaymentMethodUpdateComponent],
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
      .overrideTemplate(PaymentMethodUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PaymentMethodUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    paymentMethodFormService = TestBed.inject(PaymentMethodFormService);
    paymentMethodService = TestBed.inject(PaymentMethodService);
    userService = TestBed.inject(UserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const paymentMethod: IPaymentMethod = { id: 456 };
      const user: IUser = { id: 27689 };
      paymentMethod.user = user;

      const userCollection: IUser[] = [{ id: 17731 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [user];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ paymentMethod });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining),
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const paymentMethod: IPaymentMethod = { id: 456 };
      const user: IUser = { id: 29990 };
      paymentMethod.user = user;

      activatedRoute.data = of({ paymentMethod });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContain(user);
      expect(comp.paymentMethod).toEqual(paymentMethod);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPaymentMethod>>();
      const paymentMethod = { id: 123 };
      jest.spyOn(paymentMethodFormService, 'getPaymentMethod').mockReturnValue(paymentMethod);
      jest.spyOn(paymentMethodService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paymentMethod });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: paymentMethod }));
      saveSubject.complete();

      // THEN
      expect(paymentMethodFormService.getPaymentMethod).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(paymentMethodService.update).toHaveBeenCalledWith(expect.objectContaining(paymentMethod));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPaymentMethod>>();
      const paymentMethod = { id: 123 };
      jest.spyOn(paymentMethodFormService, 'getPaymentMethod').mockReturnValue({ id: null });
      jest.spyOn(paymentMethodService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paymentMethod: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: paymentMethod }));
      saveSubject.complete();

      // THEN
      expect(paymentMethodFormService.getPaymentMethod).toHaveBeenCalled();
      expect(paymentMethodService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPaymentMethod>>();
      const paymentMethod = { id: 123 };
      jest.spyOn(paymentMethodService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paymentMethod });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(paymentMethodService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareUser', () => {
      it('Should forward to userService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(userService, 'compareUser');
        comp.compareUser(entity, entity2);
        expect(userService.compareUser).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
