import { Routes } from '@angular/router';
// import { DashboardComponent } from './dashboard/dashboard.component';
import { ReservationsComponent } from './reservations/reservations.component';
import { PricingComponent } from './pricing/pricing.component';
import { SpotComponent } from './spot/spot.component';
import { FloorComponent } from './floor/floor.component';
import { MembersComponent } from './members/members.component';
import LoginComponent from "./login/login.component";
import {DashboardComponent} from "./dashboard/dashboard.component";

const routes: Routes = [
    { path: 'register', loadComponent: () => import('./account/signup/signup.component') },
    { path: 'login', component: LoginComponent },
    { path: 'dashboard', component: DashboardComponent },
    { path: 'reservations', component: ReservationsComponent },
    { path: 'pricing', component: PricingComponent },
    { path: 'members', component: MembersComponent },
    { path: 'spot', component: SpotComponent },
    { path: 'floor', component: FloorComponent },
    { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
    {
        path: 'account',
        loadChildren: () => import('./account/account.route'),
    },
    
];

export default routes;
