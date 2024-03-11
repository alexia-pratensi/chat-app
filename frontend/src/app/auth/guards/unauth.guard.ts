import {Injectable} from "@angular/core";
import {CanActivate, Router} from "@angular/router"; 
import { SessionService } from "src/app/services/session.service";


@Injectable({providedIn: 'root'})
export class UnauthGuard implements CanActivate {

  constructor(private router: Router,
              private sessionService: SessionService) {}

  // Allows access to the route if the user is not logged in
  public canActivate(): boolean {
    if (this.sessionService.isLogged) {
      this.router.navigateByUrl('/posts');
      return false;
    }
    return true;
  }
}