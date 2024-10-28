import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { Pagination } from 'app/core/request/request.model';
import { ISpot } from '../spot.model';
import {IFloor} from "../../floors/floor.model";

@Injectable({ providedIn: 'root' })
export class SpotService {
  private http = inject(HttpClient);
  private applicationConfigService = inject(ApplicationConfigService);

  private resourceUrl = this.applicationConfigService.getEndpointFor('api/admin/spots');

  create(spot: ISpot): Observable<ISpot> {
    return this.http.post<ISpot>(this.resourceUrl, spot);
  }

  update(spot: ISpot): Observable<ISpot> {
    return this.http.put<ISpot>(this.resourceUrl, spot);
  }

  find(login: string): Observable<ISpot> {
    return this.http.get<ISpot>(`${this.resourceUrl}/${login}`);
  }

  query(req?: Pagination): Observable<HttpResponse<ISpot[]>> {
    const options = createRequestOption(req);
    return this.http.get<ISpot[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(login: number): Observable<{}> {
    return this.http.delete(`${this.resourceUrl}/${login}`);
  }
}
