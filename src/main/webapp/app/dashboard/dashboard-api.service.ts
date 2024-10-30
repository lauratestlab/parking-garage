import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import { Pricing } from "../model/pricing-model";
import {Observable} from "rxjs";
import {Revenue} from "../model/revenue.model";
import {ApplicationConfigService} from "../core/config/application-config.service";

@Injectable({
  providedIn: 'root'
})
export class DashboardApiService {
  private http = inject(HttpClient);
  private applicationConfigService = inject(ApplicationConfigService);

  private resourceUrl = this.applicationConfigService.getEndpointFor('api/report');


  getSpots(): Observable<any> {
    return this.http.get<any>(`${this.resourceUrl}/availableSpots`);
  }

  getRevenue(start: string, end: string): Observable<Revenue> {
    const params = new HttpParams()
        .set('start', start)
        .set('end', end);
    return this.http.get<Revenue>(`${this.resourceUrl}/revenue`, { params });
  }
}
