import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import { Pricing } from "../model/pricing-model";
import {Observable} from "rxjs";
import {Revenue} from "../model/revenue.model";
import {ApplicationConfigService} from "../core/config/application-config.service";
import {ICar} from "../car/car.model";

export type EntityArrayResponseType = HttpResponse<ICar[]>;
@Injectable({
  providedIn: 'root'
})
export class DashboardApiService {
  private http = inject(HttpClient);
  private applicationConfigService = inject(ApplicationConfigService);

  private resourceUrl = this.applicationConfigService.getEndpointFor('api/report');
  private carUrl = this.applicationConfigService.getEndpointFor('api/reservation');
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/admin/report');


  getSpots(): Observable<any> {
    return this.http.get<any>(`${this.resourceUrl}/availableSpots`);
  }

  getRevenue(start: string, end: string): Observable<Revenue> {
    const params = new HttpParams()
        .set('start', start)
        .set('end', end);
    return this.http.get<Revenue>(`${this.resourceUrl}/revenue`, { params });
  }

  fetchCarByColor(color: string): Observable<number> {
    return this.http.get<number>(`${this.carUrl}/countByColor/${color}`);
  }


}
