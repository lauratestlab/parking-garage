import { Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ReservationsComponent } from './reservations/reservations.component';
import { PricingComponent } from './pricing/pricing.component';
import { MembersComponent } from './members/members.component';
import LoginComponent from "./login/login.component";
import {ActivatePageComponent} from "./activate-page/activate-page.component";

const routes: Routes = [
    { path: 'register', loadComponent: () => import('./signup/signup.component') },
    { path: 'login', component: LoginComponent },
    { path: 'activate', component: ActivatePageComponent },
    // { path: 'dashboard', component: DashboardComponent },
    // { path: 'reservations', component: ReservationsComponent },
    // { path: 'pricing', component: PricingComponent },
    // { path: 'members', component: MembersComponent },
    { path: '',   redirectTo: '/register', pathMatch: 'full' }
];

export default routes;
