import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Injectable} from '@angular/core';

@Injectable(
  {
    providedIn: 'root'
  }
)

export class ApiService {
  private basedUrl: string = 'http://localhost:8080/api/apod';

  constructor(private http: HttpClient) { }

  search(query: string): Observable<any> {
    return this.http.get(`${this.basedUrl}/search?query=${encodeURIComponent(query)}`);
  }
}
