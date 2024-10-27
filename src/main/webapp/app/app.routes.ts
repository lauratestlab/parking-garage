import { Routes } from '@angular/router';
// import { DashboardComponent } from './dashboard/dashboard.component';
import { ReservationsComponent } from './reservations/reservations.component';
import { PricingComponent } from './pricing/pricing.component';
import { SpotComponent } from './spot/spot.component';
import { FloorComponent } from './floor/floor.component';
import { MembersComponent } from './members/members.component';
import {SignupComponent} from "./signup/signup.component";
import LoginComponent from "./login/login.component";
import {ActivatePageComponent} from "./activate-page/activate-page.component";
import {DashboardComponent} from "./dashboard/dashboard.component";
// import {ConfirmationComponent} from "./confirmation/confirmation.component";

export const routes: Routes = [
    // { path: 'register', component: SignupComponent },
    // { path: 'login', component: LoginComponent },
    // { path: 'activate', component: ActivatePageComponent },
    // { path: 'account/activate', component: ConfirmationComponent },
    { path: 'dashboard', component: DashboardComponent },
    { path: 'reservations', component: ReservationsComponent },
    { path: 'pricing', component: PricingComponent },
    { path: 'members', component: MembersComponent },
    { path: '',   redirectTo: '/dashboard', pathMatch: 'full' },
    // { path: 'register', component: SignupComponent },
    // { path: 'login', component: LoginComponent },
    // { path: 'activate', component: ActivatePageComponent },
    // { path: 'dashboard', component: DashboardComponent },
    // { path: 'reservations', component: ReservationsComponent },
    // { path: 'pricing', component: PricingComponent },
    // { path: 'members', component: MembersComponent },

    {
        path: 'account',
        loadChildren: () => import('./account/account.route'),
    },
];

export default routes;
