import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { GhiNo } from './ghi-no.model';
import { createRequestOption } from '../../shared';
import { LOAIHOPDONG } from '../hop-dong';

export type EntityResponseType = HttpResponse<GhiNo>;

@Injectable()
export class GhiNoService {

    private resourceUrl =  SERVER_API_URL + 'api/ghi-nos';
    private ghinoUrl = SERVER_API_URL + 'api/ghi-nos-by-hopdong';
    private baocaoUrl = SERVER_API_URL + 'api/bao-cao-ghi-nos';
    private baocaoNVUrl = SERVER_API_URL + 'api/bao-cao-ghi-nos-nhanvien';
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
    findByHopDong(id: number): Observable<HttpResponse<GhiNo[]>> {
        return this.http.get<GhiNo[]>(`${this.ghinoUrl}/${id}`, {observe: 'response' })
            .map((res: HttpResponse<GhiNo[]>) => this.convertArrayResponse(res));
    }
    baoCao(loaihopdong: LOAIHOPDONG, start: Date, end: Date): Observable<HttpResponse<GhiNo[]>> {
        let endd = this.convertDateToString(end);
        let startd = this.convertDateToString(start);
        return this.http.get<GhiNo[]>(`${this.baocaoUrl}/${loaihopdong}/${startd}/${endd}`, {observe: 'response' })
            .map((res: HttpResponse<GhiNo[]>) => this.convertArrayResponse(res));
    }
    baoCaoNV(loaihopdong: LOAIHOPDONG, start: Date, end: Date,id: number): Observable<HttpResponse<GhiNo[]>> {
        let endd = this.convertDateToString(end);
        let startd = this.convertDateToString(start);
        return this.http.get<GhiNo[]>(`${this.baocaoNVUrl}/${loaihopdong}/${startd}/${endd}/${id}`, {observe: 'response' })
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

        // copy.ngayghino = this.dateUtils.toDate(ghiNo.ngayghino);
        return copy;
    }
    private convertDateToString(d: Date): String {

        let m = d.getMonth() + 1;
        let mm = m < 10 ? '0' + m : m;
        let day = d.getDate();
        let sday = day < 10 ? '0' + day : day;

        return d.getFullYear() + ' ' + mm + ' ' + sday;

    }
}
