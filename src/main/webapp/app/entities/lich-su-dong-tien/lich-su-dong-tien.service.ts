import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { LichSuDongTien } from './lich-su-dong-tien.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<LichSuDongTien>;

@Injectable()
export class LichSuDongTienService {

    private resourceUrl =  SERVER_API_URL + 'api/lich-su-dong-tiens';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(lichSuDongTien: LichSuDongTien): Observable<EntityResponseType> {
        const copy = this.convert(lichSuDongTien);
        return this.http.post<LichSuDongTien>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(lichSuDongTien: LichSuDongTien): Observable<EntityResponseType> {
        const copy = this.convert(lichSuDongTien);
        return this.http.put<LichSuDongTien>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<LichSuDongTien>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<LichSuDongTien[]>> {
        const options = createRequestOption(req);
        return this.http.get<LichSuDongTien[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<LichSuDongTien[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: LichSuDongTien = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<LichSuDongTien[]>): HttpResponse<LichSuDongTien[]> {
        const jsonResponse: LichSuDongTien[] = res.body;
        const body: LichSuDongTien[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to LichSuDongTien.
     */
    private convertItemFromServer(lichSuDongTien: LichSuDongTien): LichSuDongTien {
        const copy: LichSuDongTien = Object.assign({}, lichSuDongTien);
        copy.ngaybatdau = this.dateUtils
            .convertDateTimeFromServer(lichSuDongTien.ngaybatdau);
        copy.ngayketthuc = this.dateUtils
            .convertDateTimeFromServer(lichSuDongTien.ngayketthuc);
        return copy;
    }

    /**
     * Convert a LichSuDongTien to a JSON which can be sent to the server.
     */
    private convert(lichSuDongTien: LichSuDongTien): LichSuDongTien {
        const copy: LichSuDongTien = Object.assign({}, lichSuDongTien);

        copy.ngaybatdau = this.dateUtils.toDate(lichSuDongTien.ngaybatdau);

        copy.ngayketthuc = this.dateUtils.toDate(lichSuDongTien.ngayketthuc);
        return copy;
    }
}
