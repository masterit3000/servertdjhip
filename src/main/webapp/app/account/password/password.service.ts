import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

@Injectable()
export class PasswordService {

    constructor(private http: HttpClient) {}
    resourceUrl =SERVER_API_URL + 'api/change-pass-nv-users';
    save(newPassword: string): Observable<any> {
        return this.http.post(SERVER_API_URL + 'api/account/change-password', newPassword);
    }
    changPassById(id:number,password:string): Observable<HttpResponse<any>> {
        return this.http.post<any>(`${this.resourceUrl}/${id}/${password}`, { observe: 'response' })
    }
}
