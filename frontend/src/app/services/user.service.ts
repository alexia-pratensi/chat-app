import { Injectable } from "@angular/core";
import { User } from "../interfaces/user.interface";
import { UserApiService } from "./user-api.service";
import { SessionService } from "./session.service";
import { Observable, tap } from "rxjs";

@Injectable({
    providedIn: 'root'
  })
  export class UserService {
  
  public user: User | undefined;
  
  constructor(private userApiService: UserApiService,
              private sessionService: SessionService) { }

  public getCurrentUser(): Observable<User> {
    if (this.sessionService.sessionInformation && this.sessionService.sessionInformation.id) {
        return this.userApiService.getById(this.sessionService.sessionInformation.id.toString())
        .pipe(tap((user: User) => this.user = user));
    } else {
        throw new Error('Session information not available');
    }
  }
     
}