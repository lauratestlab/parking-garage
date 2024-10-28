import {Component, OnInit} from '@angular/core';
import {DashboardApiService} from "./dashboard-api.service";
import {Observable} from "rxjs";
import {FormBuilder, FormGroup, FormsModule, Validators} from "@angular/forms";
import {Revenue} from "../model/revenue.model";
import {error} from "@angular/compiler-cli/src/transformers/util";

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    FormsModule
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
    this.revenueForm = this.fb.group({
      start: ['', Validators.required],
      end: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.getAvailSpots();
  }

  getAvailSpots(): void {
    this.api.getAvailableSpots().subscribe({
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
