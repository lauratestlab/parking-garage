import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {Pricing} from "../model/pricing-model";
import {HttpClient} from "@angular/common/http";

@Injectable({
    providedIn: 'root'
})

export class ReservationApi {
    private baseUrl = 'http://localhost:8080/api';

    constructor(private http: HttpClient) {}

    addReservation(reservationDetail: any): Observable<any> {
        return this.http.post<any>(`${this.baseUrl}/reservation`, reservationDetail);
    }
}