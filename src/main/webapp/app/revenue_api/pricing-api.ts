import {HttpClient, HttpParams} from "@angular/common/http";
import {inject, Injectable} from "@angular/core";
import { map, Observable } from "rxjs";
import { Pricing } from "../model/pricing-model";
import {ApplicationConfigService} from "../core/config/application-config.service";


@Injectable({
    providedIn: 'root'
})

export class PricingService {
    // Endpoint for pricing data
    private applicationConfigService = inject(ApplicationConfigService);

    private baseUrl = this.applicationConfigService.getEndpointFor('api/price');

    constructor(private http: HttpClient) { }

    get(): Observable<Pricing[]> {
        return this.http.get<Pricing[]>(`${this.baseUrl}`);
    }

    add(priceDetail: any): Observable<Pricing> {
        return this.http.post<Pricing>(`${this.baseUrl}/update`, priceDetail);
    }

    update(price: Pricing): Observable<Pricing> {
        return this.http.put<Pricing>(`${this.baseUrl}/update/${price.duration}`, price);
    }

    delete(id: number): Observable<void> {
        return this.http.delete<void>(`${this.baseUrl}/${id}`);
    }
}