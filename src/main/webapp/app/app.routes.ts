import { Routes } from '@angular/router';
// import { DashboardComponent } from './dashboard/dashboard.component';
import { ReservationsComponent } from './reservations/reservations.component';
import { PricingComponent } from './pricing/pricing.component';
import { SpotComponent } from './spot/spot.component';
import SignupComponent from "./account/signup/signup.component";
import LoginComponent from "./login/login.component";
import {DashboardComponent} from "./dashboard/dashboard.component";
import {Authority} from "./config/authority.constants";
import {UserRouteAccessService} from "./core/auth/user-route-access.service";
import { errorRoute } from './layouts/error/error.route';
// import {ConfirmationComponent} from "./confirmation/confirmation.component";

export const routes: Routes = [
    // { path: 'register', component: SignupComponent },
    { path: 'login', component: LoginComponent, title: "login" },
    // { path: 'activate', component: ActivatePageComponent },
    // { path: 'account/activate', component: ConfirmationComponent },
    { path: 'dashboard', component: DashboardComponent, title: "dashboard" },
    { path: 'reservations', component: ReservationsComponent },
    { path: 'pricing', component: PricingComponent },
    // { path: '',   redirectTo: '/dashboard', pathMatch: 'full' },
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
        title: 'Account'
    },
    {
        path: 'admin',
        data: {
            authorities: [Authority.ADMIN],
        },
        canActivate: [UserRouteAccessService],
        loadChildren: () => import('./admin/admin.routes'),
    },
    ...errorRoute,
];

export default routes;
