import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import FloorResolve from './route/floor-routing-resolve.service';

const floorRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/floor.component').then(m => m.FloorComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/floor-detail.component').then(m => m.FloorDetailComponent),
    resolve: {
      floor: FloorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/floor-update.component').then(m => m.FloorUpdateComponent),
    resolve: {
      floor: FloorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/floor-update.component').then(m => m.FloorUpdateComponent),
    resolve: {
      floor: FloorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default floorRoute;
