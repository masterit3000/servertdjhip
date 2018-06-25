import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { AnhTaiSan } from './anh-tai-san.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<AnhTaiSan>;

@Injectable()
export class AnhTaiSanService {

    private resourceUrl =  SERVER_API_URL + 'api/anh-tai-sans';

    constructor(private http: HttpClient) { }

    create(anhTaiSan: AnhTaiSan): Observable<EntityResponseType> {
        const copy = this.convert(anhTaiSan);
        return this.http.post<AnhTaiSan>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(anhTaiSan: AnhTaiSan): Observable<EntityResponseType> {
        const copy = this.convert(anhTaiSan);
        return this.http.put<AnhTaiSan>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<AnhTaiSan>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<AnhTaiSan[]>> {
        const options = createRequestOption(req);
        return this.http.get<AnhTaiSan[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<AnhTaiSan[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: AnhTaiSan = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<AnhTaiSan[]>): HttpResponse<AnhTaiSan[]> {
        const jsonResponse: AnhTaiSan[] = res.body;
        const body: AnhTaiSan[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to AnhTaiSan.
     */
    private convertItemFromServer(anhTaiSan: AnhTaiSan): AnhTaiSan {
        const copy: AnhTaiSan = Object.assign({}, anhTaiSan);
        return copy;
    }

    /**
     * Convert a AnhTaiSan to a JSON which can be sent to the server.
     */
    private convert(anhTaiSan: AnhTaiSan): AnhTaiSan {
        const copy: AnhTaiSan = Object.assign({}, anhTaiSan);
        return copy;
    }
}
