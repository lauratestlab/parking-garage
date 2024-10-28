import {Component, inject, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Pricing } from '../model/pricing-model';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {PricingService} from "../revenue_api/pricing-api";

@Component({
  selector: 'app-pricing',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule,],
  templateUrl: './pricing.component.html',
  styleUrl: './pricing.component.css'
})
export class PricingComponent implements OnInit {
  currentPage: string = 'pricing';

  priceForm: FormGroup;
  fb = inject(FormBuilder);

  prices: Pricing[] = [];

  constructor(private api: PricingService){
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

  addPrice() {
    const request: Pricing = this.priceForm.value;
    console.log(request);

    if (this.priceForm.valid) {
      console.log("Form is valid");

      this.api.addPrice(request).subscribe({
        next: () => {
          console.log('Pricing data submitted. Check db');
        },
        error: () => {
          console.error("'Error submitting pricing data");
        }
      });
    } else {
      console.log("Form submission failed");

    }
  }


  // Function to submit form data
  updatePrice() {
    if (this.priceForm.valid) {
      const { price, duration } = this.priceForm.value;

      this.api.updatePrice(duration, price).subscribe({
        next: () => {
          console.log('Price updated successfully!');
          // this.successMessage = 'Price updated successfully!';
          // this.errorMessage = '';
        },
        error: (err) => {
          // this.errorMessage = 'Failed to update price';
          // this.successMessage = '';
          console.error('Failed to update price. ', err);
        }
      });
    }
  }

}
