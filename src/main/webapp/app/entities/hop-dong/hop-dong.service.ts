import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { HopDong } from './hop-dong.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<HopDong>;

@Injectable()
export class HopDongService {

    private resourceUrl =  SERVER_API_URL + 'api/hop-dongs';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(hopDong: HopDong): Observable<EntityResponseType> {
        const copy = this.convert(hopDong);
        return this.http.post<HopDong>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(hopDong: HopDong): Observable<EntityResponseType> {
        const copy = this.convert(hopDong);
        return this.http.put<HopDong>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<HopDong>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<HopDong[]>> {
        const options = createRequestOption(req);
        return this.http.get<HopDong[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<HopDong[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: HopDong = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<HopDong[]>): HttpResponse<HopDong[]> {
        const jsonResponse: HopDong[] = res.body;
        const body: HopDong[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to HopDong.
     */
    private convertItemFromServer(hopDong: HopDong): HopDong {
        const copy: HopDong = Object.assign({}, hopDong);
        copy.ngaytao = this.dateUtils
            .convertDateTimeFromServer(hopDong.ngaytao);
        return copy;
    }

    /**
     * Convert a HopDong to a JSON which can be sent to the server.
     */
    private convert(hopDong: HopDong): HopDong {
        const copy: HopDong = Object.assign({}, hopDong);

        copy.ngaytao = this.dateUtils.toDate(hopDong.ngaytao);
        return copy;
    }
}
