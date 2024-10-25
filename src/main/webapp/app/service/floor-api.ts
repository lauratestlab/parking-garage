import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { map, Observable } from "rxjs";
import { Floor } from "../model/floor-model";

@Injectable({
    providedIn: 'root'
})

export class FloorService {
    
    private baseUrl = 'http://localhost:8080'

    constructor(private http: HttpClient) { }

    getFloorList(): Observable<Floor[]> {
        return this.http.get<GetResponse>(this.baseUrl).pipe(
            map(response => response._embedded.floor)
          );
    }
}

interface GetResponse {
    _embedded: {
      floor: Floor[];
    }
  }