import {Component, OnInit} from '@angular/core';
import {DashboardApiService} from "./dashboard-api.service";
import {Observable} from "rxjs";
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {Revenue} from "../model/revenue.model";
import {error} from "@angular/compiler-cli/src/transformers/util";
import {CurrencyPipe, NgIf} from "@angular/common";

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    FormsModule,
    NgIf,
    ReactiveFormsModule,
    CurrencyPipe
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit{
  currentPage: string = 'dashboard';

  availSpots: number | undefined;
  revenueForm: FormGroup;
  revenueData?: Revenue;


  constructor(
      private api: DashboardApiService,
      private fb: FormBuilder,
  ) {

    const today = new Date().toISOString().split('T')[0];

    this.revenueForm = this.fb.group({
      start: [today],
      end: [today]
    });
  }

  ngOnInit(): void {
    this.getAvailSpots();

    this.revenueForm.valueChanges.subscribe(() => {
      if(this.revenueForm.valid) {
        this.fetchRevenue();
      }
    });

    // Initial fetch for today's revenue
    this.fetchRevenue();
  }

  getAvailSpots(): void {
    this.api.getSpots().subscribe({
      next: (data) => {
        this.availSpots = data;
      },
      error: (err) => {
        console.error('Error fetching all available spots: ', err);
      }
    });
  }

  fetchRevenue(): void {
    if (this.revenueForm.valid) {
      const { start, end } = this.revenueForm.value;

      this.api.getRevenue(start, end).subscribe({
        next: (data) => {
          this.revenueData = data;
        },
        error: (err) => {
          this.revenueData = undefined;
          console.error('Failed to retrieve revenue data', err);
        }
      });
    }
  }
}
