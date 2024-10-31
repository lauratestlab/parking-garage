import {inject, Injectable} from '@angular/core';
import {ApplicationConfigService} from "../core/config/application-config.service";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

// Interface for verify code response
interface ReservationResponse {
  startTime: string;
  endTime: string;
  spotId: string;
  price: number;
}

interface ReservationCompletion {
  confirmationCode: string;
}

@Injectable({
  providedIn: 'root'
})
export class VerifyCodeService {
  private applicationConfigService = inject(ApplicationConfigService);

  private baseUrl = this.applicationConfigService.getEndpointFor('api/reservation');

  constructor(private http: HttpClient) { }

  verify(confirmationCode: ReservationCompletion): Observable<ReservationResponse>{
    return this.http.post<ReservationResponse>(`${this.baseUrl}/complete`, confirmationCode);
  }
}
