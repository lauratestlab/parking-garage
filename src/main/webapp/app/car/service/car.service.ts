import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { Pagination } from 'app/core/request/request.model';
import { ICar } from '../car.model';

@Injectable({ providedIn: 'root' })
export class CarService {
  private http = inject(HttpClient);
  private applicationConfigService = inject(ApplicationConfigService);

  private resourceUrl = this.applicationConfigService.getEndpointFor('api/user/cars');

  create(car: ICar): Observable<ICar> {
    return this.http.post<ICar>(this.resourceUrl, car);
  }

  update(car: ICar): Observable<ICar> {
    return this.http.put<ICar>(this.resourceUrl, car);
  }

  find(login: string): Observable<ICar> {
    return this.http.get<ICar>(`${this.resourceUrl}/${login}`);
  }

  query(req?: Pagination): Observable<HttpResponse<ICar[]>> {
    const options = createRequestOption(req);
    return this.http.get<ICar[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(login: number): Observable<{}> {
    return this.http.delete(`${this.resourceUrl}/${login}`);
  }
}