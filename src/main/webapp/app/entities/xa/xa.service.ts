import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Xa } from './xa.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Xa>;

@Injectable()
export class XaService {

    private resourceUrl =  SERVER_API_URL + 'api/xas';

    constructor(private http: HttpClient) { }

    create(xa: Xa): Observable<EntityResponseType> {
        const copy = this.convert(xa);
        return this.http.post<Xa>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(xa: Xa): Observable<EntityResponseType> {
        const copy = this.convert(xa);
        return this.http.put<Xa>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Xa>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Xa[]>> {
        const options = createRequestOption(req);
        return this.http.get<Xa[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Xa[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Xa = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Xa[]>): HttpResponse<Xa[]> {
        const jsonResponse: Xa[] = res.body;
        const body: Xa[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Xa.
     */
    private convertItemFromServer(xa: Xa): Xa {
        const copy: Xa = Object.assign({}, xa);
        return copy;
    }

    /**
     * Convert a Xa to a JSON which can be sent to the server.
     */
    private convert(xa: Xa): Xa {
        const copy: Xa = Object.assign({}, xa);
        return copy;
    }
}
