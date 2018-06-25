import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Tinh } from './tinh.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Tinh>;

@Injectable()
export class TinhService {

    private resourceUrl =  SERVER_API_URL + 'api/tinhs';

    constructor(private http: HttpClient) { }

    create(tinh: Tinh): Observable<EntityResponseType> {
        const copy = this.convert(tinh);
        return this.http.post<Tinh>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(tinh: Tinh): Observable<EntityResponseType> {
        const copy = this.convert(tinh);
        return this.http.put<Tinh>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Tinh>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Tinh[]>> {
        const options = createRequestOption(req);
        return this.http.get<Tinh[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Tinh[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Tinh = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Tinh[]>): HttpResponse<Tinh[]> {
        const jsonResponse: Tinh[] = res.body;
        const body: Tinh[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Tinh.
     */
    private convertItemFromServer(tinh: Tinh): Tinh {
        const copy: Tinh = Object.assign({}, tinh);
        return copy;
    }

    /**
     * Convert a Tinh to a JSON which can be sent to the server.
     */
    private convert(tinh: Tinh): Tinh {
        const copy: Tinh = Object.assign({}, tinh);
        return copy;
    }
}
