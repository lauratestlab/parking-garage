import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { Authority } from 'app/config/authority.constants';

import UserManagementDetailComponent from './car-detail.component';

describe('User Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserManagementDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./car-detail.component'),
              resolve: {
                user: () =>
                  of({
                    carId: 123,
                    model: 'Sedan',
                    make: 'Toyota',
                    color: 'Red',
                    registration: 'ABC-1234',
                    activated: true,
                    langKey: 'en',
                    authorities: [Authority.USER],
                    createdBy: 'user',
                  }),
              },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(UserManagementDetailComponent, '')
      .compileComponents();
  });

  describe('Construct', () => {
    it('Should call load all on construct', async () => {
      // WHEN
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', UserManagementDetailComponent);

      // THEN
      expect(instance.user()).toEqual(
        expect.objectContaining({
          carId: 123,
          model: 'Sedan',
          make: 'Toyota',
          color: 'Red',
          registration: 'ABC-1234',
          langKey: 'en',
          authorities: [Authority.USER],
          createdBy: 'user',
        }),
      );
    });
  });
});
