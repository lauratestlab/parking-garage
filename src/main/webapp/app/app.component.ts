import { CommonModule } from '@angular/common';
import { registerLocaleData } from '@angular/common';
import dayjs from 'dayjs/esm';
import {Component, inject} from '@angular/core';
import { NgbDatepickerConfig } from '@ng-bootstrap/ng-bootstrap';
import { RouterLink, RouterOutlet, RouterLinkActive } from '@angular/router';
import { AdminSidebarComponent } from "./admin-sidebar/admin-sidebar.component";
import { DashboardComponent } from "./dashboard/dashboard.component";
import { ReservationsComponent } from './reservations/reservations.component';
import {FormsModule} from "@angular/forms";
import LoginComponent from "./login/login.component";
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import locale from '@angular/common/locales/en';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    RouterLink,
    RouterLinkActive,
    CommonModule,
    FormsModule,
    AdminSidebarComponent,
    ReservationsComponent,
    DashboardComponent,
    LoginComponent
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export default class AppComponent {
  currentPage: string = 'dashboard';

  private applicationConfigService = inject(ApplicationConfigService);
  private dpConfig = inject(NgbDatepickerConfig);

  constructor() {
    this.applicationConfigService.setEndpointPrefix(SERVER_API_URL);
    registerLocaleData(locale);
    this.dpConfig.minDate = { year: dayjs().subtract(100, 'year').year(), month: 1, day: 1 };
  }
}
