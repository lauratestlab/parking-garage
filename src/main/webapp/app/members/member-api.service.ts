import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import { Member } from "../model/member-model";

@Injectable({
  providedIn: 'root'
})
export class MemberApiService {
  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) { }

  getAllMembers(): Observable<Member[]> {
    return this.http.get<Member[]>(`${this.baseUrl}/users`);
  }
}
