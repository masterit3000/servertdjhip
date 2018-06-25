import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { ThuChi } from './thu-chi.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<ThuChi>;

@Injectable()
export class ThuChiService {

    private resourceUrl =  SERVER_API_URL + 'api/thu-chis';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(thuChi: ThuChi): Observable<EntityResponseType> {
        const copy = this.convert(thuChi);
        return this.http.post<ThuChi>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(thuChi: ThuChi): Observable<EntityResponseType> {
        const copy = this.convert(thuChi);
        return this.http.put<ThuChi>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ThuChi>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<ThuChi[]>> {
        const options = createRequestOption(req);
        return this.http.get<ThuChi[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ThuChi[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: ThuChi = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<ThuChi[]>): HttpResponse<ThuChi[]> {
        const jsonResponse: ThuChi[] = res.body;
        const body: ThuChi[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to ThuChi.
     */
    private convertItemFromServer(thuChi: ThuChi): ThuChi {
        const copy: ThuChi = Object.assign({}, thuChi);
        copy.thoigian = this.dateUtils
            .convertDateTimeFromServer(thuChi.thoigian);
        return copy;
    }

    /**
     * Convert a ThuChi to a JSON which can be sent to the server.
     */
    private convert(thuChi: ThuChi): ThuChi {
        const copy: ThuChi = Object.assign({}, thuChi);

        copy.thoigian = this.dateUtils.toDate(thuChi.thoigian);
        return copy;
    }
}
