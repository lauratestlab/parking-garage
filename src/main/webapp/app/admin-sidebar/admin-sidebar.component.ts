import { CommonModule } from '@angular/common';
import {Component, inject} from '@angular/core';
import {Router, RouterLink, RouterLinkActive} from "@angular/router";
import {LoginService} from "../login/login.service";
import HasAnyAuthorityDirective from "../shared/auth/has-any-authority.directive";
import {AccountService} from "../core/auth/account.service";
import {NgbDropdown, NgbDropdownMenu, NgbDropdownToggle} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-admin-sidebar',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    HasAnyAuthorityDirective,
    RouterLinkActive,
    NgbDropdownToggle,
    NgbDropdownMenu,
    NgbDropdown
  ],
  templateUrl: './admin-sidebar.component.html',
  styleUrl: './admin-sidebar.component.css'
})
export class AdminSidebarComponent {
  currentPage: string = '';

  account = inject(AccountService).trackCurrentAccount();

  private loginService = inject(LoginService);
  private router = inject(Router);

  login(): void {
    this.router.navigate(['/login']);
  }

  logout(): void {
    this.loginService.logout();
    this.router.navigate(['']);
  }
}
