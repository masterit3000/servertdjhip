import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Huyen } from './huyen.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Huyen>;

@Injectable()
export class HuyenService {

    private resourceUrl =  SERVER_API_URL + 'api/huyens';

    constructor(private http: HttpClient) { }

    create(huyen: Huyen): Observable<EntityResponseType> {
        const copy = this.convert(huyen);
        return this.http.post<Huyen>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(huyen: Huyen): Observable<EntityResponseType> {
        const copy = this.convert(huyen);
        return this.http.put<Huyen>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Huyen>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Huyen[]>> {
        const options = createRequestOption(req);
        return this.http.get<Huyen[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Huyen[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Huyen = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Huyen[]>): HttpResponse<Huyen[]> {
        const jsonResponse: Huyen[] = res.body;
        const body: Huyen[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Huyen.
     */
    private convertItemFromServer(huyen: Huyen): Huyen {
        const copy: Huyen = Object.assign({}, huyen);
        return copy;
    }

    /**
     * Convert a Huyen to a JSON which can be sent to the server.
     */
    private convert(huyen: Huyen): Huyen {
        const copy: Huyen = Object.assign({}, huyen);
        return copy;
    }
}
