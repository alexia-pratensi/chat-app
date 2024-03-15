import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginRequest } from '../interfaces/loginRequest.interface';
import { SessionInformation } from '../interfaces/sessionInformation.interface';
import { User } from '../interfaces/user.interface';


@Injectable({
  providedIn: 'root'
})
export class UserApiService {

  private pathService = 'http://localhost:8080/api/users';

  constructor(private httpClient: HttpClient) {}

  // Sends the login request to the server
  public login(loginRequest: LoginRequest): Observable<SessionInformation> {
    return this.httpClient.post<SessionInformation>(`${this.pathService}/login`, loginRequest);
  }
  // Fetches the user by id
  public getById(id: string): Observable<User> {
    return this.httpClient.get<User>(`${this.pathService}/${id}`);
  }
}