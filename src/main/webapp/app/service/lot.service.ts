import {Injectable, inject} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {SignupModel} from "../account/signup/signup-model";
import {SignupResponse} from "../model/signup-response";
import {ApplicationConfigService} from "../core/config/application-config.service";


@Injectable({
  providedIn: 'root'
})

export class LotService {
  private applicationConfigService = inject(ApplicationConfigService);

  constructor(private http: HttpClient) {}

  register(request: SignupModel): Observable<SignupResponse> {
    return this.http.post<SignupResponse>(this.applicationConfigService.getEndpointFor('api/register'), request);
  }
}