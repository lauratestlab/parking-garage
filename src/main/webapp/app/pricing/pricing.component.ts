import {Component, inject, OnInit, signal} from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Pricing } from '../model/pricing-model';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {PricingService} from "../revenue_api/pricing-api";
import {ITEMS_PER_PAGE} from "../config/pagination.constants";

import SharedModule from 'app/shared/shared.module';
import { SortByDirective, SortDirective, SortService, SortState, sortStateSignal } from 'app/shared/sort';
import {UserManagementService} from "../admin/user-management/service/user-management.service";

@Component({
  selector: 'app-pricing',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule,],
  templateUrl: './pricing.component.html',
  styleUrl: './pricing.component.css'
})
export class PricingComponent implements OnInit {
  currentPage: string = 'pricing';


  private priceService = inject(PricingService);

  priceForm: FormGroup;
  fb = inject(FormBuilder);

  prices: Pricing[] = [];
  selectedPrice: Pricing | null = null;

  constructor(private api: PricingService){
    this.priceForm = this.fb.group({
      price: ['', Validators.required],
      duration: ['', Validators.required]
    });
  }

  ngOnInit(): void {
      this.fetchAll();
  }

  fetchAll(): void {
    this.priceService.get().subscribe((data) => {
      this.prices = data;
    });
  }

  edit(price: Pricing): void {
    this.selectedPrice = { ...price }; // Clone data to avoid direct modifcations
    console.log('Clicking edit button for ', price.duration);
  }

  update(): void {
    if(this.selectedPrice) {
      this.priceService.update(this.selectedPrice).subscribe(() => {
        this.fetchAll();
        this.selectedPrice = null;
      })
    }
  }

  cancel(): void {
    this.selectedPrice = null;
  }

  // update(price: Pricing): void {
  //   this.priceService.update(price).subscribe(() => {
  //     this.fetchAll();
  //   })
  // }

  delete(id: number): void {
    this.priceService.delete(id).subscribe(() => {
      this.fetchAll();
    })
  }

  add() {
    const request: Pricing = this.priceForm.value;

    if (this.priceForm.valid) {
      this.api.add(request).subscribe({
        next: () => {
          window.alert('New price has been added!');
          this.fetchAll();
        },
        error: () => {
          console.error("'Error submitting pricing data");
        }
      });
    } else {
      console.log("Form submission failed");
    }
  }

}
