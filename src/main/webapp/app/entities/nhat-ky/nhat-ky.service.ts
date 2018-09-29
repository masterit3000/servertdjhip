import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { NhatKy } from './nhat-ky.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<NhatKy>;

@Injectable()
export class NhatKyService {

    private resourceUrl = SERVER_API_URL + 'api/nhat-kies';
    private getAllByCuaHangUrl = SERVER_API_URL + 'api/nhat-kies-by-cua-hang';
    private findNhatKyUrl = SERVER_API_URL + 'api/find-nhat-kies';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(nhatKy: NhatKy): Observable<EntityResponseType> {
        const copy = this.convert(nhatKy);
        return this.http.post<NhatKy>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(nhatKy: NhatKy): Observable<EntityResponseType> {
        const copy = this.convert(nhatKy);
        return this.http.put<NhatKy>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<NhatKy>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<NhatKy[]>> {
        const options = createRequestOption(req);
        return this.http.get<NhatKy[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<NhatKy[]>) => this.convertArrayResponse(res));
    }
    getAllByCuaHAng(id: number): Observable<HttpResponse<NhatKy[]>> {
        return this.http.get<NhatKy[]>(`${this.getAllByCuaHangUrl}/${id}`, {  observe: 'response' })
            .map((res: HttpResponse<NhatKy[]>) => this.convertArrayResponse(res));
    }

    findNhatKy(key: any): Observable<HttpResponse<NhatKy[]>> {
        return this.http.get<NhatKy[]>(`${this.findNhatKyUrl}/${key}`, { observe: 'response' })
            .map((res: HttpResponse<NhatKy[]>) => this.convertArrayResponse(res));
    }
    findNhatKyByCuaHang(key: any,id:number): Observable<HttpResponse<NhatKy[]>> {
        return this.http.get<NhatKy[]>(`${this.findNhatKyUrl}/${key}/${id}`, { observe: 'response' })
            .map((res: HttpResponse<NhatKy[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: NhatKy = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: HttpResponse<NhatKy[]>): HttpResponse<NhatKy[]> {
        const jsonResponse: NhatKy[] = res.body;
        const body: NhatKy[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to NhatKy.
     */
    private convertItemFromServer(nhatKy: NhatKy): NhatKy {
        const copy: NhatKy = Object.assign({}, nhatKy);
        copy.thoiGian = this.dateUtils
            .convertDateTimeFromServer(nhatKy.thoiGian);
        return copy;
    }

    /**
     * Convert a NhatKy to a JSON which can be sent to the server.
     */
    private convert(nhatKy: NhatKy): NhatKy {
        const copy: NhatKy = Object.assign({}, nhatKy);

        copy.thoiGian = this.dateUtils.toDate(nhatKy.thoiGian);
        return copy;
    }
}
