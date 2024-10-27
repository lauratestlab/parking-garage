import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

import {ApplicationConfigService} from 'app/core/config/application-config.service';
import {SignupModel} from "./signup-model";

@Injectable({ providedIn: 'root' })
export class SignupService {
  private http = inject(HttpClient);
  private applicationConfigService = inject(ApplicationConfigService);

  save(registration: SignupModel): Observable<{}> {
    return this.http.post(this.applicationConfigService.getEndpointFor('api/register'), registration);
  }
}
