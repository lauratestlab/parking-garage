import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFloor } from '../floor.model';
import { FloorService } from '../service/floor.service';

const floorResolve = (route: ActivatedRouteSnapshot): Observable<null | IFloor> => {
  const id = route.params.id;
  if (id) {
    return inject(FloorService)
      .find(id)
      .pipe(
        mergeMap((floor: HttpResponse<IFloor>) => {
          if (floor.body) {
            return of(floor.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default floorResolve;
