import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { map, Observable } from "rxjs";
import { Spot } from "../model/spot-model";

@Injectable({
    providedIn: 'root'
})

export class SpotService {
    
    private baseUrl = 'http://localhost:8080'

    constructor(private http: HttpClient) { }

    getSpotList(): Observable<Spot[]> {
        return this.http.get<GetResponse>(this.baseUrl).pipe(
            map(response => response._embedded.spot)
          );
    }
}

interface GetResponse {
    _embedded: {
      spot: Spot[];
    }
  }