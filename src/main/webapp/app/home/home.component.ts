// import { Component, OnDestroy, OnInit, inject, signal } from '@angular/core';
// import { Router, RouterModule } from '@angular/router';
// import { Subject } from 'rxjs';

// import { takeUntil } from 'rxjs/operators';

// import SharedModule from 'app/shared/shared.module';
// import { AccountService } from 'app/core/auth/account.service';
// import { Account } from 'app/core/auth/account.model';

// @Component({
//   standalone: true,
//   selector: 'app-home',
//   templateUrl: './home.component.html',
//   styleUrl: './home.component.css',
//   imports: [SharedModule, RouterModule],
// })
// export default class HomeComponent implements OnInit, OnDestroy {
//   account = signal<Account | null>(null);

//   private readonly destroy$ = new Subject<void>();

//   private accountService = inject(AccountService);
//   private router = inject(Router);

//   ngOnInit(): void {
//     this.accountService
//       .getAuthenticationState()
//       .pipe(takeUntil(this.destroy$))
//       .subscribe(account => this.account.set(account));
//   }

//   login(): void {
//     this.router.navigate(['/login']);
//   }

//   ngOnDestroy(): void {
//     this.destroy$.next();
//     this.destroy$.complete();
//   }
  // loadPricingModel(): void {
  //   this.api.getPricingList().subscribe(
  //       data => { this.prices = data; },
  //       error => { console.error('Error fetching home list', error); }
  //   );
  // }
// }
import {Component, inject, OnInit} from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Pricing } from '../model/pricing-model';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {PricingService} from "../revenue_api/pricing-api";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit {
  currentPage: string = 'pricing';

  priceForm: FormGroup;
  fb = inject(FormBuilder);

  prices: Pricing[] = [];
   private router = inject(Router);

  constructor(private api: PricingService) {
    this.priceForm = this.fb.group({
      price: ['', Validators.required],
      duration: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.loadPricingModel();
  }
    loadPricingModel(): void {
    this.api.getPricingList().subscribe(
        data => { this.prices = data; },
        error => { console.error('Error fetching pricing list', error); }
    );
    }
    goToReservationPage() {
    this.router.navigate(['./reservations/reservations.componen']); 
  }

}
