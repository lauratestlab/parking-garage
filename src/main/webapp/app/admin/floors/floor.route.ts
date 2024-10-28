import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, ResolveFn, Routes } from '@angular/router';
import { of } from 'rxjs';

import { IFloor } from './floor.model';
import { FloorService } from './service/floor.service';

export const userManagementResolve: ResolveFn<IFloor | null> = (route: ActivatedRouteSnapshot) => {
  const login = route.paramMap.get('login');
  if (login) {
    return inject(FloorService).find(login);
  }
  return of(null);
};

const floorRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/floor.component'),
    data: {
      defaultSort: 'id,asc',
    },
  },
  {
    path: ':login/view',
    loadComponent: () => import('./detail/floor-detail.component'),
    resolve: {
      user: userManagementResolve,
    },
  },
  {
    path: 'new',
    loadComponent: () => import('./update/floor-update.component'),
    resolve: {
      user: userManagementResolve,
    },
  },
  {
    path: ':login/edit',
    loadComponent: () => import('./update/floor-update.component'),
    resolve: {
      user: userManagementResolve,
    },
  },
];

export default floorRoute;
