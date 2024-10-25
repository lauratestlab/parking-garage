import {Component, OnInit} from '@angular/core';
import {DashboardApiService} from "./dashboard-api.service";
import {Observable} from "rxjs";
import {FormsModule} from "@angular/forms";

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
  revenueData: any;



  constructor(private api: DashboardApiService) {}

  ngOnInit(): void {
    this.getAvailSpots();
    this.fetchRevenue();
  }

  getAvailSpots(): void {
    this.api.getAvailableSpots().subscribe(
        data => { this.availSpots = data; },
        error => { console.error('Error fetching all available spots: ', error); }
    );
  }


  fetchRevenue(): void {
    this.api.getRevenue().subscribe({
      next: (data) => {
        this.revenueData = data;
        },
      error: (err) => {
        console.error('Error fetching revenue data', err);
      }
    });
  }

}
