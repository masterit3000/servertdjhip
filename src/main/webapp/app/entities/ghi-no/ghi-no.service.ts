import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { GhiNo } from './ghi-no.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<GhiNo>;

@Injectable()
export class GhiNoService {

    private resourceUrl =  SERVER_API_URL + 'api/ghi-nos';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(ghiNo: GhiNo): Observable<EntityResponseType> {
        const copy = this.convert(ghiNo);
        return this.http.post<GhiNo>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(ghiNo: GhiNo): Observable<EntityResponseType> {
        const copy = this.convert(ghiNo);
        return this.http.put<GhiNo>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<GhiNo>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<GhiNo[]>> {
        const options = createRequestOption(req);
        return this.http.get<GhiNo[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<GhiNo[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: GhiNo = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<GhiNo[]>): HttpResponse<GhiNo[]> {
        const jsonResponse: GhiNo[] = res.body;
        const body: GhiNo[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to GhiNo.
     */
    private convertItemFromServer(ghiNo: GhiNo): GhiNo {
        const copy: GhiNo = Object.assign({}, ghiNo);
        copy.ngayghino = this.dateUtils
            .convertDateTimeFromServer(ghiNo.ngayghino);
        return copy;
    }

    /**
     * Convert a GhiNo to a JSON which can be sent to the server.
     */
    private convert(ghiNo: GhiNo): GhiNo {
        const copy: GhiNo = Object.assign({}, ghiNo);

        copy.ngayghino = this.dateUtils.toDate(ghiNo.ngayghino);
        return copy;
    }
}
