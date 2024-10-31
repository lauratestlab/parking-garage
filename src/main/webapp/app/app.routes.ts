import {Routes} from '@angular/router';
import {ReservationsComponent} from './reservations/reservations.component';
import {PricingComponent} from './pricing/pricing.component';
import {HomeComponent} from './home/home.component';
import LoginComponent from "./login/login.component";
import {DashboardComponent} from "./dashboard/dashboard.component";
import {Authority} from "./config/authority.constants";
import {UserRouteAccessService} from "./core/auth/user-route-access.service";
import {errorRoute} from './layouts/error/error.route';

export const routes: Routes = [
    { path: 'login', component: LoginComponent, title: "login" },
    { path: 'dashboard', component: DashboardComponent, title: "dashboard" },
    { path: 'reservations', component: ReservationsComponent },
    { path: 'pricing', component: PricingComponent },
    { path: 'home', component: HomeComponent, },
    { path: '',   redirectTo: 'home', pathMatch: 'full' },

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
    {
        path: 'car',
        loadChildren: () => import('./car/car.routes'),
        title: 'cars',
    },
    {
        path: 'payment-method',
        loadChildren: () => import('./payment-method/payment-method.routes'),
        title: 'Payment methods',
    },
    ...errorRoute,
];

export default routes;
