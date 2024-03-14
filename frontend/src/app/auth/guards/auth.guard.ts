import {Injectable} from "@angular/core";
import {CanActivate, Router} from "@angular/router"; 
import { SessionService } from "src/app/services/session.service";


@Injectable({providedIn: 'root'})
export class AuthGuard implements CanActivate {

  constructor(private router: Router,
              private sessionService: SessionService) {}

  // Allows access to the route if the user is logged in
  // Otherwise, redirects to the login page
  public canActivate(): boolean {
    if (!this.sessionService.isLogged) {
      this.router.navigateByUrl('/login');
      return false;
    }
    return true;
  }
}