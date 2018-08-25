import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { TaiSan } from './tai-san.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<TaiSan>;

@Injectable()
export class TaiSanService {

    private resourceUrl =  SERVER_API_URL + 'api/tai-sans';
    private taisanUrl= SERVER_API_URL + 'api/tai-sans-by-hopdong';

    constructor(private http: HttpClient) { }

    create(taiSan: TaiSan): Observable<EntityResponseType> {
        const copy = this.convert(taiSan);
        return this.http.post<TaiSan>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(taiSan: TaiSan): Observable<EntityResponseType> {
        const copy = this.convert(taiSan);
        return this.http.put<TaiSan>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<TaiSan>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }
    findByHopDong(id: number): Observable<HttpResponse<TaiSan[]>> {
        return this.http
            .get<TaiSan[]>(`${this.taisanUrl}/${id}`, { observe: 'response' })
            .map((res: HttpResponse<TaiSan[]>) =>
                this.convertArrayResponse(res)
            );
    }
    query(req?: any): Observable<HttpResponse<TaiSan[]>> {
        const options = createRequestOption(req);
        return this.http.get<TaiSan[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<TaiSan[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: TaiSan = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<TaiSan[]>): HttpResponse<TaiSan[]> {
        const jsonResponse: TaiSan[] = res.body;
        const body: TaiSan[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to TaiSan.
     */
    private convertItemFromServer(taiSan: TaiSan): TaiSan {
        const copy: TaiSan = Object.assign({}, taiSan);
        return copy;
    }

    /**
     * Convert a TaiSan to a JSON which can be sent to the server.
     */
    private convert(taiSan: TaiSan): TaiSan {
        const copy: TaiSan = Object.assign({}, taiSan);
        return copy;
    }
}
