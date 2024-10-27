import {HttpClient, HttpParams} from "@angular/common/http";
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

    updatePrice(id: number, price: number): Observable<Pricing> {
        // Define URL with parameters
        const url = `${this.baseUrl}/price/update/${id}`;

        // Set up query parameters
        const params = new HttpParams().set('price', price.toString());

        // Make HTTP PUT request
        return this.http.put<Pricing>(url, {}, { params });
    }
}