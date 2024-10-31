import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'user-management',
    loadChildren: () => import('./user-management/user-management.route'),
    title: 'userManagement.home.title',
  },
  {
    path: 'floor',
    loadChildren: () => import('./floor/floor.routes'),
    title: 'Floors',
  },

  {
    path: 'spot',
    loadChildren: () => import('./spot/spot.routes'),
    title: 'Spots',
  }
];

export default routes;
