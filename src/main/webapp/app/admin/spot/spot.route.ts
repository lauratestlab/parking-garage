import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, ResolveFn, Routes } from '@angular/router';
import { of } from 'rxjs';

import { ISpot } from './spot.model';
import { SpotService } from './service/spot.service';

export const spotManagementResolve: ResolveFn<ISpot | null> = (route: ActivatedRouteSnapshot) => {
  const id = route.paramMap.get('id');
  if (id) {
    return inject(SpotService).find(id);
  }
  return of(null);
};

const spotRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/spot.component'),
    data: {
      defaultSort: 'id,asc',
    },
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/spot-detail.component'),
    resolve: {
      spot: spotManagementResolve,
    },
  },
  {
    path: 'new',
    loadComponent: () => import('./update/spot-update.component'),
    resolve: {
      spot: spotManagementResolve,
    },
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/spot-update.component'),
    resolve: {
      spot: spotManagementResolve,
    },
  },
];

export default spotRoute;
