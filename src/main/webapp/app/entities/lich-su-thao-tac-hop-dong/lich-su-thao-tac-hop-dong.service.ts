import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { LichSuThaoTacHopDong } from './lich-su-thao-tac-hop-dong.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<LichSuThaoTacHopDong>;

@Injectable()
export class LichSuThaoTacHopDongService {

    private resourceUrl =  SERVER_API_URL + 'api/lich-su-thao-tac-hop-dongs';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(lichSuThaoTacHopDong: LichSuThaoTacHopDong): Observable<EntityResponseType> {
        const copy = this.convert(lichSuThaoTacHopDong);
        return this.http.post<LichSuThaoTacHopDong>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(lichSuThaoTacHopDong: LichSuThaoTacHopDong): Observable<EntityResponseType> {
        const copy = this.convert(lichSuThaoTacHopDong);
        return this.http.put<LichSuThaoTacHopDong>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<LichSuThaoTacHopDong>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<LichSuThaoTacHopDong[]>> {
        const options = createRequestOption(req);
        return this.http.get<LichSuThaoTacHopDong[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<LichSuThaoTacHopDong[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: LichSuThaoTacHopDong = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<LichSuThaoTacHopDong[]>): HttpResponse<LichSuThaoTacHopDong[]> {
        const jsonResponse: LichSuThaoTacHopDong[] = res.body;
        const body: LichSuThaoTacHopDong[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to LichSuThaoTacHopDong.
     */
    private convertItemFromServer(lichSuThaoTacHopDong: LichSuThaoTacHopDong): LichSuThaoTacHopDong {
        const copy: LichSuThaoTacHopDong = Object.assign({}, lichSuThaoTacHopDong);
        copy.thoigian = this.dateUtils
            .convertDateTimeFromServer(lichSuThaoTacHopDong.thoigian);
        return copy;
    }

    /**
     * Convert a LichSuThaoTacHopDong to a JSON which can be sent to the server.
     */
    private convert(lichSuThaoTacHopDong: LichSuThaoTacHopDong): LichSuThaoTacHopDong {
        const copy: LichSuThaoTacHopDong = Object.assign({}, lichSuThaoTacHopDong);

        copy.thoigian = this.dateUtils.toDate(lichSuThaoTacHopDong.thoigian);
        return copy;
    }
}
