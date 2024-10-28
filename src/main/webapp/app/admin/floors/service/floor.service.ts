import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
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

  getFloorIdentifier(floor: Pick<IFloor, 'id'>): number | null {
    return floor.id;
  }

  compareFloor(o1: Pick<IFloor, 'id'> | null, o2: Pick<IFloor, 'id'> | null): boolean {
    return o1 && o2 ? this.getFloorIdentifier(o1) === this.getFloorIdentifier(o2) : o1 === o2;
  }

  addFloorToCollectionIfMissing<Type extends Pick<IFloor, 'id'>>(
      floorCollection: Type[],
      ...floorsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const floors: Type[] = floorsToCheck.filter(isPresent);
    if (floors.length > 0) {
      const floorCollectionIdentifiers = floorCollection.map(floorItem => this.getFloorIdentifier(floorItem));
      const floorsToAdd = floors.filter(floorItem => {
        const floorIdentifier = this.getFloorIdentifier(floorItem);
        if (floorCollectionIdentifiers.includes(floorIdentifier)) {
          return false;
        }
        floorCollectionIdentifiers.push(floorIdentifier);
        return true;
      });
      return [...floorsToAdd, ...floorCollection];
    }
    return floorCollection;
  }
}
