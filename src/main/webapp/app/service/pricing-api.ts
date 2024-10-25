import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { map, Observable } from "rxjs";
import { Pricing } from "../model/pricing-model";


@Injectable({
    providedIn: 'root'
})

export class PricingService {
    // Endpoint for pricing data
    private baseUrl = 'http://localhost:8080/api'

    constructor(private http: HttpClient) { }

    getPricingList(): Observable<Pricing[]> {
        return this.http.get<Pricing[]>(`${this.baseUrl}/price`);
    }

    addPrice(priceDetail: any): Observable<Pricing> {
        return this.http.post<Pricing>(`${this.baseUrl}/price/update`, priceDetail);
    }
    
    // updatePrice(id: number, priceDetail: any): Observable<Pricing> {
    //     return this.http.post<Pricing>(`${this.baseUrl}/${id}/price/update`, priceDetail);
    // }
}