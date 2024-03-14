import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { SessionInformation } from '../interfaces/sessionInformation.interface';


@Injectable({
  providedIn: 'root'
})
export class SessionService {

  public isLogged = false;
  public sessionInformation: SessionInformation | undefined;
  private isLoggedSubject = new BehaviorSubject<boolean>(this.isLogged);

  // Returns an observable that indicates if the user is logged in
  public $isLogged(): Observable<boolean> {
    return this.isLoggedSubject.asObservable();
  }

  // Returns the session information of the user
  public logIn(user: SessionInformation): void {
    this.sessionInformation = user;
    this.isLogged = true;
    this.next();
  }

  // Removes the session information of the user
  public logOut(): void {
    this.sessionInformation = undefined;
    this.isLogged = false;
    this.next();
  }

  // Uses to notify the subscribers that the user is logged in or not
  private next(): void {
    this.isLoggedSubject.next(this.isLogged);
  }
}
