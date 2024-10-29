import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, ResolveFn, Routes } from '@angular/router';
import { of } from 'rxjs';

import { ICar } from './car.model';
import { CarService } from './service/car.service';

export const userManagementResolve: ResolveFn<ICar | null> = (route: ActivatedRouteSnapshot) => {
  const login = route.paramMap.get('login');
  if (login) {
    return inject(CarService).find(login);
  }
  return of(null);
};

const carRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/car.component'),
    data: {
      defaultSort: 'id,asc',
    },
  },
  {
    path: ':id/view',
    loadComponent: () => import('../car/detail/car-detail.component'),
    resolve: {
      user: userManagementResolve,
    },
  },
  {
    path: 'new',
    loadComponent: () => import('./update/car-update.component'),
    resolve: {
      user: userManagementResolve,
    },
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/car-update.component'),
    resolve: {
      user: userManagementResolve,
    },
  },
];

export default carRoute;
