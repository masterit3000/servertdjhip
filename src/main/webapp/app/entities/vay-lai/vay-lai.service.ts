import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';
import { LichSuThaoTacHopDong } from '../lich-su-thao-tac-hop-dong';
import { VayLai } from './vay-lai.model';
import { createRequestOption } from '../../shared';
import { LichSuDongTien } from '../lich-su-dong-tien';
import { TRANGTHAIHOPDONG } from '../hop-dong';
export type EntityResponseType = HttpResponse<VayLai>;

@Injectable()
export class VayLaiService {

    private resourceUrl = SERVER_API_URL + 'api/vay-lais';
    private traChamUrl = SERVER_API_URL + 'api/vay-lais-tra-cham';
    private findoneURl = SERVER_API_URL + 'api/find-one-vay-lais';
    private tienVayThemTraBotUrl = SERVER_API_URL + 'api/vay-them-tra-bot';
    private lichSuDongTien = 'lichsudongtien';
    private resourceUrlTimVayLai = SERVER_API_URL + 'api/tim-vay-lais-by-ten-cmnd';
    private lichSuThaoTacHopDong = 'lichsuthaotac';
    private thembotvaylai = SERVER_API_URL + 'api/them-bot-vay-lais';
    private baocaoUrl = SERVER_API_URL + 'api/bao-cao-vay-lais';
    private baocaoUrlKeToan = SERVER_API_URL + 'api/bao-cao-vay-lais-ke-toan';
    private baocaoNVUrl = SERVER_API_URL + 'api/bao-cao-vay-lais-nhanvien';
    private findByCuaHangUrl = SERVER_API_URL + 'api/vay-lais-by-cuaHang';
    private findByNhanVienUrl = SERVER_API_URL + 'api/vay-lais-by-nhanvien';
    private findByHopDongUrl = SERVER_API_URL + 'api/vay-lais-by-hopdong';

    constructor(private http: HttpClient) { }

    create(vayLai: VayLai): Observable<EntityResponseType> {
        const copy = this.convert(vayLai);
        return this.http.post<VayLai>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    themBotVayLai(vayLai: VayLai, id: number,mahopdong:string): Observable<EntityResponseType> {
        const copy = this.convert(vayLai);
        return this.http.post<VayLai>(`${this.thembotvaylai}/${id}/${mahopdong}`, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(vayLai: VayLai): Observable<EntityResponseType> {
        const copy = this.convert(vayLai);
        return this.http.put<VayLai>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<VayLai>(`${this.findoneURl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(trangthai: TRANGTHAIHOPDONG): Observable<HttpResponse<VayLai[]>> {
        return this.http.get<VayLai[]>(`${this.resourceUrl}/${trangthai}`, { observe: 'response' })
            .map((res: HttpResponse<VayLai[]>) => this.convertArrayResponse(res));
    }
    lichSuTraCham(): Observable<HttpResponse<VayLai[]>> {
        return this.http.get<VayLai[]>(`${this.traChamUrl}`, { observe: 'response' })
            .map((res: HttpResponse<VayLai[]>) => this.convertArrayResponse(res));
    }
    getAllByCuaHang(id?: number): Observable<HttpResponse<VayLai[]>> {
        return this.http.get<VayLai[]>(`${this.findByCuaHangUrl}/${id}`, { observe: 'response' })
            .map((res: HttpResponse<VayLai[]>) => this.convertArrayResponse(res));
    }
    getAllByNhanVien(id?: number): Observable<HttpResponse<VayLai[]>> {
        return this.http.get<VayLai[]>(`${this.findByNhanVienUrl}/${id}`, { observe: 'response' })
            .map((res: HttpResponse<VayLai[]>) => this.convertArrayResponse(res));
    }
    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: VayLai = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: HttpResponse<VayLai[]>): HttpResponse<VayLai[]> {
        const jsonResponse: VayLai[] = res.body;
        const body: VayLai[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to VayLai.
     */
    private convertItemFromServer(vayLai: VayLai): VayLai {
        const copy: VayLai = Object.assign({}, vayLai);
        return copy;
    }

    /**
     * Convert a VayLai to a JSON which can be sent to the server.
     */
    private convert(vayLai: VayLai): VayLai {
        const copy: VayLai = Object.assign({}, vayLai);
        return copy;
    }
    findVayLaiByTenOrCMND(key: any,trangthai:TRANGTHAIHOPDONG): Observable<HttpResponse<VayLai[]>> {
        // const options = createRequestOption(req);
        return this.http
            .get<VayLai[]>(`${this.resourceUrlTimVayLai}/${key}/${trangthai}`, {
                observe: 'response'
            })
            .map((res: HttpResponse<VayLai[]>) =>
                this.convertArrayResponse(res)
            );
    }
    baoCao(start: Date, end: Date,chon:number): Observable<HttpResponse<VayLai[]>> {
        let endd = this.convertDateToString(end);
        let startd = this.convertDateToString(start);
        return this.http.get<VayLai[]>(`${this.baocaoUrl}/${startd}/${endd}/${chon}`, { observe: 'response' })
            .map((res: HttpResponse<VayLai[]>) => this.convertArrayResponse(res));
    }
    baoCaoNV(start: Date, end: Date, id: number,chon:number): Observable<HttpResponse<VayLai[]>> {
        let endd = this.convertDateToString(end);
        let startd = this.convertDateToString(start);
        return this.http.get<VayLai[]>(`${this.baocaoNVUrl}/${startd}/${endd}/${id}/${chon}`, { observe: 'response' })
            .map((res: HttpResponse<VayLai[]>) => this.convertArrayResponse(res));
    }
    
    findByHopDong(id: number): Observable<EntityResponseType> {
        return this.http.get<VayLai>(`${this.findByHopDongUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }
    private convertDateToString(d: Date): String {

        let m = d.getMonth() + 1;
        let mm = m < 10 ? '0' + m : m;
        let day = d.getDate();
        let sday = day < 10 ? '0' + day : day;

        return d.getFullYear() + ' ' + mm + ' ' + sday;

    }

    tienVayThemTraBot(id:number,mahopdong:string): Observable<HttpResponse<string>> {
        return this.http.get<string>(`${this.tienVayThemTraBotUrl}/${id}/${mahopdong}`, { observe: 'response' });
    }
    baoCaoKeToan(start: Date, end: Date,chon:number,id:number): Observable<HttpResponse<VayLai[]>> {
        let endd = this.convertDateToString(end);
        let startd = this.convertDateToString(start);
        return this.http.get<VayLai[]>(`${this.baocaoUrlKeToan}/${startd}/${endd}/${chon}/${id}`, { observe: 'response' })
            .map((res: HttpResponse<VayLai[]>) => this.convertArrayResponse(res));
    }

}
