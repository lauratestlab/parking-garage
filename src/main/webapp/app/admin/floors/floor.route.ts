import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, ResolveFn, Routes } from '@angular/router';
import { of } from 'rxjs';

import { IFloor } from './floor.model';
import { FloorService } from './service/floor.service';

export const floorManagementResolve: ResolveFn<IFloor | null> = (route: ActivatedRouteSnapshot) => {
  const id = route.paramMap.get('id');
  if (id) {
    return inject(FloorService).find(id);
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
    path: ':id/view',
    loadComponent: () => import('./detail/floor-detail.component'),
    resolve: {
      floor: floorManagementResolve,
    },
  },
  {
    path: 'new',
    loadComponent: () => import('./update/floor-update.component'),
    resolve: {
      floor: floorManagementResolve,
    },
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/floor-update.component'),
    resolve: {
      floor: floorManagementResolve,
    },
  },
];

export default floorRoute;
