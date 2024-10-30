import {Component, OnInit, ViewChild} from '@angular/core';
import {DashboardApiService} from "./dashboard-api.service";
import {Observable} from "rxjs";
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {Revenue} from "../model/revenue.model";
import {CurrencyPipe, KeyValuePipe, NgIf} from "@angular/common";
import {ChartData, ChartOptions} from "chart.js/auto";
import {BaseChartDirective} from "ng2-charts";

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    FormsModule,
    NgIf,
    ReactiveFormsModule,
    CurrencyPipe,
    BaseChartDirective,
    KeyValuePipe,
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit{
  currentPage: string = 'dashboard';

  availSpots: number | undefined;
  revenueForm: FormGroup;
  revenueData?: Revenue;

  colors: string[] = ['Red', 'Black', 'Blue', 'White', 'Grey', 'Green'];
  carCount: number | null = null;
  selectedColor: string = 'Red';

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

    // Initial fetch all the information to display on dashboard
    this.fetchRevenue();
    this.getAvailSpots();
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

  fetchCarCount(): void {
    if(this.selectedColor) {
      this.api.fetchCarByColor(this.selectedColor).subscribe({
        next: (count) => {
          this.carCount = count;
        },
        error: (err) => {
          console.error('Failed to fetch car count', err);
          this.carCount = null;
        }
      })
    }
  }

}
