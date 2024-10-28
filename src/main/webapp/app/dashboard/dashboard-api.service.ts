import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Pricing} from "../model/pricing-model";
import {Observable} from "rxjs";
import {Revenue} from "../model/revenue.model";

@Injectable({
  providedIn: 'root'
})
export class DashboardApiService {
  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) { }

  getAvailableSpots(): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/report/availableSpots`);
  }

  getRevenue(start: string, end: string): Observable<Revenue> {
    const params = new HttpParams()
        .set('start', start)
        .set('end', end);
    return this.http.get<Revenue>(`${this.baseUrl}/report/revenue`, { params });
  }
}
