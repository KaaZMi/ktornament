import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Player } from "./player";

@Injectable({
  providedIn: 'root',
})
export class PlayersApiService {
  apiURL = 'http://localhost:8080';

  constructor(private http: HttpClient) {
  }

  getPlayers(): Observable<Player> {
    return this.http
      .get<Player>(this.apiURL + '/players')
      .pipe(catchError(this.handleError));
  }

  getPlayer(id: String): Observable<Player> {
    return this.http
      .get<Player>(this.apiURL + `/players/${id}`)
      .pipe(catchError(this.handleError));
  }

  addPlayer(pseudo: string): Observable<string> {
    return this.http
      .post(this.apiURL + '/players', JSON.stringify({ pseudo }), {
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
        }),
        responseType: 'text'
      })
      .pipe(catchError(this.handleError));
  }

  updatePlayer(id: String, points: number): Observable<string> {
    return this.http
      .put(this.apiURL + `/players/${id}`, JSON.stringify({ points }), {
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
        }),
        responseType: 'text'
      })
      .pipe(catchError(this.handleError));
  }

  deletePlayers(): Observable<string> {
    return this.http
      .delete(this.apiURL + '/players', { responseType: 'text' })
      .pipe(catchError(this.handleError));
  }

  handleError(error: any) {
    let errorMessage: string;
    if (error.error instanceof ErrorEvent) {
      // Client-side error
      errorMessage = error.error.message;
    } else {
      // Server-side error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    console.log(errorMessage);
    return throwError(() => {
      return errorMessage;
    });
  }
}
