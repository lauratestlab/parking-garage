import {inject, Injectable, OnInit} from "@angular/core";
import {Observable} from "rxjs";
import {Pricing} from "../model/pricing-model";
import {HttpClient} from "@angular/common/http";
import {ApplicationConfigService} from "../core/config/application-config.service";
import {ReservationResponse} from "../model/reservation.model";

@Injectable({
    providedIn: 'root'
})

export class ReservationApi{
    private applicationConfigService = inject(ApplicationConfigService);

    private baseUrl = this.applicationConfigService.getEndpointFor('api/reservation');

    constructor(private http: HttpClient) {}

    get(): Observable<ReservationResponse[]> {
        return this.http.get<ReservationResponse[]>(`${this.baseUrl}`);
    }

    add(reservationDetail: any): Observable<any> {
        return this.http.post<any>(`${this.baseUrl}`, reservationDetail);
    }

}