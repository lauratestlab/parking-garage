import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {SignupModel} from "../model/signup-model";
import {request} from "node:http";
import {SignupResponse} from "../model/signup-response";

const BASE_URL = "http://localhost:8080/api";

@Injectable({
  providedIn: 'root'
})

export class LotService {
  private baseUrl = 'http://localhost:8080/api/register';

  constructor(private http: HttpClient) {}

  register(request: SignupModel): Observable<SignupResponse> {
    return this.http.post<SignupResponse>(`${this.baseUrl}`, request);
  }
}