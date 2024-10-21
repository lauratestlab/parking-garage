import { CommonModule } from '@angular/common';
import {Component, inject} from '@angular/core';
import { RouterLink, RouterOutlet, RouterLinkActive } from '@angular/router';
import { AdminSidebarComponent } from "./admin-sidebar/admin-sidebar.component";
import { DashboardComponent } from "./dashboard/dashboard.component";
import { ReservationsComponent } from './reservations/reservations.component';
import LoginComponent from "./login/login.component";
import { ApplicationConfigService } from 'app/core/config/application-config.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    RouterLink,
    RouterLinkActive,
    CommonModule,
    AdminSidebarComponent,
    ReservationsComponent,
    DashboardComponent,
    LoginComponent
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  currentPage: string = 'dashboard';

  private applicationConfigService = inject(ApplicationConfigService);

  constructor() {
    // this.applicationConfigService.setEndpointPrefix('http://localhost:8080');
  }
}
