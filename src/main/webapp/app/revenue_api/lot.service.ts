import {Injectable, inject} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {SignupModel} from "../account/signup/signup-model";
import {ApplicationConfigService} from "../core/config/application-config.service";

@Injectable({
  providedIn: 'root'
})

export class LotService {
  private applicationConfigService = inject(ApplicationConfigService);

  constructor(private http: HttpClient) {}

  register(request: SignupModel): Observable<SignupModel> {
    return this.http.post<SignupModel>(this.applicationConfigService.getEndpointFor('api/register'), request);
  }
}