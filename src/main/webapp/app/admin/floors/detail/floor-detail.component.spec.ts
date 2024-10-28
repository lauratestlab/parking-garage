import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { Authority } from 'app/config/authority.constants';

import FloorDetailComponent from './floor-detail.component';

describe('Floor Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FloorDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./floor-detail.component'),
              resolve: {
                floor: () =>
                  of({
                    id: 123,
                    login: 'floor',
                    firstName: 'first',
                    lastName: 'last',
                    email: 'first@last.com',
                    activated: true,
                    langKey: 'en',
                    authorities: [Authority.USER],
                    createdBy: 'admin',
                  }),
              },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(FloorDetailComponent, '')
      .compileComponents();
  });

  describe('Construct', () => {
    it('Should call load all on construct', async () => {
      // WHEN
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', FloorDetailComponent);

      // THEN
      expect(instance.floor()).toEqual(
        expect.objectContaining({
          id: 123,
          login: 'floor',
          firstName: 'first',
          lastName: 'last',
          email: 'first@last.com',
          activated: true,
          langKey: 'en',
          authorities: [Authority.USER],
          createdBy: 'admin',
        }),
      );
    });
  });
});
