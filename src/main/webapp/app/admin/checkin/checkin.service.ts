import {inject, Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {ApplicationConfigService} from "../../core/config/application-config.service";
import {ReservationInfo} from "../../model/reservation-info.model";

@Injectable({
    providedIn: 'root'
})

export class CheckinService{
    private applicationConfigService = inject(ApplicationConfigService);

    private baseUrl = this.applicationConfigService.getEndpointFor('api/reservation');

    constructor(private http: HttpClient) {}

    addNewReservation(reservation: any): Observable<ReservationInfo> {
        return this.http.post<ReservationInfo>(`${this.baseUrl}/start`, reservation);
    }

    getQrImage(confirmationCode: string): string {
        return `${this.baseUrl}/qr/${confirmationCode}`;
    }
}