import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { Pagination } from 'app/core/request/request.model';
import { IFloor } from '../floor.model';

@Injectable({ providedIn: 'root' })
export class FloorService {
  private http = inject(HttpClient);
  private applicationConfigService = inject(ApplicationConfigService);

  private resourceUrl = this.applicationConfigService.getEndpointFor('api/admin/floors');

  create(floor: IFloor): Observable<IFloor> {
    return this.http.post<IFloor>(this.resourceUrl, floor);
  }

  update(floor: IFloor): Observable<IFloor> {
    return this.http.put<IFloor>(this.resourceUrl, floor);
  }

  find(login: string): Observable<IFloor> {
    return this.http.get<IFloor>(`${this.resourceUrl}/${login}`);
  }

  query(req?: Pagination): Observable<HttpResponse<IFloor[]>> {
    const options = createRequestOption(req);
    return this.http.get<IFloor[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(login: number): Observable<{}> {
    return this.http.delete(`${this.resourceUrl}/${login}`);
  }
}
