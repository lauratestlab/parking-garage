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

export const routes: Routes = [
    { path: 'register', component: SignupComponent },
    { path: 'login', component: LoginComponent },
    { path: 'activate', component: ActivatePageComponent },
    // { path: 'dashboard', component: DashboardComponent },
    // { path: 'reservations', component: ReservationsComponent },
    { path: 'pricing', component: PricingComponent },
    // { path: 'members', component: MembersComponent },
    { path: 'spot', component: SpotComponent },
    { path: 'floor', component: FloorComponent },
    { path: '', redirectTo: '/register', pathMatch: 'full' }
    
];
