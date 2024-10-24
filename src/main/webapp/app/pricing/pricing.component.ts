import {Component, inject, OnInit} from '@angular/core';
import { PricingService } from '../service/pricing-api';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Pricing } from '../model/pricing-model';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {SignupModel} from "../model/signup-model";

@Component({
  selector: 'app-pricing',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule,],
  templateUrl: './pricing.component.html',
  styleUrl: './pricing.component.css'
})
export class PricingComponent implements OnInit {
  currentPage: string = 'pricing';

  isAddPriceFormVisible = false;

  addPriceForm: FormGroup;
  fb = inject(FormBuilder);

  prices: Pricing[] = [];

  constructor(private api: PricingService){
    this.addPriceForm = this.fb.group({
      price: ['', Validators.required],
      duration: ['', Validators.required]
    });
  }


  ngOnInit(): void {
      this.loadPricingModel();
  }

  toggleAddPriceBar() {
    console.log("Add has clicked!");
    this.isAddPriceFormVisible = !this.isAddPriceFormVisible;
  }

  loadPricingModel(): void {
    this.api.getPricingList().subscribe(data =>{
      this.prices = data;
    });
  }

  addPrice() {
    const request: Pricing = this.addPriceForm.value;
    console.log(request);

    if (this.addPriceForm.valid) {
      console.log("Form is valid");

      this.api.addPrice(request).subscribe({
        next: () => {
          console.log('Pricing data submitted. Check db');
        },
        error: (err) => {
          console.error("'Error submitting pricing data: ", err);
        }
      });
    } else {
      console.log("Form submission failed");
    }
    // this.api.addPrice(this.prices).subscribe(
    //     (response) => {
    //       console.log('Pricing data submitted:', response);
    //     },
    //     (error) => {
    //       console.error('Error submitting pricing data:',error);
    //     }
    // );
  }
  updatePricing() {

  }

}
