CREATE  FUNCTION `tinhNo`(`mahd` INT) RETURNS int(11)
BEGIN
DECLARE tongNo DOUBLE;
DECLARE tongTra double;
DECLARE conLai double;
SELECT SUM(ghi_no.sotien) into tongNo from ghi_no WHERE ghi_no.trangthai='NO' and ghi_no.hop_dong_id = mahd;
SELECT SUM(ghi_no.sotien) INTO tongTra from ghi_no WHERE ghi_no.trangthai='TRA' and ghi_no.hop_dong_id = mahd;
SELECT tongNo - tongTra into conLai;
RETURN (conLai);
END