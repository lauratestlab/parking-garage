import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Pricing} from "../model/pricing-model";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class DashboardApiService {
  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) { }

  getAvailableSpots(): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/report/availableSpots`);
  }

  getRevenue(): Observable<any> {
    return this.http.get(`${this.baseUrl}/report/revenue`);
  }
}
