import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'user-management',
    loadChildren: () => import('./user-management/user-management.route'),
    title: 'userManagement.home.title',
  },
  {
    path: 'floors',
    loadChildren: () => import('./floors/floor.route'),
    title: 'Floors',
  },
  {
    path: 'spots',
    loadChildren: () => import('./spot/spot.route'),
    title: 'Spots',
  },
  {
    path: 'docs',
    loadComponent: () => import('./docs/docs.component'),
    title: 'global.menu.admin.apidocs',
  }
];

export default routes;
